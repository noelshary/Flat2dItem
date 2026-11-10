package io.neoloni.flat2d;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Einfache JSON-Config: .minecraft/config/flat2d.json */
public final class Flat2DConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("flat2d.json");
    private static Flat2DConfig instance = new Flat2DConfig();

    /** Flache Items ein/aus */
    public boolean enabled = true;

    public static Flat2DConfig get() {
        return instance;
    }

    public static void load() {
        try {
            if (Files.exists(FILE)) {
                Flat2DConfig loaded = GSON.fromJson(Files.readString(FILE), Flat2DConfig.class);
                if (loaded != null) instance = loaded;
            } else {
                save();
            }
        } catch (Exception e) {
            Flat2DMod.LOGGER.warn("Konnte flat2d.json nicht lesen, nutze Standardwerte", e);
            instance = new Flat2DConfig();
        }
    }

    public static void save() {
        try {
            Files.writeString(FILE, GSON.toJson(instance));
        } catch (IOException e) {
            Flat2DMod.LOGGER.warn("Konnte flat2d.json nicht speichern", e);
        }
    }

    public void reset() {
        enabled = true;
    }
}
