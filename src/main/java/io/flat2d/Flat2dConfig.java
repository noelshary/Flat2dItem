package io.flat2d;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Flat2dConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /** Items am Boden flach darstellen. */
    public boolean groundFlat = true;
    /** false = komplett flach, true = duenn (Rand bleibt leicht sichtbar). */
    public boolean thin = false;

    public static Flat2dConfig INSTANCE = new Flat2dConfig();

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("flat2d.json");
    }

    public static float depth() {
        return INSTANCE.thin ? 0.15f : 0.02f;
    }

    public static void load() {
        Path f = file();
        if (!Files.exists(f)) {
            save();
            return;
        }
        try {
            Flat2dConfig c = GSON.fromJson(Files.readString(f), Flat2dConfig.class);
            if (c != null) INSTANCE = c;
        } catch (Exception e) {
            System.err.println("[flat2d] Konfiguration konnte nicht gelesen werden: " + e);
        }
    }

    public static void save() {
        try {
            Files.writeString(file(), GSON.toJson(INSTANCE));
        } catch (IOException e) {
            System.err.println("[flat2d] Konfiguration konnte nicht gespeichert werden: " + e);
        }
    }

    public static void reset() {
        INSTANCE = new Flat2dConfig();
        save();
    }
}
