package pl.guideme.burkia.view.components.mainrecycleview;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.components.base.ComponentAdapter;

@EBean
public class MainRecyclerView extends ComponentAdapter {
    protected View mContainer;
    protected RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MainRecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(FragmentActivity activity, Context context, View view){
        mContainer = view;
        mRecyclerView = (RecyclerView)view.findViewById(R.id.m_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MainRecyclerViewAdapter(new String[]{
                "asdasd","asd","valdkjklfsjs"
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        mContainer = null;
        mRecyclerView = null;
        mLayoutManager = null;
        mAdapter = null;
    }
}
