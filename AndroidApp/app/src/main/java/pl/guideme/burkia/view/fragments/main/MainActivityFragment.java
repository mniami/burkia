package pl.guideme.burkia.view.fragments.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import pl.guideme.burkia.R;
import pl.guideme.burkia.view.fragments.BaseFragment;

@EFragment
public class MainActivityFragment extends BaseFragment {

    protected RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MainAdapter mAdapter;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.m_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MainAdapter(new String[]{
                "asdasd","asd","valdkjklfsjs"
        });
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
