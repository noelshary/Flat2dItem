package io.flat2d;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Flat2dScreen extends Screen {
    private static final int PANEL_W = 260;
    private static final int PANEL_H = 176;
    private static final int BORDER = 0xFF3FA9F5;
    private static final int PANEL = 0xF0121A2B;
    private static final int HEADER = 0xFF1B2A47;

    private final Screen parent;
    private ButtonWidget toggleButton;
    private ButtonWidget thicknessButton;

    public Flat2dScreen(Screen parent) {
        super(Text.literal("2D Items"));
        this.parent = parent;
    }

    private int left() { return (this.width - PANEL_W) / 2; }
    private int top() { return (this.height - PANEL_H) / 2; }

    @Override
    protected void init() {
        int x = left() + 20;
        int w = PANEL_W - 40;
        int y = top() + 52;

        toggleButton = this.addDrawableChild(ButtonWidget.builder(toggleText(), b -> {
            Flat2dConfig.INSTANCE.groundFlat = !Flat2dConfig.INSTANCE.groundFlat;
            Flat2dConfig.save();
            refresh();
        }).dimensions(x, y, w, 20).build());

        thicknessButton = this.addDrawableChild(ButtonWidget.builder(thicknessText(), b -> {
            Flat2dConfig.INSTANCE.thin = !Flat2dConfig.INSTANCE.thin;
            Flat2dConfig.save();
            refresh();
        }).dimensions(x, y + 26, w, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Zurücksetzen"), b -> {
            Flat2dConfig.reset();
            refresh();
        }).dimensions(x, y + 66, (w - 6) / 2, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Fertig"), b -> this.close())
                .dimensions(x + (w - 6) / 2 + 6, y + 66, (w - 6) / 2, 20).build());
    }

    private void refresh() {
        toggleButton.setMessage(toggleText());
        thicknessButton.setMessage(thicknessText());
    }

    private Text toggleText() {
        boolean on = Flat2dConfig.INSTANCE.groundFlat;
        return Text.literal("Items am Boden flach: ")
                .append(Text.literal(on ? "AN" : "AUS").formatted(on ? Formatting.GREEN : Formatting.RED));
    }

    private Text thicknessText() {
        boolean thin = Flat2dConfig.INSTANCE.thin;
        return Text.literal("Dicke: ")
                .append(Text.literal(thin ? "Dünn" : "Ganz flach").formatted(Formatting.AQUA));
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        int x = left();
        int y = top();
        rounded(context, x, y, x + PANEL_W, y + PANEL_H, 6, BORDER);
        rounded(context, x + 1, y + 1, x + PANEL_W - 1, y + PANEL_H - 1, 5, PANEL);
        rounded(context, x + 1, y + 1, x + PANEL_W - 1, y + 34, 5, HEADER);
        context.fill(x + 12, y + 34, x + PANEL_W - 12, y + 35, BORDER);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int cx = this.width / 2;
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, cx, top() + 12, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("Gedroppte Items liegen flach am Boden").formatted(Formatting.GRAY),
                cx, top() + 40, 0xFFAAAAAA);
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }

    /** Gefuelltes Rechteck mit abgerundeten Ecken. */
    private static void rounded(DrawContext c, int x1, int y1, int x2, int y2, int r, int color) {
        c.fill(x1 + r, y1, x2 - r, y2, color);
        c.fill(x1, y1 + r, x1 + r, y2 - r, color);
        c.fill(x2 - r, y1 + r, x2, y2 - r, color);
        for (int i = 0; i < r; i++) {
            double d = r - i - 0.5;
            int dx = (int) Math.round(r - Math.sqrt(r * r - d * d));
            c.fill(x1 + dx, y1 + i, x1 + r, y1 + i + 1, color);
            c.fill(x2 - r, y1 + i, x2 - dx, y1 + i + 1, color);
            c.fill(x1 + dx, y2 - i - 1, x1 + r, y2 - i, color);
            c.fill(x2 - r, y2 - i - 1, x2 - dx, y2 - i, color);
        }
    }
}
