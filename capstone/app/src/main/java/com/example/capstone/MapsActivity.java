package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import static com.kakao.util.maps.helper.Utility.getKeyHash;

public class MapsActivity extends AppCompatActivity {
    public MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_page);

        Intent intent = getIntent();

        getKeyHash(getApplicationContext());
        mMapView = new MapView(this);
        ViewGroup mapViewContainer = findViewById(R.id.map);

        mapViewContainer.addView(mMapView);

        mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.591742, 127.021249), true);
        mMapView.setZoomLevel(1, true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.591742, 127.021249));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mMapView.addPOIItem(marker);
    }
}