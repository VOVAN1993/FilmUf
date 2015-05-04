package com.example.Start.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.Comment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CommentAdapter extends SimpleAdapter{
    private final ArrayList<Comment> likes;
    private final ArrayList<Comment> dislikes;

    public CommentAdapter(ArrayList<Comment> likes,ArrayList<Comment> dislikes, Context context,
                          List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.likes = likes;
        this.dislikes = dislikes;
    }

    @Override
    public void setViewText(TextView v, String text) {
        super.setViewText(v, text);
        if (v.getId() == R.id.cLikeNum) {
            LinearLayout parent = (LinearLayout) v.getParent().getParent().getParent();
            TextView invisibleTV = ((TextView) parent.findViewById(R.id.invisibleTV));

            RelativeLayout rl1 = (RelativeLayout) parent.findViewById(R.id.cUserInfo);
            RelativeLayout rl2 = (RelativeLayout) rl1.findViewById(R.id.cUserInfo);
            RelativeLayout rl3 = (RelativeLayout) rl2.findViewById(R.id.vova);


            if(contains(invisibleTV.getText().toString(), likes)){
                ImageView viewById = (ImageView) rl3.findViewById(R.id.cLike);
                if(!viewById.isSelected() && viewById.getVisibility() == View.VISIBLE){

                    Log.d(BasicUtil.LOG_TAG, "like , " + invisibleTV.getText() + " "  + viewById.hashCode());
                    viewById.setSelected(true);
                }

            }
        }
        if(v.getId() == R.id.cDislikeNum){
            LinearLayout parent = (LinearLayout) v.getParent().getParent().getParent();
            TextView invisibleTV = ((TextView) parent.findViewById(R.id.invisibleTV));
            if(contains(invisibleTV.getText().toString(), dislikes)){
                ImageView viewById11 = (ImageView) ((RelativeLayout) v.getParent()).findViewById(R.id.cDislike);
                if(!viewById11.isSelected()){
                    Log.d(BasicUtil.LOG_TAG, "dislike , " + invisibleTV.getText() + " " + viewById11.hashCode());
                    viewById11.setSelected(true);
                }

            }
        }

    }

    private static boolean contains(String pk, Collection<Comment> collection){
        for(Comment c : collection){
            if(c.pk.equals(pk))return true;
        }
        return false;
    }
}
