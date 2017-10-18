package info.socialhackathonumbria.memo.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import info.socialhackathonumbria.memo.client.Client;
import info.socialhackathonumbria.memo.models.Feature;
import info.socialhackathonumbria.memo.models.GeoJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LuckySeven srl on 18/10/2017.
 */

public class GoogleMapsFragment extends SupportMapFragment
        implements OnMapReadyCallback, Callback<GeoJson> {
    private GoogleMap map;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions().position(sydney)
//                .title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        fetchEarthquakes();
    }

    public void fetchEarthquakes() {
        Context ctx = getContext();
        if (ctx != null) {
            long startTime = System.currentTimeMillis() - (48 * 60 * 60 * 1000L);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(startTime);
            Client.sharedEarthquake.endpoints.query("geojson", formatter.format(date))
                    .enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<GeoJson> call, Response<GeoJson> response) {
        if (response.isSuccessful() && map != null) {
            for (Feature feature : response.body().features) {
                LatLng coordinates = new LatLng(
                        feature.geometry.coordinates[1],
                        feature.geometry.coordinates[0]);
                map.addMarker(new MarkerOptions()
                        .position(coordinates)
                        .title("Magnitudo " + feature.properties.mag)
                );
            }
        }
    }

    @Override
    public void onFailure(Call<GeoJson> call, Throwable t) {

    }
}
