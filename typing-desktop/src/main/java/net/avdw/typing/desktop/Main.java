package net.avdw.typing.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public class Main {
    public static void main(String[] args) {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class_name}.{method}:{line} [{level}] {message}")
                .level(Level.TRACE)
                .activate();

        Injector injector = Guice.createInjector(new Config());
        new LwjglApplication(injector.getInstance(ApplicationAdapter.class), injector.getInstance(LwjglApplicationConfiguration.class));
    }
}
