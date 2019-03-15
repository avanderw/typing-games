package net.avdw.typing.desktop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import org.pmw.tinylog.Logger;

public class GoodKey extends InputAdapter {
    @Override
    public boolean keyDown(int keycode) {
        Logger.debug(String.format("good key = %s", Input.Keys.toString(keycode)));

        return true;
    }

}
