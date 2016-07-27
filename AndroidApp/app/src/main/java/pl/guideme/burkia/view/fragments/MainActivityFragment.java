package pl.guideme.burkia.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.ChangeFragment;
import pl.guideme.burkia.view.fragments.BaseFragment;

@EFragment
public class MainActivityFragment extends BaseFragment {
    @Bean
    protected ChangeFragment changeFragment;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}
