package pl.guideme.componentslib;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class ComponentAdapter implements Component{
    private int mComponentId;

    public void setComponentId(int componentId){
        this.mComponentId = componentId;
    }

    @Override
    public void onCreate(Context context, ComponentContainer container) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getComponentId() {
        return mComponentId;
    }

    @Override
    public void register(Fragment fragment) {

    }

    @Override
    public void onActivityCreated(AppCompatActivity activity) {

    }

    @Override
    public void onActivityDestroy(AppCompatActivity activity) {

    }
}
