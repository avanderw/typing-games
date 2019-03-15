package net.avdw.typing.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class Config extends AbstractModule {
    @Override
    protected void configure() {
        bind(ApplicationAdapter.class).to(Application.class);
    }

    @Provides
    LwjglApplicationConfiguration lwjglApplicationConfiguration() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "multimaze-desktop-client";
        config.useGL30 = Boolean.FALSE;
        config.width = 640;
        config.height = 480;
        return config;
    }
}
