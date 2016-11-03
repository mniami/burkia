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
import org.androidannotations.annotations.EFragment;

import java.util.List;

import pl.guideme.burkia.R;
import pl.guideme.componentslib.Action;
import pl.guideme.componentslib.BaseFragment;
import pl.guideme.burkia.view.components.feeds.FeedsRecyclerViewAdapter;
import pl.guideme.data.util.ImageLoader;
import pl.guideme.data.api.FeedResponse;
import pl.guideme.data.datas.DataService;
import pl.guideme.data.logs.Log;
import pl.guideme.data.vo.Atomic;

@EFragment
public class FeedListFragment extends BaseFragment {
    private static final String FEED_LIST_FRAGMENT = "FeedListFragment";
    public static final int FEED_LIST_ITEM_CLICKED = 1;

    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected FeedsRecyclerViewAdapter mAdapter;

    @Bean
    protected DataService mDataService;
    @Bean
    protected ImageLoader mImageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        log = Log.withName(FEED_LIST_FRAGMENT);
        log.fine(()->"onCreateView called");
        View view = inflater.inflate(R.layout.feed_list_layout, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.m_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FeedsRecyclerViewAdapter(mImageLoader, toArray(mDataService.getFeeds()));
        mAdapter.setTextClickListener(view1 -> raiseAction(Action.named(FEED_LIST_ITEM_CLICKED)));
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private Atomic[] toArray(List<FeedResponse> feeds) {
        return feeds.get(0).getAtomics().toArray(new Atomic[0]);
    }
}
