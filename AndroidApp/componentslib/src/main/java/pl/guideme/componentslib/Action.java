package pl.guideme.componentslib;

import android.os.Bundle;

public class Action {
    private Action() {
        throw new IllegalAccessError("EventAction class");
    }
    public static Bundle named(int actionValue){
        Bundle b = new Bundle();
        b.putInt(ActionKeys.ACTION_NAME, actionValue);
        return b;
    }
}
