package info.socialhackathonumbria.memo.client;

import info.socialhackathonumbria.memo.models.ArticlesResponse;
import info.socialhackathonumbria.memo.models.SourcesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by LuckySeven srl on 04/10/2017.
 */

public interface NewsEndpoint {

    @GET("articles")
    public Call<ArticlesResponse> articles(
            @Query("source") String source,
            @Query("apiKey") String apiKey
    );

    @GET("sources")
    public Call<SourcesResponse> sources();

}
