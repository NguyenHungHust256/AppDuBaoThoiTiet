package com.example.ba_hung.dubaothoitiet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by ba-hung on 11/12/2017.
 */

public class ThoiTietAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> datas;

    public ThoiTietAdapter(Context context, ArrayList<ThoiTiet> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.custom_listview_item, null);

        ThoiTiet thoiTiet = datas.get(position);

        TextView txtDay = view.findViewById(R.id.txtNgay);
        TextView txtTrangThai = view.findViewById(R.id.txtTrangThai);
        TextView txtNhietDoMax = view.findViewById(R.id.txtMaxTemp);
        TextView txtNhietDoMin = view.findViewById(R.id.txtMinTemp);
        ImageView imgIcon = view.findViewById(R.id.imgIconMoiNgay);

        txtDay.setText(thoiTiet.getDay());
        txtTrangThai.setText(thoiTiet.getStatus());
        txtNhietDoMax.setText(thoiTiet.getMaxTemp());
        txtNhietDoMin.setText(thoiTiet.getMinTemp());
        Glide.with(context).load("http://openweathermap.org/img/w/"+thoiTiet.getImage()+".png").into(imgIcon);
        return view;
    }
}
