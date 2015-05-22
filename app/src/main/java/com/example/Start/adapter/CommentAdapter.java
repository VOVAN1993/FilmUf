package com.example.Start.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Start.R;
import com.example.Start.activity.MainTabActivity;
import com.example.Start.util.BasicUtil;
import com.example.Start.util.Comment;
import com.example.Start.util.NetworkUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CommentAdapter extends SimpleAdapter{
    private final List<Comment> likes;
    private final List<Comment> dislikes;
    private final String clazz;

    private Context context;

    public CommentAdapter(List<Comment> likes,List<Comment> dislikes, Context context,
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

            if(contains(invisibleTV.getText().toString(), likes)){
                ImageView viewById = (ImageView) parent.findViewById(R.id.cLike);
                if(!viewById.isSelected() && viewById.getVisibility() == View.VISIBLE){

                    Log.d(BasicUtil.LOG_TAG, "like , " + invisibleTV.getText() + " "  + viewById.hashCode());
                    viewById.setSelected(true);
                }

            }else{
                ImageView viewById = (ImageView) parent.findViewById(R.id.cLike);
                    viewById.setSelected(false);
            }
            return;

        }

        if(v.getId() == R.id.cDislikeNum){
            RelativeLayout parent = (RelativeLayout) v.getParent();
            TextView invisibleTV = ((TextView) parent.findViewById(R.id.cInvisibleTVCommentPK));
            if(contains(invisibleTV.getText().toString(), dislikes)){
                ImageView viewById11 = (ImageView) ((RelativeLayout) v.getParent()).findViewById(R.id.cDislike);
                if(!viewById11.isSelected()){
                    Log.d(BasicUtil.LOG_TAG, "dislike , " + invisibleTV.getText() + " " + viewById11.hashCode());
                    viewById11.setSelected(true);
                }

            }else{
                ImageView viewById = (ImageView) parent.findViewById(R.id.cDislike);
                viewById.setSelected(false);
            }
            return;
        }
        if (v.getId() == R.id.fLikeNum) {
            RelativeLayout parent = (RelativeLayout) v.getParent();
            TextView invisibleTV = ((TextView) parent.findViewById(R.id.fInvisibleTVCommentPK));

            if(contains(invisibleTV.getText().toString(), likes)){
                ImageView viewById = (ImageView) parent.findViewById(R.id.cLike);
                if(!viewById.isSelected() && viewById.getVisibility() == View.VISIBLE){

                    Log.d(BasicUtil.LOG_TAG, "like , " + invisibleTV.getText() + " "  + viewById.hashCode());
                    viewById.setSelected(true);
                }

            }
            return;

        }

        if (v.getId() == R.id.fDislikeNum) {
            RelativeLayout parent = (RelativeLayout) v.getParent();
            TextView invisibleTV = ((TextView) parent.findViewById(R.id.fInvisibleTVCommentPK));

            if(contains(invisibleTV.getText().toString(), dislikes)){
                ImageView viewById = (ImageView) parent.findViewById(R.id.cDislike);
                if(!viewById.isSelected() && viewById.getVisibility() == View.VISIBLE){
                    Log.d(BasicUtil.LOG_TAG, "like , " + invisibleTV.getText() + " "  + viewById.hashCode());
                    viewById.setSelected(true);
                }

            }
            return;

        }

    }

    private static boolean contains(String pk, Collection<Comment> collection){
        for(Comment c : collection){
            if(c.pk.equals(pk))return true;
        }
        return false;
    }

    @Override
    public void setViewImage(ImageView v, String value) {
        super.setViewImage(v, value);
        String[] split = value.split("end@");
        if(split[1].equals("N/A")){
            v.setImageDrawable(context.getResources().getDrawable(R.drawable.blank_wanted_poster));
        } else{
            v.setImageBitmap(NetworkUtil.getImage(split[1],split[0]));
            if(clazz.equals("film")) {
                scaleImage(v, 150);
            }
            if(clazz.equals("comment")) {
                scaleImage(v, 220);
            }
        }
    }

    @Override
    public void setViewImage(ImageView v, int value) {
        super.setViewImage(v, value);
    }


    private void scaleImage(ImageView view, int boundBoxInDp)
    {
        // Get the ImageView and its bitmap
        Drawable drawing = view.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();

        // Get current dimensions
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) boundBoxInDp) / width;
        float yScale = ((float) boundBoxInDp) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        width = scaledBitmap.getWidth();
        height = scaledBitmap.getHeight();

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private int dpToPx(int dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}
