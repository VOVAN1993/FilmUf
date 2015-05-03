package com.example.Start.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.adapter.AdapterHelper;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.NetworkUtil;
import com.example.Start.util.RangeSeekBar;

import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends Activity {

    EditText editText;
    ExpandableListView elvMain;
    AdapterHelper ah;
    SimpleExpandableListAdapter adapter;
    private int minValueRangeBar, maxValueRangeBar;
    private RangeSeekBar<Integer> bar;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

//        --------------------------------------------------------
//        obrabatyvaem vvod v stroke poiska
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
//      ----------------------------------------------------------

        // создаем адаптер
        ah = new AdapterHelper(this);
        adapter = ah.getAdapter();

//        linearGroup
        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);
        elvMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                CheckedTextView view1 = (CheckedTextView) view;
                view1.setChecked(true);
                return false;
            }
        });
        elvMain.setAdapter(adapter);


    }

    private ArrayList<Map<String, String>> search(Map<String, String> map) {
        return NetworkUtil.requestToMyServer("http://109.234.36.127:8000/dasha/getFilmByCountry/USA");
    }

    private String createURI(Map<String, String> map) {
        String addr = "http://109.234.36.127:8000/dasha/getFilm/127%20hours";
        return addr;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.search) {
            ArrayList<Map<String, String>> search = search(null);
            Intent intent = new Intent(this, ListFilmsActivity.class);
            if (search.size() > 0) {
                intent.putExtra("map", search);
                intent.putExtra("Result", "Success");
            } else {
                intent.putExtra("Result", "Bad");
            }
            startActivityForResult(intent, 1);
        }
        if (view.getId() == R.id.imgPlusTV) {
            ViewGroup layout2 = ((ViewGroup) findViewById(R.id.linearGroupChild));
            if (layout2.findViewById(R.id.twYear) != null) {
                layout2.removeAllViews();
                Log.d(BasicUtil.LOG_TAG, "Remove bar");
            } else {
                bar = getRangeBar();
                layout2.addView(bar, 0);
                Log.d(BasicUtil.LOG_TAG, "Add bar");
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || !data.getStringExtra("result").equals("Success")) {
            return;
        }
        Log.d(BasicUtil.LOG_TAG, "Show " + data.getStringExtra("film") + "film");
    }
}