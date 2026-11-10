package io.neoloni.flat2d;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/** Das Menue ist NUR ueber das ModMenu erreichbar (keine Taste). */
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return Flat2DScreen::new;
    }
}
