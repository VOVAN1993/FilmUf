package com.example.Start.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.Start.R;
import com.example.Start.adapter.ExpListAdapter;
import com.example.Start.util.BasicUtil;
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

    // названия компаний (групп)
    String[] mygroups = new String[]{"Жанры", "Страна"};

    // названия телефонов (элементов)
    String[] genres = new String[]{"Комедия", "Триллер", "Боевик", "Драма"};
    String[] countries = new String[]{"Россия", "США", "Япония"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);

        // Находим наш list
         listView = (ExpandableListView) findViewById(R.id.elvMain);

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
                System.out.println();
                break;
            case R.id.imgPlusTV:
                ViewGroup layout2 = ((ViewGroup) findViewById(R.id.linearGroupChild));
                if (layout2.findViewById(R.id.twYear) != null) {
                    layout2.removeAllViews();
                    Log.d(BasicUtil.LOG_TAG, "Remove bar");
                } else {
                    bar = getRangeBar();
                    layout2.addView(bar, 0);
                    Log.d(BasicUtil.LOG_TAG, "Add bar");
                }
                break;
        }
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
