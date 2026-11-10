package io.neoloni.flat2d;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Flat2DScreen extends Screen {
    private static final int PANEL_W = 260;
    private static final int PANEL_H = 150;

    private static final int COL_BORDER = 0xFF3A6BFF;
    private static final int COL_PANEL = 0xF00E1426;
    private static final int COL_HEADER = 0xFF16213E;
    private static final int COL_MUTED = 0xFF8FA3C7;

    private final Screen parent;
    private ButtonWidget toggleButton;

    public Flat2DScreen(Screen parent) {
        super(Text.literal("2D Items"));
        this.parent = parent;
    }

    private int px() { return (this.width - PANEL_W) / 2; }
    private int py() { return (this.height - PANEL_H) / 2; }

    @Override
    protected void init() {
        int x = px() + 30;
        int y = py() + 46;
        int w = PANEL_W - 60;

        toggleButton = ButtonWidget.builder(toggleText(), b -> {
            Flat2DConfig.get().enabled = !Flat2DConfig.get().enabled;
            b.setMessage(toggleText());
        }).dimensions(x, y, w, 22).build();
        this.addDrawableChild(toggleButton);

        int half = (w - 6) / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Zurücksetzen"), b -> {
            Flat2DConfig.get().reset();
            toggleButton.setMessage(toggleText());
        }).dimensions(x, y + 36, half, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Fertig"), b -> this.close())
                .dimensions(x + half + 6, y + 36, half, 20).build());
    }

    private Text toggleText() {
        boolean on = Flat2DConfig.get().enabled;
        return Text.literal("Flache Items: ")
                .append(Text.literal(on ? "AN" : "AUS").formatted(on ? Formatting.GREEN : Formatting.RED));
    }

    @Override
    public void renderBackground(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.renderBackground(ctx, mouseX, mouseY, delta);
        int x = px();
        int y = py();
        // Rahmen + Panel mit leicht abgerundeten Ecken
        ctx.fill(x - 1, y - 1, x + PANEL_W + 1, y + PANEL_H + 1, COL_BORDER);
        ctx.fill(x, y, x + PANEL_W, y + PANEL_H, COL_PANEL);
        ctx.fill(x, y, x + PANEL_W, y + 30, COL_HEADER);
        ctx.fill(x, y + 30, x + PANEL_W, y + 31, COL_BORDER);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);
        int x = px();
        int y = py();
        ctx.drawCenteredTextWithShadow(this.textRenderer, Text.literal("2D Items").formatted(Formatting.BOLD),
                this.width / 2, y + 11, 0xFFFFFFFF);
        ctx.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Items flach wie in alten Versionen"),
                this.width / 2, y + 112, COL_MUTED);
        ctx.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Wirkt in Hand, am Boden und im Rahmen"),
                this.width / 2, y + 126, COL_MUTED);
    }

    @Override
    public void close() {
        Flat2DConfig.save();
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
}
