package pl.guideme.burkia.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import pl.guideme.burkia.R.id;
import pl.guideme.burkia.R.layout;
import pl.guideme.burkia.R.string;
import pl.guideme.componentslib.Action;
import pl.guideme.componentslib.BaseFragment;
import pl.guideme.data.logs.Log;
import pl.guideme.data.util.ImageLoader;

@EFragment
public class DrawerFragment extends BaseFragment {
    public static final int BUTTON_CLICKED = 1;
    private static final String DRAWER_FRAGMENT = "DrawerFragment";
    private ImageButton mImageButton;

    @Bean
    protected ImageLoader mImageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        log = Log.withName(DRAWER_FRAGMENT);
        View view = inflater.inflate(layout.drawer, container, false);
        mImageButton = (ImageButton) view.findViewById(id.imageButton);
        mImageLoader.load(getString(string.test_image_url), getContext(), mImageButton);
        mImageButton.setOnClickListener(view1 -> raiseAction(Action.named(BUTTON_CLICKED)));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImageButton.setOnClickListener(null);
    }
}
