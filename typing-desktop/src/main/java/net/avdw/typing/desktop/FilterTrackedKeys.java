package net.avdw.typing.desktop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import org.pmw.tinylog.Logger;

import java.util.HashSet;
import java.util.Set;

public class FilterTrackedKeys extends InputAdapter {
    Set<Integer> trackedInputSet = new HashSet<>();

    FilterTrackedKeys() {
        trackedInputSet.add(Input.Keys.A);
        trackedInputSet.add(Input.Keys.B);
        trackedInputSet.add(Input.Keys.C);
        trackedInputSet.add(Input.Keys.D);
        trackedInputSet.add(Input.Keys.E);
        trackedInputSet.add(Input.Keys.F);
        trackedInputSet.add(Input.Keys.G);
        trackedInputSet.add(Input.Keys.H);
        trackedInputSet.add(Input.Keys.I);
        trackedInputSet.add(Input.Keys.J);
        trackedInputSet.add(Input.Keys.K);
        trackedInputSet.add(Input.Keys.L);
        trackedInputSet.add(Input.Keys.M);
        trackedInputSet.add(Input.Keys.N);
        trackedInputSet.add(Input.Keys.O);
        trackedInputSet.add(Input.Keys.P);
        trackedInputSet.add(Input.Keys.Q);
        trackedInputSet.add(Input.Keys.R);
        trackedInputSet.add(Input.Keys.S);
        trackedInputSet.add(Input.Keys.T);
        trackedInputSet.add(Input.Keys.U);
        trackedInputSet.add(Input.Keys.V);
        trackedInputSet.add(Input.Keys.W);
        trackedInputSet.add(Input.Keys.X);
        trackedInputSet.add(Input.Keys.Y);
        trackedInputSet.add(Input.Keys.Z);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (trackedInputSet.contains(keycode)) {
            return false;
        } else {
            Logger.debug(String.format("un-tracked key = %s", Input.Keys.toString(keycode)));
            return true;
        }
    }
}
