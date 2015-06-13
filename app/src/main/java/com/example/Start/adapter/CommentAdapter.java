package com.example.Start.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.Comment;
import com.example.Start.util.NetworkUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.example.Start.util.BasicUtil.scaleImage;

public class CommentAdapter extends SimpleAdapter {
    private final List<Comment> likes;
    private final List<Comment> dislikes;
    private final String clazz;

    private Context context;

    public CommentAdapter(List<Comment> likes, List<Comment> dislikes, Context context,
                          List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,
                          String clazz) {
        super(context, data, resource, from, to);
        this.likes = likes;
        this.dislikes = dislikes;
        this.context = context;
        this.clazz = clazz;
    }

    @Override
    public void setViewText(TextView v, String text) {
        super.setViewText(v, text);
        if (v.getId() == R.id.cLikeNum) {
            RelativeLayout parent = (RelativeLayout) v.getParent();
            TextView invisibleTV = ((TextView) parent.findViewById(R.id.cInvisibleTVCommentPK));

            if (contains(invisibleTV.getText().toString(), likes)) {
                ImageView viewById = (ImageView) parent.findViewById(R.id.cLike);
                if (!viewById.isSelected() && viewById.getVisibility() == View.VISIBLE) {

                    Log.d(BasicUtil.LOG_TAG, "like , " + invisibleTV.getText() + " " + viewById.hashCode());
                    viewById.setSelected(true);
                }

            } else {
                ImageView viewById = (ImageView) parent.findViewById(R.id.cLike);
                viewById.setSelected(false);
            }
            return;

        }

        if (v.getId() == R.id.cDislikeNum) {
            RelativeLayout parent = (RelativeLayout) v.getParent();
            TextView invisibleTV = ((TextView) parent.findViewById(R.id.cInvisibleTVCommentPK));
            if (contains(invisibleTV.getText().toString(), dislikes)) {
                ImageView viewById11 = (ImageView) ((RelativeLayout) v.getParent()).findViewById(R.id.cDislike);
                if (!viewById11.isSelected()) {
                    Log.d(BasicUtil.LOG_TAG, "dislike , " + invisibleTV.getText() + " " + viewById11.hashCode());
                    viewById11.setSelected(true);
                }

            } else {
                ImageView viewById = (ImageView) parent.findViewById(R.id.cDislike);
                viewById.setSelected(false);
            }
            return;
        }
        if (v.getId() == R.id.fLikeNum) {
            RelativeLayout parent = (RelativeLayout) v.getParent();
            TextView invisibleTV = ((TextView) parent.findViewById(R.id.fInvisibleTVCommentPK));

            if (contains(invisibleTV.getText().toString(), likes)) {
                ImageView viewById = (ImageView) parent.findViewById(R.id.cLike);
                if (!viewById.isSelected() && viewById.getVisibility() == View.VISIBLE) {

                    Log.d(BasicUtil.LOG_TAG, "like , " + invisibleTV.getText() + " " + viewById.hashCode());
                    viewById.setSelected(true);
                }

            }
            return;

        }

        if (v.getId() == R.id.fDislikeNum) {
            RelativeLayout parent = (RelativeLayout) v.getParent();
            TextView invisibleTV = ((TextView) parent.findViewById(R.id.fInvisibleTVCommentPK));

            if (contains(invisibleTV.getText().toString(), dislikes)) {
                ImageView viewById = (ImageView) parent.findViewById(R.id.cDislike);
                if (!viewById.isSelected() && viewById.getVisibility() == View.VISIBLE) {
                    Log.d(BasicUtil.LOG_TAG, "like , " + invisibleTV.getText() + " " + viewById.hashCode());
                    viewById.setSelected(true);
                }

            }
            return;

        }

    }

    private static boolean contains(String pk, Collection<Comment> collection) {
        for (Comment c : collection) {
            if (c.pk.equals(pk)) return true;
        }
        return false;
    }

    @Override
    public void setViewImage(ImageView v, String value) {
        super.setViewImage(v, value);
        String[] split = value.split("end@");
        if (split[1].equals("N/A")) {
            v.setImageDrawable(context.getResources().getDrawable(R.drawable.blank_wanted_poster));
        } else {
            v.setImageBitmap(NetworkUtil.getImage(split[1], split[0]));
            if (clazz.equals("film")) {
                scaleImage(v, 150);
            }
            if (clazz.equals("comment")) {
                scaleImage(v, 220);
            }
        }
    }

    @Override
    public void setViewImage(ImageView v, int value) {
        super.setViewImage(v, value);
    }


    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
