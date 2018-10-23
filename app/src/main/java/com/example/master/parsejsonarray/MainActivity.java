package com.example.master.parsejsonarray;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String TAG;
    String DataParsed="";
    private Button btn;
    private TextView fetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        btn = (Button)findViewById(R.id.button);
        fetch = (TextView)findViewById(R.id.fetch);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch.setText(DataParsed);
            }
        });
    }

    private void getData() {
        String urlParkingArea = "http://data.ntpc.gov.tw/api/v1/rest/datastore/382000000A-000004-002";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                urlParkingArea,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "response = " + response.toString());
                        parserJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "error : " + error.toString());
                    }
                }
        );
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    private void parserJson(JSONObject jsonObject) {
        try {
            JSONArray JA = jsonObject.getJSONObject("result").getJSONArray("records");
            for (int i = 0; i < JA.length(); i++) {
                JSONObject JO = JA.getJSONObject(i);
                DataParsed += JO;
                Log.d("json",DataParsed);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
