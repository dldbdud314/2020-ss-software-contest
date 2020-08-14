package com.example.capstone;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.kakao.util.maps.helper.Utility.getKeyHash;

public class MapsActivity extends AppCompatActivity {
    public MapView mMapView;
    private MapReverseGeoCoder reverseGeoCoder;
    private static String IP_ADDRESS = "220.69.170.218";
    private static String API_KEY = "655ccf784fd4041b5642f94d0e115647";
    String store_name = "";
    String store_add = "";

    double lat, longi;

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private View mCalloutBalloon;
        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((TextView) mCalloutBalloon.findViewById(R.id.balloon_storeName)).setText(store_name);
            ((TextView) mCalloutBalloon.findViewById(R.id.balloon_storeAddress)).setText(store_add);

            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_page);

        Intent intent = getIntent();
        store_name = intent.getExtras().getString("store_name");

        getKeyHash(getApplicationContext());
        mMapView = new MapView(this);
        ViewGroup mapViewContainer = findViewById(R.id.map);

        mapViewContainer.addView(mMapView);

        locationDB ldb = new locationDB();
        ldb.execute();
    }

    public class locationDB extends AsyncTask<Void, Integer, Void> {
        String data = "";
        int count=0;
        @Override
        protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "store_name=" + store_name +  "";
            try {
                /* 서버연결 */
                URL url = new URL(
                        "http://" + IP_ADDRESS + "/findlocation.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                /* 안드로이드 -> 서버 파라메터값 전달 */
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

                /* 서버 -> 안드로이드 파라메터값 전달 */
                InputStream is = null;
                BufferedReader in = null;

                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    buff.append(line + "\n");
                    count++;
                }
                data = buff.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /* 서버에서 응답 */
            int len=data.length();
            StringBuffer checkBf= new StringBuffer();
            int countChk=0;
            count--;
            for(int i=0; i<len; i++){
                if(data.charAt(i)=='\n'){
                    countChk++;
                }
                if(countChk==count){
                    checkBf.append(data.charAt(i));
                }
            }
            String check = checkBf.toString().trim();
            if(check.equals("e")){
                Log.e("Fail","값 넘어오지 않음!");
            } else {
                String[] array = check.split(",");
                lat = Double.parseDouble(array[0]);
                longi = Double.parseDouble(array[1]);

                Log.e("lat",Double.toString(lat));
                Log.e("longi",Double.toString(longi));

                reverseGeoCoder = new MapReverseGeoCoder(API_KEY, MapPoint.mapPointWithGeoCoord(lat, longi), new MapReverseGeoCoder.ReverseGeoCodingResultListener() {
                    @Override
                    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
                        store_add = s;
                        Log.e("add", "#" + s);
                    }
                    @Override
                    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
                        store_add = "주소를 찾을 수 없습니다.";
                        Log.e("add", store_add);
                    }
                }, MapsActivity.this);

                reverseGeoCoder.startFindingAddress();

                mMapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(store_name);
                marker.setUserObject(store_add);
                marker.setTag(0);
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, longi));
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                mMapView.addPOIItem(marker);

                mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat, longi), true);
                mMapView.setZoomLevel(1, true);

                reverseGeoCoder.cancelFindingAddress();
            }
        }
    }
}