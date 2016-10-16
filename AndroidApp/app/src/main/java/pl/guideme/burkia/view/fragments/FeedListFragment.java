package pl.guideme.burkia.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import pl.guideme.burkia.R;
import pl.guideme.componentslib.Action;
import pl.guideme.componentslib.BaseFragment;
import pl.guideme.burkia.view.components.feeds.FeedsRecyclerViewAdapter;
import pl.guideme.data.datas.DataService;
import pl.guideme.data.logs.Log;
import pl.guideme.data.vo.FeedItem;

@EBean
public class FeedListFragment extends BaseFragment {
    private static final Log log = Log.withName("FeedListFragment");
    public static final int FEED_LIST_ITEM_CLICKED = 1;

    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected FeedsRecyclerViewAdapter mAdapter;

    @Bean
    protected DataService mDataService;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        log.fine(()->"onCreateView called");
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.m_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FeedsRecyclerViewAdapter(mDataService.getFeeds().toArray(new FeedItem[0]));
        mAdapter.setTextClickListener(view1 -> raiseAction(Action.named(FEED_LIST_ITEM_CLICKED)));
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
