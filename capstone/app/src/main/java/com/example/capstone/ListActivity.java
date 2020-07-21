package com.example.capstone;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import androidx.appcompat.app.AppCompatActivity;

    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import android.text.method.ScrollingMovementMethod;
    import android.util.Log;
    import android.view.View;
    import android.widget.TextView;
    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;
    import java.io.BufferedReader;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.OutputStream;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "220.69.170.218";
    private static String TAG = "phptest";

    private TextView mTextViewResult;
    private ArrayList<StoreData> mArrayList;
    private ListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;
    private TextView subText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_page);

        Intent intent = getIntent();
        int sitResult = intent.getIntExtra("sit", 0);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        subText = (TextView)findViewById(R.id.subText);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new ListAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        GetData task;

        switch (sitResult){
            case 1:
                task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/list1.php", "");
                subText.setText("짧은 공강시간에 혼밥");
                break;
            case 2:
                task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/list2.php", "");
                subText.setText("수고한 나를 위한 혼밥");
                break;
            case 3:
                task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/list3.php", "");
                subText.setText("짧은 공강시간에 친구와");
                break;
            case 4:
                task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/list4.php", "");
                subText.setText("기분이 좋은 날 친구와");
                break;
            case 5:
                task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/list5.php", "");
                subText.setText("위로가 필요한 날 친구와");
                break;
            case 6:
                task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/list6.php", "");
                subText.setText("애인과 분위기 좋은 맛집");
                break;
            case 7:
                task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/list7.php", "");
                subText.setText("부모님 취향저격 맛집");
                break;
            case 8:
                task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/list8.php", "");
                subText.setText("왁자지껄 술자리");
                break;
        }
    }

    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ListActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){
                mTextViewResult.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }
        }
    }

    private void showResult(){

        String TAG_JSON="webnautes";
        String TAG_NAME = "name";
        String TAG_CATEGORY = "category";
        String TAG_PRICE ="price";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_NAME);
                String category = item.getString(TAG_CATEGORY);
                int price = item.getInt(TAG_PRICE);

                StoreData storeData = new StoreData();

                storeData.setStore_name(name);
                storeData.setStore_category(category);
                storeData.setStore_price(price);

                mArrayList.add(storeData);
                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

    }

}