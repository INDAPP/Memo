package info.socialhackathonumbria.memo.client;

import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by LuckySeven srl on 04/10/2017.
 */

public class Client {
    public static final Client shared = new Client();
    public static final String BASE_URL = "https://newsapi.org/v1/";
    public static final String API_KEY = "d0a3d237e0e14249941d26e316542df1";

    public final Endpoints endpoints;

    private Client() {
        Moshi moshi = new Moshi.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
        this.endpoints = retrofit.create(Endpoints.class);
    }
}
