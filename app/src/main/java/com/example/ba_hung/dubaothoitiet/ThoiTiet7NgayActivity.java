package com.example.ba_hung.dubaothoitiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThoiTiet7NgayActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView txtName;
    private ListView lv;
    ThoiTietAdapter thoiTietAdapter;
    ArrayList<ThoiTiet> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoi_tiet7_ngay);
        anhXa();
        khoiTaoDuLieuThoiTiet();
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ketqua", "Du lieu truyen qua : " + city);
        if(city.equals("")){
            city="Ha Noi";
        }
        get7DayData(city);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void khoiTaoDuLieuThoiTiet() {
        datas = new ArrayList<>();
        thoiTietAdapter = new ThoiTietAdapter(ThoiTiet7NgayActivity.this, datas);
        lv.setAdapter(thoiTietAdapter);

    }

    private void anhXa() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtName = (TextView) findViewById(R.id.txtThanhPho);
        lv = (ListView) findViewById(R.id.listview);
    }

    public void get7DayData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&cnt=50&appid=1fbe106423cd558f25e74141f0ad4778";
        RequestQueue requestQueue = Volley.newRequestQueue(ThoiTiet7NgayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ketqua", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");

                            String nameThanhPho = jsonObjectCity.getString("name");
                            txtName.setText(nameThanhPho);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");

                            for(int k=0; k < jsonArrayList.length(); k++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(k);

                                String ngay = jsonObjectList.getString("dt");
                                Long l = Long.valueOf(ngay);
                                Date date = new Date(l*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String Day = simpleDateFormat.format(date);

                                Log.d("ketqua", Day);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                String maxTemp = jsonObjectTemp.getString("temp_max");
                                String minTemp = jsonObjectTemp.getString("temp_min");

                                Double a = Double.valueOf(maxTemp);
                                Double b = Double.valueOf(minTemp);

                                String NhietDoMax = String.valueOf(a.intValue());
                                String NhietDoMin = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");
                                datas.add(new ThoiTiet(Day,status,icon,NhietDoMax, NhietDoMin));
                            }
                            Toast.makeText(ThoiTiet7NgayActivity.this, "datasize "+ datas.size(), Toast.LENGTH_SHORT).show();
                            thoiTietAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
