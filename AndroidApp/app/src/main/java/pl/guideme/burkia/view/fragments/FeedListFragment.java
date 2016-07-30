package pl.guideme.burkia.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.base.Action;
import pl.guideme.burkia.view.components.base.BaseFragment;
import pl.guideme.burkia.view.customviews.MainRecyclerViewAdapter;

public class FeedListFragment extends BaseFragment {
    public static final int FEED_LIST_ITEM_CLICKED = 1;

    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected MainRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.m_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MainRecyclerViewAdapter(new String[]{
                "asdasd", "asd", "valdkjklfsjs"
        });
        mAdapter.setTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raiseAction(Action.named(FEED_LIST_ITEM_CLICKED));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
