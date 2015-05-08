package com.example.Start.adapter;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.util.BasicUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ExpListAdapter extends BaseExpandableListAdapter {

    public Map<Integer, Boolean> states = new TreeMap<>();
    public Map<String,Map<Integer,String>> result = new TreeMap<>();
    public Map<Integer, String> mygroup = new TreeMap<>();

    private Integer generateKey(int x, int y) {
        return new Integer(10000 * x + y);
    }

    private ArrayList<ArrayList<String>> mGroups;
    private Context mContext;
    private List<String> nameGroups;

    public ExpListAdapter(Context context, ArrayList<ArrayList<String>> groups, List<String> nameGroups) {
        mContext = context;
        mGroups = groups;
        this.nameGroups = nameGroups;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

//        if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.my_parent_check, null);
//        }


        if (isExpanded) {
//            Log.d(BasicUtil.LOG_TAG, "isExpanded " + groupPosition);

            int childrenCount = getChildrenCount(groupPosition);
            for (int i = 0; i < childrenCount; i++) {
                View childAt = ((ExpandableListView) parent).getChildAt(groupPosition);
//                if(child.isChecked()) {
//                    child.toggle();
//                }
            }
            //Изменяем что-нибудь, если текущая Group раскрыта
        } else {
//            Log.d(BasicUtil.LOG_TAG, "not isExpanded " + groupPosition);

            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(android.R.id.text1);
        String id = nameGroups.get(groupPosition);
        textGroup.setText(id);
        if (!result.containsKey(id)){
            mygroup.put(groupPosition, id);
            result.put(id, new TreeMap<Integer, String>());
        }
        return convertView;

    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
//        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.my_check, null);
//        }

        CheckBox cb = (CheckBox) convertView.findViewById(R.id.cb);
        TextView tw = (TextView) convertView.findViewById(R.id.tw);

        if(states.containsKey(generateKey(groupPosition,childPosition)) && states.get(generateKey(groupPosition,childPosition))){
            Log.d(BasicUtil.LOG_TAG, "Set in if group = " + groupPosition + "||||  child = " + childPosition);
            cb.setChecked(true);
        }
        tw.setText(mGroups.get(groupPosition).get(childPosition));
        result.get(mygroup.get(groupPosition)).put(childPosition, mGroups.get(groupPosition).get(childPosition));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(BasicUtil.LOG_TAG, "click");
                ViewGroup viewGroup = (ViewGroup)view;
                CheckBox viewById = (CheckBox) view.findViewById(R.id.cb);
                if(!viewById.isChecked()) {
                    viewById.setChecked(true);

                    Log.d(BasicUtil.LOG_TAG, "set group = " + groupPosition + "||||  child = " + childPosition);
                    states.put(generateKey(groupPosition, childPosition), true);
                } else{
                    states.put(generateKey(groupPosition, childPosition), false);
                    Log.d(BasicUtil.LOG_TAG, "UnSET group = " + groupPosition + "||||  child = " + childPosition);
                    viewById.setChecked(false);
                }
//                CheckedTextView ct = (CheckedTextView)view;
//                ct.setChecked(true);

//                if (ct.isChecked()) {
//                    ct.setTextColor(Color.WHITE);
//                } else {
//                    ct.setTextColor(Color.GRAY);
//                }
            }
        });
//        cb.toggle();

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}