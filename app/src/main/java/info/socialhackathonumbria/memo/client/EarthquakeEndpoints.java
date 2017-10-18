package info.socialhackathonumbria.memo.client;

import info.socialhackathonumbria.memo.models.GeoJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by LuckySeven srl on 18/10/2017.
 */

public interface EarthquakeEndpoints {

    @GET("query")
    public Call<GeoJson> query(
            @Query("format") String format,
            @Query("starttime") String startTime
    );

}
