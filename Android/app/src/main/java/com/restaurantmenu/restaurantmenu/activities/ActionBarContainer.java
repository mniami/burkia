package com.restaurantmenu.restaurantmenu.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.restaurantmenu.restaurantmenu.R;

import roboguice.fragment.RoboFragment;

/**
 * Created by dszcz_000 on 22.08.2015.
 */
public class ActionBarContainer {

    public void createView(LayoutInflater inflater, Activity activity) {
        final View view = inflater.inflate(R.layout.actionbar_one, null);

        ActionBar mActionBar = activity.getActionBar();

        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);

        final TextView mTitleTextView = (TextView) view.findViewById(R.id.title_text);
        final EditText editText = (EditText)view.findViewById(R.id.searchEditText);

        editText.setHint("Wyszukaj restauracje");
        mTitleTextView.setText("Restauracje");

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int editTextVisibility = editText.getVisibility();

                if (editTextVisibility == View.VISIBLE){
                    editText.setVisibility(View.GONE);
                    mTitleTextView.setVisibility(View.VISIBLE);
                }
                else {
                    editText.setVisibility(View.VISIBLE);
                    mTitleTextView.setVisibility(View.GONE);
                }
            }
        });

        mActionBar.setCustomView(view);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
}
