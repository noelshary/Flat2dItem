package io.flat2d;

/** Mixin-Ziele (Yarn 1.21.11) als Konstanten. */
public final class Descriptors {
    private Descriptors() {}

    private static final String ARGS =
            "Lnet/minecraft/client/util/math/MatrixStack;"
            + "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;"
            + "I"
            + "Lnet/minecraft/client/render/entity/state/ItemStackEntityRenderState;"
            + "Lnet/minecraft/util/math/random/Random;";

    public static final String NO_BOX_A = "renderStack(" + ARGS + ")V";
    public static final String NO_BOX_B = "render(" + ARGS + ")V";
    public static final String BOX_A = "renderStack(" + ARGS + "Lnet/minecraft/util/math/Box;)V";
    public static final String BOX_B = "render(" + ARGS + "Lnet/minecraft/util/math/Box;)V";
}
