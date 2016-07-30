package pl.guideme.burkia.view.components.base;

import android.os.Bundle;

public class Action {
    public static Bundle named(int actionValue){
        Bundle b = new Bundle();
        b.putInt(ActionKeys.ACTION_NAME, actionValue);
        return b;
    }
}
