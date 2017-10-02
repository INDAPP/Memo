package info.socialhackathonumbria.memo.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.socialhackathonumbria.memo.R;
import info.socialhackathonumbria.memo.adpters.MessagesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    OnHomeFragmentInteractionListener listener;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public MessagesAdapter mAdapter;

    public HomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.fab).setOnClickListener(this);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new MessagesAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            listener = (OnHomeFragmentInteractionListener) context;
        }
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onFabClick(view);
        }
    }

    public interface OnHomeFragmentInteractionListener {
        void onFabClick(View view);
    }
}
