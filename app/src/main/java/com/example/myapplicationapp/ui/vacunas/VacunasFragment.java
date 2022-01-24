package com.example.myapplicationapp.ui.vacunas;

import android.location.Address;

import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.myapplicationapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class VacunasFragment extends Fragment implements OnMapReadyCallback {

    private SearchView mSearchView;
    private GoogleMap map;

    public VacunasFragment() {
        //constructor required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vacunas, container, false);

        mSearchView = view.findViewById(R.id.sv_location);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = mSearchView.getQuery().toString();
                List<Address> addressList = null;
                if (!location.equals("")) {
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (
                            IOException ioException) {
                        ioException.printStackTrace();
                    }
                    assert addressList != null;

                    for (Address address : addressList) {
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng).title(location));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                    }

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}