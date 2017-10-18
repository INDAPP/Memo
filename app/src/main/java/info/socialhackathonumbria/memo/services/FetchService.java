package info.socialhackathonumbria.memo.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import info.socialhackathonumbria.memo.client.Client;
import info.socialhackathonumbria.memo.models.ArticlesResponse;
import info.socialhackathonumbria.memo.models.News;
import info.socialhackathonumbria.memo.models.Source;
import info.socialhackathonumbria.memo.models.SourcesResponse;
import io.realm.Realm;
import retrofit2.Response;

public class FetchService extends IntentService implements Realm.Transaction {
    private static final String ACTION_FETCH_ARTICLES = "info.socialhackathonumbria.memo.services.action.FETCH_ARTICLES";
    private static final String EXTRA_SOURCE = "info.socialhackathonumbria.memo.services.extra.SOURCE";
    public static final String ACTION_FETCH_ARTICLES_COMPLETED = "info.socialhackathonumbria.memo.services.action.ACTION_FETCH_ARTICLES_COMPLETED";
    public static final String ACTION_FETCH_SOURCES_COMPLETED = "info.socialhackathonumbria.memo.services.action.ACTION_FETCH_SOURCES_COMPLETED";
    private static final String ACTION_FETCH_RESOURCES = "info.socialhackathonumbria.memo.services.action.FETCH_RESOURCES";

    private ArticlesResponse mArticlesResponse;
    private SourcesResponse mSourcesResponse;

    public FetchService() {
        super("FetchService");
    }

    public static void startArticlesFetch(Context context, String source) {
        Intent intent = new Intent(context, FetchService.class);
        intent.setAction(ACTION_FETCH_ARTICLES);
        intent.putExtra(EXTRA_SOURCE, source);
        context.startService(intent);
    }

    public static void startSourcesFetch(Context context) {
        Intent intent = new Intent(context, FetchService.class);
        intent.setAction(ACTION_FETCH_RESOURCES);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            switch (action) {
                case ACTION_FETCH_ARTICLES:
                    final String source = intent.getStringExtra(EXTRA_SOURCE);
                    handleArticlesFetch(source);
                    break;
                case ACTION_FETCH_RESOURCES:
                    handleSourcesFetch();
                    break;
            }
        }
    }

    private void handleArticlesFetch(String source) {
        if (source != null) {
            try {
                Response<ArticlesResponse> response =
                        Client.sharedNews.endpoints.articles(source, Client.API_KEY).execute();
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().articles.length > 0) {
                    mArticlesResponse = response.body();
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(this);
                    realm.close();
                    Intent intent = new Intent(ACTION_FETCH_ARTICLES_COMPLETED);
                    sendBroadcast(intent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleSourcesFetch() {
        try {
            Response<SourcesResponse> response =
                    Client.sharedNews.endpoints.sources().execute();
            if (response.isSuccessful()
                    && response.body() != null
                    && response.body().sources.length > 0) {
                mSourcesResponse = response.body();
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(this);
                realm.close();

                Intent intent = new Intent(ACTION_FETCH_SOURCES_COMPLETED);
                sendBroadcast(intent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Realm realm) {
        if (mArticlesResponse != null) {
            List<News> newsList = Arrays.asList(mArticlesResponse.articles);
            for (News n : newsList) n.source = mArticlesResponse.source;
            realm.insertOrUpdate(newsList);
        }
        if (mSourcesResponse != null) {
            List<Source> sourcesList = Arrays.asList(mSourcesResponse.sources);
            realm.insertOrUpdate(sourcesList);
        }
    }
}
