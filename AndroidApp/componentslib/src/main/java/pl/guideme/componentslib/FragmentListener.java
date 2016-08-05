package pl.guideme.componentslib;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public interface FragmentListener {
    void fragmentStarted(Fragment fragment);
    void fragmentResumed(Fragment fragment);
    void fragmentPaused(Fragment fragment);
    void fragmentStopped(Fragment fragment);
    void fragmentDestroyed(Fragment fragment);
    void fragmentAction(Bundle actionArgument);
}
