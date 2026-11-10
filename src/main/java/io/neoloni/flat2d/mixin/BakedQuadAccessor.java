package io.neoloni.flat2d.mixin;

import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BakedQuad.class)
public interface BakedQuadAccessor {
    @Accessor("face")
    Direction flat2d$getFace();
}
