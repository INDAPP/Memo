package info.socialhackathonumbria.memo.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import info.socialhackathonumbria.memo.R;
import info.socialhackathonumbria.memo.adpters.NewsAdapter;
import info.socialhackathonumbria.memo.client.Client;
import info.socialhackathonumbria.memo.models.ArticlesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    public RecyclerView recyclerView;
    public ProgressBar progressView;
    public NewsAdapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressView = view.findViewById(R.id.progressBar);

        mAdapter = new NewsAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(mLayoutManager);

        update();
    }

    private void update() {
        Client.shared.endpoints
                .articles("hacker-news", Client.API_KEY)
                .enqueue(new Callback<ArticlesResponse>() {
                    @Override
                    public void onResponse(Call<ArticlesResponse> call,
                                           Response<ArticlesResponse> response) {
                        progressView.setVisibility(View.GONE);
                        if(response.isSuccessful() && response.body() != null) {
                            mAdapter.update(response.body().articles);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticlesResponse> call,
                                          Throwable t) {
                        progressView.setVisibility(View.GONE);
                        Context ctx = getContext();
                        if (ctx != null)
                            Toast.makeText(ctx, "Errore durante la richiesta", Toast.LENGTH_SHORT)
                                    .show();
                    }
                });
    }
}
