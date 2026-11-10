package io.flat2d.mixin;

import io.flat2d.Descriptors;
import io.flat2d.FlatState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.state.ItemStackEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin {

    // Variante ohne Box
    @Inject(method = {Descriptors.NO_BOX_A, Descriptors.NO_BOX_B}, at = @At("HEAD"), require = 0)
    private static void flat2d$begin(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                                     ItemStackEntityRenderState state, Random random, CallbackInfo ci) {
        FlatState.begin(matrices);
    }

    @Inject(method = {Descriptors.NO_BOX_A, Descriptors.NO_BOX_B}, at = @At("RETURN"), require = 0)
    private static void flat2d$end(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                                   ItemStackEntityRenderState state, Random random, CallbackInfo ci) {
        FlatState.end(matrices);
    }

    // Variante mit Box
    @Inject(method = {Descriptors.BOX_A, Descriptors.BOX_B}, at = @At("HEAD"), require = 0)
    private static void flat2d$beginBox(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                                        ItemStackEntityRenderState state, Random random, Box box, CallbackInfo ci) {
        FlatState.begin(matrices);
    }

    @Inject(method = {Descriptors.BOX_A, Descriptors.BOX_B}, at = @At("RETURN"), require = 0)
    private static void flat2d$endBox(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                                      ItemStackEntityRenderState state, Random random, Box box, CallbackInfo ci) {
        FlatState.end(matrices);
    }
}
