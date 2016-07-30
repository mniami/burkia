package pl.guideme.burkia.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.base.Action;
import pl.guideme.burkia.view.components.base.BaseFragment;

public class DrawerFragment extends BaseFragment {
    public static final int BUTTON_CLICKED = 1;
    private ImageButton mImageButton;

    public boolean closeClicked() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer, container, false);
        mImageButton = (ImageButton) view.findViewById(R.id.imageButton);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raiseAction(Action.named(BUTTON_CLICKED));
            }
        });
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mImageButton.setOnClickListener(null);
    }
}
