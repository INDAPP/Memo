package info.socialhackathonumbria.memo.client;

import com.squareup.moshi.Moshi;

import info.socialhackathonumbria.memo.adapters.DateAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by LuckySeven srl on 04/10/2017.
 */

public class Client<T> {
    public static final String BASE_URL_NEWS = "https://newsapi.org/v1/";
    public static final String BASE_URL_EARTHQUAKE = "https://earthquake.usgs.gov/fdsnws/event/1/";
    public static final String API_KEY = "d0a3d237e0e14249941d26e316542df1";
    public static final Client<NewsEndpoint> sharedNews =
            new Client<>(BASE_URL_NEWS, NewsEndpoint.class);
    public static final Client<EarthquakeEndpoints> sharedEarthquake =
            new Client<>(BASE_URL_EARTHQUAKE, EarthquakeEndpoints.class);

    public final T endpoints;

    private Client(String baseUrl, Class<T> tClass) {
        Moshi moshi = new Moshi.Builder()
                .add(new DateAdapter())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
        this.endpoints = retrofit.create(tClass);
    }
}
