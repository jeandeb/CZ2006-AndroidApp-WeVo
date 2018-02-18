package com.example.deversity.wevo.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.deversity.wevo.Entity.VWO;
import com.example.deversity.wevo.R;
import com.example.deversity.wevo.mgr.VolunteerClientMgr;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonGeometry;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * MapTab is a boundary class for showing VWOs on google map
 * @author John; Fu, Yunhao
 */
public class mapTab extends Fragment implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;
    private View mView;
    private ArrayList<String> VWOArrayList = new ArrayList<>();
    private final static int PERMISSION_FINE_LOCATION = 101;
    private static List<MarkerOptions> VWOMarkerList = VolunteerClientMgr.getVWOMarkerList();
    DatabaseReference mVWORef = FirebaseDatabase.getInstance().getReference().child("VWO").child("id");
    private final VolunteerClientMgr volunteerClientMgr = VolunteerView.getVolunteerMgr();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_maptab, container, false);


        mVWORef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                VWOArrayList = new ArrayList<>();
                for (DataSnapshot VWOSnapshot : dataSnapshot.getChildren()) {
                    if (VWOSnapshot.child("name").getValue(String.class) != null)
                        VWOArrayList.add(VWOSnapshot.child("name").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mMapView = (MapView) mView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
        return mView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MarkerOptions marker;
        JSONObject reader;
        JSONObject vwo;
        JSONArray features = null;
        JSONArray coordinates = null;
        JSONObject geometry = null;
        JSONObject properties;
        double lg, lat;
        int j;
        String name;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap = googleMap;
        LatLng Sg = new LatLng(1.350524, 103.815610);
        CameraPosition target = CameraPosition.builder().target(Sg).zoom(10).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));


        try {

            InputStream is = getResources().openRawResource(R.raw.vwo);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            Reader read = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = read.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

            is.close();
            String jsonString = writer.toString();


            reader = new JSONObject(jsonString);
            features = reader.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {

                vwo = features.getJSONObject(i + 2);
                properties = vwo.getJSONObject("properties");
                name = properties.getString("Name");
                geometry = vwo.getJSONObject("geometry");
                coordinates = geometry.getJSONArray("coordinates");
                lat = coordinates.getDouble(1);
                lg = coordinates.getDouble(0);
                marker = new MarkerOptions().position(new LatLng(lat, lg)).title(name);


                for (j = 0; j < VWOArrayList.size(); j++) {

                    if (((String) VWOArrayList.get(j)).equals(name)) {
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_red));
                        volunteerClientMgr.addVWOMarker(lat, lg, name);
                        break;
                    }
                }
                if (j == VWOArrayList.size())
                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_blue));

                mMap.addMarker(marker);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        for(MarkerOptions markerOptions: VWOMarkerList){
            if(markerOptions.getTitle().equals(marker.getTitle())){
                volunteerClientMgr.getShowVWOMgr().setSelectedVWOName(marker.getTitle());
                Intent intent = new Intent(getContext(), VWOView.class);
                intent.putExtra("MODE","VOL");
                intent.putExtra("VWOName",marker.getTitle());
                startActivity(intent);
                Toast.makeText(getContext(),"Visit "+marker.getTitle(),Toast.LENGTH_SHORT).show();
                try {
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getContext(), "This app requires location permissions!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * After getting back from VWO view, clean up all the things just selected in mgr
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    volunteerClientMgr.getShowVWOMgr().setSelectedVWOName("");
                    volunteerClientMgr.getShowVWOMgr().setSelectedEventName("");
                    volunteerClientMgr.getShowVWOMgr().setSelectedJobName("");
                }
        }
    }
}