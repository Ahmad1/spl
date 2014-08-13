package com.feellike.split.util;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.feellike.split.R;

/**
 * Created by root on 7/2/14.
 */
public class ItemButtonLongClickListener implements View.OnLongClickListener {

    private Activity mActivity;
    private boolean hasPerson;

    public ItemButtonLongClickListener(Activity c, boolean hasPerson) {
        this.mActivity = c;
        this.hasPerson = hasPerson;
    }

    @Override
    public boolean onLongClick(View v ) {
        if (hasPerson) {
            v.setTag("selected");
            v.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.btn_default_long_pressed));
            Button b = (Button) v;
            b.setTextColor(Color.BLACK);
            ClipData data = ClipData.newPlainText("label", "Hey, I'm moving");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v , 0);
        } else {
            Toast.makeText(mActivity, "Add at least one person", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
