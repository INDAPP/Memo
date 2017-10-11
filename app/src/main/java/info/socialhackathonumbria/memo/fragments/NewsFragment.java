package info.socialhackathonumbria.memo.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import info.socialhackathonumbria.memo.BuildConfig;
import info.socialhackathonumbria.memo.Memo;
import info.socialhackathonumbria.memo.R;
import info.socialhackathonumbria.memo.adapters.NewsAdapter;
import info.socialhackathonumbria.memo.client.Client;
import info.socialhackathonumbria.memo.models.ArticlesResponse;
import info.socialhackathonumbria.memo.models.Source;
import info.socialhackathonumbria.memo.models.SourcesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener {
    public static final String PREFS_SOURCE = "source";

    public RecyclerView recyclerView;
    public SwipeRefreshLayout refreshLayout;
    public ProgressBar progressView;
    public NewsAdapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    private String lastSource;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        SharedPreferences prefs = context.getSharedPreferences(BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE);
        lastSource = prefs.getString(PREFS_SOURCE, null);
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
        refreshLayout = view.findViewById(R.id.swipeRefresh);
        progressView = view.findViewById(R.id.progressBar);
        view.findViewById(R.id.fab).setOnClickListener(this);

        mAdapter = new NewsAdapter(this);

        mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new SimpleDecorator());

        refreshLayout.setOnRefreshListener(this);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (lastSource != null)
            update(lastSource);
        else
            selectSource();
    }

    private void update(String source) {
        setLastSource(source);
        Client.shared.endpoints
                .articles(source, Client.API_KEY)
                .enqueue(new Callback<ArticlesResponse>() {
                    @Override
                    public void onResponse(Call<ArticlesResponse> call,
                                           Response<ArticlesResponse> response) {
                        progressView.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(false);
                        if(response.isSuccessful() && response.body() != null) {
                            mAdapter.update(response.body().articles);
                        } else {
                            showMessage("Impossibile recuperare le news");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticlesResponse> call,
                                          Throwable t) {
                        progressView.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(false);
                        showMessage("Errore durante la richiesta");
                    }
                });
    }

    private void setLastSource(String source) {
        lastSource = source;
        if (getContext() != null) {
            SharedPreferences prefs = getContext().getSharedPreferences(BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE);
            prefs.edit().putString(PREFS_SOURCE, source).apply();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                selectSource();
                break;
            default:
                int position = recyclerView.getChildAdapterPosition(view);
                if (position != RecyclerView.NO_POSITION) onItemClick(position);
                break;
        }
    }

    private void onItemClick(int position) {
        String url = mAdapter.getUrl(position);
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder
                .setToolbarColor(Memo.getPrimaryColor(getContext()))
                .build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(url));
    }

    public void selectSource() {
        Client.shared.endpoints.sources()
                .enqueue(new Callback<SourcesResponse>() {
                    @Override
                    public void onResponse(Call<SourcesResponse> call,
                                           Response<SourcesResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            showSourcesDialog(response.body().sources);
                        } else {
                            showMessage("Impossibile recuperare le fonti");
                        }
                    }

                    @Override
                    public void onFailure(Call<SourcesResponse> call, Throwable t) {
                        showMessage("Impossibile recuperare le fonti");
                    }
                });
    }

    public void showSourcesDialog(final Source[] sources) {
        Context ctx = getContext();
        if (ctx != null) {
            CharSequence[] items = new CharSequence[sources.length];
            for (int i=0; i<sources.length; i++) items[i] = sources[i].name;
            new AlertDialog.Builder(ctx)
                    .setTitle("Fonti")
//                    .setMessage("Seleziona una fonte di notizie")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mAdapter.update(null);
                            progressView.setVisibility(View.VISIBLE);
                            Source source = sources[i];
                            update(source.id);
                        }
                    })
                    .show();
        }
    }

    public void showMessage(String message) {
        Context ctx = getContext();
        if (ctx != null)
            Toast.makeText(ctx, message, Toast.LENGTH_SHORT)
                    .show();
    }

    private class SimpleDecorator extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position > 0) {
                DisplayMetrics metrics = view.getContext().getResources().getDisplayMetrics();
                outRect.top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, metrics);
            } else {
                outRect.top = 0;
            }
        }
    }
}
