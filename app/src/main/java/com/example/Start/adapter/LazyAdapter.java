package com.example.Start.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.activity.ListFilmsActivity;
import com.example.Start.util.Film;
import com.example.Start.util.ImageCache;
import com.example.Start.util.NetworkUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<Map<String, String>> data;
    private static LayoutInflater inflater=null;
    private ImageCache cache = new ImageCache();

    public LazyAdapter(Activity a, ArrayList<Map<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        Map<String, String> film;
        film = data.get(position);
        
        title.setText(film.get(Film.KEY_NAME));
        artist.setText(film.get(Film.KEY_DIRECTORS));
        duration.setText(film.get(Film.KEY_DELAY));

        if(film.containsKey(Film.KEY_POSTER)){
            Bitmap bitmap = cache.get(film.get(Film.KEY_POSTER));
            if(bitmap==null){
                bitmap = NetworkUtil.getImage(film.get(Film.KEY_POSTER),activity,
                        film.get(Film.KEY_PK));
                cache.put(film.get(Film.KEY_POSTER),bitmap);
            }
            thumb_image.setImageBitmap(bitmap);
        }else{
            thumb_image.setImageResource(R.drawable.blank_wanted_poster);
        }

        return vi;
    }
}