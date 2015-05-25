package com.example.Start.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.Comment;
import com.example.Start.util.NetworkUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.example.Start.util.BasicUtil.scaleImage;

public class EstimateAdapter extends SimpleAdapter{

    private Context context;
    private final String clazz;

    public EstimateAdapter( Context context,
                           List<? extends Map<String, ?>> data, int resource, String[] from, int[] to, String clazz) {
        super(context, data, resource, from, to);
        this.context = context;
        this.clazz = clazz;
    }

    @Override
    public void setViewImage(ImageView v, String value) {
        super.setViewImage(v, value);
        String[] split = value.split("end@");
        if(split[1].equals("N/A")){
            v.setImageDrawable(context.getResources().getDrawable(R.drawable.blank_wanted_poster));
        } else{
            v.setImageBitmap(NetworkUtil.getImage(split[1], split[0]));
            scaleImage(v, 220);
        }
    }

}
