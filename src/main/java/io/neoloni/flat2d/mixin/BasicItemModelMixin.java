package io.neoloni.flat2d.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.neoloni.flat2d.Flat2DConfig;
import net.minecraft.client.render.item.model.BasicItemModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entfernt bei "generated" Items (Schwert, Diamant, ...) die Seitenflaechen,
 * die dem Item seine Dicke geben. Uebrig bleiben Vorder- und Rueckseite = flach.
 */
@Mixin(BasicItemModel.class)
public class BasicItemModelMixin {

    @WrapOperation(
            method = "update",
            at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z"),
            require = 0
    )
    private boolean flat2d$flatten(List<?> list, Collection<?> quads, Operation<Boolean> original) {
        return original.call(list, flat2d$filter(quads));
    }

    private static Collection<?> flat2d$filter(Collection<?> in) {
        Flat2DConfig cfg = Flat2DConfig.get();
        if (!cfg.enabled || in.isEmpty()) return in;

        int south = 0, north = 0, sides = 0;
        for (Object o : in) {
            if (!(o instanceof BakedQuad quad)) return in;
            Direction d = ((BakedQuadAccessor) (Object) quad).flat2d$getFace();
            if (d == Direction.SOUTH) south++;
            else if (d == Direction.NORTH) north++;
            else sides++;
        }

        // Typisches "generated" Item: gleich viele Vorder-/Rueckseiten, aber viele kleine Seitenstuecke.
        // Ein normaler Block hat dagegen genau 1 Quad pro Seite (sides == 4).
        boolean looksLikeFlatItem = south > 0 && south == north && sides > 4 * south;
        if (!looksLikeFlatItem) return in;

        List<BakedQuad> out = new ArrayList<>();
        for (Object o : in) {
            BakedQuad quad = (BakedQuad) o;
            Direction d = ((BakedQuadAccessor) (Object) quad).flat2d$getFace();
            if (d == Direction.SOUTH || d == Direction.NORTH) out.add(quad);
        }
        return out;
    }
}
