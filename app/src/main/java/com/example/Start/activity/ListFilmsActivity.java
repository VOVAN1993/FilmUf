package com.example.Start.activity;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Start.R;
import com.example.Start.util.BasicUtil;

public class ListFilmsActivity extends ListActivity{

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" ,"1111", "22222","33333","4444", "5555"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }





    private TextView twRusName;
    private TextView twEngName;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        twRusName = (TextView) findViewById(R.id.twRusName);
//        twEngName = (TextView) findViewById(R.id.twEngName);
//
//        Intent intent = getIntent();
//        //if null -> not found + exception
//        if(intent.getStringExtra("Result").equals("Success")) {
//            twRusName.setText(intent.getStringExtra("name_rus"));
//            twEngName.setText(intent.getStringExtra("name"));
//        }else{
//            Log.d(BasicUtil.LOG_TAG, "Finish ListFilmsActivity with bad code");
//            intent.putExtra("result", "Bad");
//            setResult(RESULT_CANCELED);
//            finish();
//        }
//
//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("result", "Success");
//                intent.putExtra("film", "127 hours");
//                Log.d(BasicUtil.LOG_TAG, "Finish ListFilmsActivity with OK code");
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
//    }

}
