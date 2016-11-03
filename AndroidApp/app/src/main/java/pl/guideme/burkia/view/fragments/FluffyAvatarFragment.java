package pl.guideme.burkia.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.customviews.LoadingBarView;
import pl.guideme.componentslib.BaseFragment;
import pl.guideme.data.datas.DataService;
import pl.guideme.data.logs.Log;

@EFragment
public class FluffyAvatarFragment extends BaseFragment {
    private static final String FLUFFY_AVATAR_FRAGMENT = "FluffyAvatarFragment";

    @Bean
    protected DataService mDataService;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        log = Log.withName(FLUFFY_AVATAR_FRAGMENT);
        log.fine(() -> "onCreateView called");
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fluffy_avatar_layout, container, false);
        return view;
    }
}
