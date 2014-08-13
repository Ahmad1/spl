package com.feellike.split.util;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;

import com.feellike.split.R;


/**
 * Created by root on 7/2/14.
 */
public class PersonContainerDragListener implements View.OnDragListener {
    private Activity mActivity;
    private MyDragEventListener mListener;

    public PersonContainerDragListener(Activity act, MyDragEventListener mListener){
        this.mActivity = act;
        this.mListener = mListener;
    }
    @Override
    public boolean onDrag(View v, DragEvent event) {
        Button nameBtn = (Button) v.findViewById(R.id.person_name);
        boolean ended = false;
        switch (event.getAction()){
            case (DragEvent.ACTION_DRAG_ENTERED):
                nameBtn.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.btn_default_long_pressed));
                nameBtn.setTextColor(Color.BLACK);

                Log.e("ahmad", "Entered!" + nameBtn.getText());

                break;
            case (DragEvent.ACTION_DRAG_EXITED):
                nameBtn.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.btn_default));
                nameBtn.setTextColor(Color.WHITE);
                Log.e("ahmad", "Exited!" + nameBtn.getText());

                break;
            case (DragEvent.ACTION_DROP):
                // do what you want to do. add to items list, remove from items.
                // if it is shared add to counter and the items list.
                nameBtn.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.btn_default));
                nameBtn.setTextColor(Color.WHITE);
                mListener.onDroppedItem(nameBtn.getText().toString());
                // add item to here and remove from charge list.
                // TODO needs interface listener to notify what happend here and react in charge views.
                Log.e("ahmad", "Dropped!" + nameBtn.getText());
                break;
            case (DragEvent.ACTION_DRAG_ENDED):
                ended = true;
                break;
            // events ENDED and STARTED left out.
        }
        if (ended && !event.getResult()) {
            Log.e("ahmad", "result: " +  event.getResult());
            mListener.onDroppedItemOutside();
        }
        return true;
    }
}
