package com.example.Start.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.adapter.ExpListAdapter;
import com.example.Start.util.BasicUtil;
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
    String[] mygroups = new String[]{"Жанр", "Страна"};

    // названия телефонов (элементов)
    String[] genres = new String[]{"Comedy", "Thriller", "Action", "Drama"};
    String[] countries = new String[]{"Russia", "USA", "Japan"};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exp_activity);

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
        listView.setIndicatorBoundsRelative(width - GetPixelFromDips(98), width - GetPixelFromDips(46));
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

        setListViewHeightBasedOnChildren(listView);
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.MATCH_PARENT, View.MeasureSpec.EXACTLY);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY);
//        listView.measure(widthMeasureSpec, heightMeasureSpec);
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
                EditText viewById = (EditText) findViewById(R.id.search_line);
                String name = editText.getText().toString();
                editText.setText("");
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

                ArrayList<Map<String, String>> search ;
                if(name.equals("") && ans.isEmpty()){
                    search = search(null);
                }else{
                    TreeSet<String> t = new TreeSet<String>();
                    t.add(name);
                    if(!name.equals("")) {
                        ans.put("name", t);
                    }
                    TreeSet<String> t1 = new TreeSet<String>();

                    t1.add(String.valueOf(selectedMinValue) + "," + String.valueOf(selectedMaxValue));
                    ans.put("years", t1);
                    search = search(ans);
                }
                if(search ==null || search.size()==0) return;
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

    private ArrayList<Map<String, String>> search(Map<String, Set<String>> map) {
        if(map == null){
            return NetworkUtil.requestToMyServer("http://109.234.36.127:8000/dasha/getFilmByCountry/USA" );
        }
        if(map.containsKey("name")){
            ArrayList<Map<String, String>> film = NetworkUtil.requestToMyServer("http://109.234.36.127:8000/dasha/getFilmSmart?name_rus=" + map.get("name").iterator().next());
            if(film == null || film.isEmpty()){
                film = NetworkUtil.requestToMyServer("http://109.234.36.127:8000/dasha/getFilmSmart?name=" + map.get("name").iterator().next());
                return film;
            }
            return film;
        }

        String base  = "http://109.234.36.127:8000/dasha/getFilmSmart";
        if(map.containsKey("Страна")){
            if(base.charAt(base.length()-1)=='t'){
                base+="?";
            }
            base+="countries=";
            for(String c:map.get("Страна")){
                base+=c+',';
            }
            base = base.substring(0,base.length()-1);
            base +="&";
        }
        if(map.containsKey("Жанр")){
            if(base.charAt(base.length()-1)=='t'){
                base+="?";
            }
            base+="genres=";
            for(String c:map.get("Жанр")){
                base+=c+',';
            }
            base = base.substring(0,base.length()-1);
            base +="&";
        }
        if(map.containsKey("years")){
            if(base.charAt(base.length()-1)=='t'){
                base+="?";
            }
            base+="years=" + map.get("years").iterator().next();
            base +="&";
        }
        return NetworkUtil.requestToMyServer(base.substring(0,base.length()-1));
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

    @Override
    public void onBackPressed() {
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

        float v = BasicUtil.dipToPixels(this, dpHeight/3.3f);
        params.height = (int) v;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
