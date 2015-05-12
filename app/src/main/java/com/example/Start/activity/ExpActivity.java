package com.example.Start.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.adapter.ExpListAdapter;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.Comment;
import com.example.Start.util.NetworkUtil;
import com.example.Start.util.NetworkUtil;
import com.example.Start.util.RangeSeekBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ExpActivity extends Activity {
    private RangeSeekBar<Integer> bar;
    private ExpListAdapter adapter;
    private ExpandableListView listView;
    EditText editText;


    // названия компаний (групп)
    String[] mygroups = new String[]{"Жанры", "Страна"};

    // названия телефонов (элементов)
    String[] genres = new String[]{"Комедия", "Триллер", "Боевик", "Драма"};
    String[] countries = new String[]{"Россия", "США", "Япония"};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);

        // обрабатываем ввод в строке поиска
        editText = (EditText) findViewById(R.id.search_line);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;
                }
                return handled;
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        // Находим наш list
         listView = (ExpandableListView) findViewById(R.id.elvMain);
        listView.setIndicatorBoundsRelative(width - GetPixelFromDips(100), width - GetPixelFromDips(40));
        //Создаем набор данных для адаптера
        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        ArrayList<String> children1 = new ArrayList<String>(Arrays.asList(genres));
        ArrayList<String> children2 = new ArrayList<String>(Arrays.asList(countries));
        groups.add(children1);
        groups.add(children2);
        //Создаем адаптер и передаем context и список с данными
        adapter = new ExpListAdapter(getApplicationContext(), groups,Arrays.asList(mygroups));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private String createURI(Map<String, String> map) {
        String addr = "http://109.234.36.127:8000/dasha/getFilm/127%20hours";
        return addr;
    }

    public void onClick(View view){
        Log.d(BasicUtil.LOG_TAG, "CLICK");
        switch (view.getId()){
            case R.id.search:
                Integer selectedMaxValue = bar == null ? 2015 :bar.getSelectedMaxValue();
                Integer selectedMinValue = bar == null ? 0 : bar.getSelectedMinValue();
                Map<Integer, Boolean> states = adapter.states;
                Map<Integer, String> mygroup = adapter.mygroup;
                Map<String, Map<Integer, String>> result = adapter.result;

                Map<String, Set<String>> ans = new TreeMap<>();
                for(Map.Entry<Integer,Boolean> entry : states.entrySet()){
                    if(entry.getValue()){
                        int x = entry.getKey() / 10000;
                        int y = entry.getKey() % 10;
                        String group = mygroup.get(x);
                        String ap = result.get(group).get(y);
                        if(!ans.containsKey(group)){
                            ans.put(group, new TreeSet<String>());
                        }
                        ans.get(group).add(ap);
                    }
                }
                ListFilmsActivity.map.clear();
                ArrayList<Map<String, String>> search = search(null);
                ListFilmsActivity.map.put("map", search);
                MainTabActivity.tabs.setCurrentTab(4);
                break;
            case R.id.imgPlusTV:
                ViewGroup layout2 = ((ViewGroup) findViewById(R.id.linearGroupChild));
                ImageView ivRangeArr = ((ImageView) findViewById(R.id.ivRangeParent));
                if (layout2.findViewById(R.id.twYear) != null) {
                    layout2.removeAllViews();
                    ivRangeArr.setImageResource(R.drawable.arr_down64);
                    Log.d(BasicUtil.LOG_TAG, "Remove bar");
                } else {
                    ivRangeArr.setImageResource(R.drawable.arr_up64);
                    bar = getRangeBar();
                    layout2.addView(bar, 0);
                    Log.d(BasicUtil.LOG_TAG, "Add bar");
                }
                break;
        }
    }

    private ArrayList<Map<String, String>> search(Map<String, String> map) {
        return NetworkUtil.requestToMyServer("http://109.234.36.127:8000/dasha/getFilmByCountry/USA");
    }

    private RangeSeekBar<Integer> getRangeBar() {
        if (bar != null) {
            return bar;
        } else {
            bar = new RangeSeekBar<Integer>(this);
            bar.setId(R.id.twYear);
            bar.setRangeValues(1920, 2015);
            bar.setSelectedMinValue(1990);
            bar.setSelectedMaxValue(2010);
            return bar;
        }
    }
}
