package com.example.jasamarga3;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jasamarga3.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private JasamargaDatabase db;
    private AutoCompleteTextView searchLoc;
    private Marker marker;
    private Geocoder geocoder;
    private LatLng cawang;
    private List<Address> addresses;
    private ArrayList<LatLng> latLng;
    private Intent intent, intent2;
    private Bundle args;
    private ImageView add, history;
    private Boolean mapClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Places.initialize(getApplicationContext(), "${MAPS_API_KEY}");
        PlacesClient placesClient = Places.createClient(this);
        add = (ImageView) findViewById(R.id.iv_maps_add);
        history = (ImageView) findViewById(R.id.iv_maps_history);
        db = new JasamargaDatabase(this);
        intent = new Intent(MapsActivity.this, InputTempatActivity.class);
        args = new Bundle();
        latLng = new ArrayList<>();

        tambahKegiatan();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this, Locale.getDefault());
        cawang = new LatLng(-6.2115, 106.8452);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cawang));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cawang, 15));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                try {
                    mapClicked = true;
                    tambahKegiatan();
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    args.putSerializable("DetailLokasi", (Serializable) addresses);
                    intent.putExtra("BUNDLE", args);
                    if (marker != null) {
                        marker.setPosition(latLng);
                    } else {
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(city).snippet(address));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        search();
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, DaftarKegiatanActivity.class));
            }
        });
        markerKegiatan();
    }

    private void search() {
        searchLoc = (AutoCompleteTextView) findViewById(R.id.tv_maps_search);
        searchLoc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    geoLocate();
                }
                return false;
            }
        });
    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");
        String searchString = searchLoc.getText().toString();
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }
        if (addressList.size() > 0) {
            Address address = addressList.get(0);
            LatLng newLatLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 15));

        }
    }

    public void tambahKegiatan() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapClicked.booleanValue() == true) {
                    startActivity(intent);
                }
                if (mapClicked.booleanValue() == false) {
                    Toast.makeText(MapsActivity.this, "Pilih Lokasi Terlebih Dahulu",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void markerKegiatan() {
        latLng = db.get_latlng();
        if (latLng.size() > 0) {
            for (int i = 0; i < latLng.size(); i++) {
                mMap.addMarker(new MarkerOptions().position(latLng.get(i)).title("Ada Agenda")
                        .icon(BitmapUtils.BitmapFromVector(getApplicationContext(), R.drawable.ic_baseline_emergency_24)));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mMap != null) {
            markerKegiatan();
        }
    }
}