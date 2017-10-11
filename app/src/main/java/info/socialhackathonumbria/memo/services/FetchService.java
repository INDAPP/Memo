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
import io.realm.Realm;
import retrofit2.Response;

public class FetchService extends IntentService implements Realm.Transaction {
    private static final String ACTION_FETCH_ARTICLES = "info.socialhackathonumbria.memo.services.action.FETCH_ARTICLES";
    private static final String EXTRA_SOURCE = "info.socialhackathonumbria.memo.services.extra.SOURCE";

    private ArticlesResponse mResponse;

    public FetchService() {
        super("FetchService");
    }

    public static void startArticlesFetch(Context context, String source) {
        Intent intent = new Intent(context, FetchService.class);
        intent.setAction(ACTION_FETCH_ARTICLES);
        intent.putExtra(EXTRA_SOURCE, source);
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
            }
        }
    }

    private void handleArticlesFetch(String source) {
        if (source != null) {
            try {
                Response<ArticlesResponse> response =
                        Client.shared.endpoints.articles(source, Client.API_KEY).execute();
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().articles.length > 0) {
                    mResponse = response.body();
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(this);
                    realm.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void execute(Realm realm) {
        List<News> newsList = Arrays.asList(mResponse.articles);
        realm.insertOrUpdate(newsList);
    }
}
