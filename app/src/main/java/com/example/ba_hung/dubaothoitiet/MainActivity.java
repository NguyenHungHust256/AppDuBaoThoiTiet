package com.example.ba_hung.dubaothoitiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtSearch;
    private TextView txtThanhPho, txtQuocGia, txtDoAm, txtGio, txtMay, txtNgayCapNhat, txtNhietDo, txtTrangThai;
    private Button btnSearch, btnXemCacNgayTiepTheo;
    private ImageView imgIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        getCurrentWeatherData("Ha Noi");
        btnSearch.setOnClickListener(this);
        btnXemCacNgayTiepTheo.setOnClickListener(this);
    }

    public void getCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=1fbe106423cd558f25e74141f0ad4778";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtThanhPho.setText("Tên thành phố: "+ name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            txtNgayCapNhat.setText(Day);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Glide.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imgIcon);
                            txtTrangThai.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietDo = jsonObjectMain.getString("temp");
                            String doAm = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(nhietDo);
                            String NhietDo = String.valueOf(a.intValue());

                            txtNhietDo.setText(NhietDo);
                            txtDoAm.setText(doAm+"%");

                            JSONObject jsonObjectGio = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectGio.getString("speed");
                            txtGio.setText(gio+"m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClouds.getString("all");
                            txtMay.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtQuocGia.setText("Tên quốc gia : "+country);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void anhXa() {
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        txtThanhPho = (TextView) findViewById(R.id.txtThanhPho);
        txtQuocGia = (TextView) findViewById(R.id.txtQuocGia);
        txtDoAm = (TextView) findViewById(R.id.txtDoAm);
        txtMay = (TextView) findViewById(R.id.txtMay);
        txtGio = (TextView) findViewById(R.id.txtGio);
        txtNgayCapNhat = (TextView) findViewById(R.id.txtNgay);
        txtNhietDo = (TextView) findViewById(R.id.txtNhietDo);
        txtTrangThai = (TextView) findViewById(R.id.txtTrangThai);
        btnSearch = (Button) findViewById(R.id.btnOK);
        btnXemCacNgayTiepTheo = (Button) findViewById(R.id.btnXemCacNgayKhac);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOK){
            String city = edtSearch.getText().toString();
            if(city.equals("")){
                city="Ha Noi";
                getCurrentWeatherData(city);
            } else {
                getCurrentWeatherData(city);
            }

        } else if(v.getId() == R.id.btnXemCacNgayKhac){
            String city = edtSearch.getText().toString();
            Intent intent = new Intent(MainActivity.this, ThoiTiet7NgayActivity.class);
            intent.putExtra("name", city);
            startActivity(intent);
        }
    }
}
