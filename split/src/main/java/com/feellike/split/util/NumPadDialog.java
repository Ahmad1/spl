package com.feellike.split.util;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.feellike.split.activities.MyActivity;
import com.feellike.split.R;

/**
 * Created by root on 7/1/14.
 */
public class NumPadDialog extends DialogFragment {

    private Button enter_btn, backSpace_btn, one_btn, two_btn, three_btn, four_btn,
    five_btn, six_btn, seven_btn, eight_btn, nine_btn, point_btn, zero_btn;
    private EditText item;
    private StringBuilder itemToAdd;
    private MyDialogListener mListener;

    private boolean isItEditText = false;

    public NumPadDialog() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.num_pad, container,false);

        mListener = (MyActivity) NumPadDialog.this.getActivity();
        item = (EditText) view.findViewById(R.id.item_edit_text);
        itemToAdd = new StringBuilder();

        backSpace_btn = (Button) view.findViewById(R.id.backspace_btn);
        backSpace_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(itemToAdd))
                    item.setText(itemToAdd.deleteCharAt(itemToAdd.length()-1));
            }
        });
        enter_btn = (Button) view.findViewById(R.id.enter_btn);
        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO validate user input .
                String userInput = item.getText().toString();
                if (!TextUtils.isEmpty(userInput) && !userInput.equalsIgnoreCase(".")){
                    String charge = String.format("%.2f", Double.parseDouble(item.getText().toString()));
                    mListener.OnDialogEnterPressed(charge, isItEditText);
                }

                dismiss();
            }
        });
        zero_btn = (Button) view.findViewById(R.id.zero_btn);
        zero_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setText(itemToAdd.append(zero_btn.getText()));
            }
        });
        one_btn = (Button) view.findViewById(R.id.one_btn);
        one_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setText(itemToAdd.append(one_btn.getText()));
            }
        });
        two_btn = (Button) view.findViewById(R.id.two_btn);
        two_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setText(itemToAdd.append(two_btn.getText()));
            }
        });
        three_btn = (Button) view.findViewById(R.id.three_btn);
        three_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setText(itemToAdd.append(three_btn.getText()));
            }
        });
        four_btn = (Button) view.findViewById(R.id.four_btn);
        four_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setText(itemToAdd.append(four_btn.getText()));
            }
        });
        five_btn = (Button) view.findViewById(R.id.five_btn);
        five_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setText(itemToAdd.append(five_btn.getText()));
            }
        });
        six_btn = (Button) view.findViewById(R.id.six_btn);
        six_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setText(itemToAdd.append(six_btn.getText()));
            }
        });
        seven_btn = (Button) view.findViewById(R.id.seven_btn);
        seven_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setText(itemToAdd.append(seven_btn.getText()));
            }
        });
        eight_btn = (Button) view.findViewById(R.id.eight_btn);
        eight_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setText(itemToAdd.append(eight_btn.getText()));
            }
        });
        nine_btn = (Button) view.findViewById(R.id.nine_btn);
        nine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setText(itemToAdd.append(nine_btn.getText()));
            }
        });
        point_btn = (Button) view.findViewById(R.id.point_btn);
        point_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // only one "." accepted
                if (!itemToAdd.toString().contains(point_btn.getText()))
                    item.setText(itemToAdd.append(point_btn.getText()));
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            isItEditText = args.getBoolean("isEditText", false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        DisplayMetricsUtil mMetricsUtil = new DisplayMetricsUtil(getActivity());
        window.setLayout(mMetricsUtil.convertDpToPixel(290)
                ,mMetricsUtil.convertDpToPixel(325));
        window.setGravity(Gravity.CENTER);
    }
}
