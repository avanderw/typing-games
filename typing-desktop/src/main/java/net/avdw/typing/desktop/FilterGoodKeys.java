package net.avdw.typing.desktop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import org.pmw.tinylog.Logger;

public class FilterGoodKeys extends InputAdapter {
    @Override
    public boolean keyDown(int keycode) {
        if (44 == keycode) {
            return false;
        } else {
            Logger.debug(String.format("bad key = %s", Input.Keys.toString(keycode)));
            return true;
        }
    }

}
