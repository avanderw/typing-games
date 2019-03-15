package net.avdw.typing.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public class Application extends ApplicationAdapter {
    @Override
    public void create() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new FilterTrackedKeys());
        inputMultiplexer.addProcessor(new FilterGoodKeys());
        inputMultiplexer.addProcessor(new GoodKey());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
}
