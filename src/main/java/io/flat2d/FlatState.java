package io.flat2d;

import net.minecraft.client.util.math.MatrixStack;

/** Haelt den Flach-Zustand waehrend ein gedropptes Item gezeichnet wird. */
public final class FlatState {
    private static int depth = 0;
    private static boolean pushed = false;

    private FlatState() {}

    public static void begin(MatrixStack matrices) {
        if (depth++ == 0) {
            pushed = Flat2dConfig.INSTANCE.groundFlat;
            if (pushed) {
                matrices.push();
                matrices.scale(1.0f, 1.0f, Flat2dConfig.depth());
            }
        }
    }

    public static void end(MatrixStack matrices) {
        if (depth > 0 && --depth == 0 && pushed) {
            matrices.pop();
            pushed = false;
        }
    }
}
