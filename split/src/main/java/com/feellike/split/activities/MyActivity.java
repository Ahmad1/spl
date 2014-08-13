package com.feellike.split.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


import com.feellike.split.R;
import com.feellike.split.util.DisplayMetricsUtil;
import com.feellike.split.util.FloatLabelUtil;
import com.feellike.split.util.ItemButtonLongClickListener;
import com.feellike.split.util.MyDragEventListener;
import com.feellike.split.util.PersonAndItems;
import com.feellike.split.util.PersonContainerDragListener;
import com.feellike.split.util.MyDialogListener;
import com.feellike.split.util.NumPadDialog;


public class MyActivity extends Activity implements View.OnClickListener, MyDialogListener, MyDragEventListener, CompoundButton.OnCheckedChangeListener{

    private FloatLabelUtil subTotal, taxService, tip, numberOfPeople;
    private Button addItem, addPerson, done;
    private HorizontalScrollView itemsScrollView;
    private LinearLayout itemsListLinearLayout;
    private RelativeLayout initLayoutContainer;
    private View items, people;
    private CheckBox shareAll;
    private String[] mItemsArray;
    private PersonAndItems[] peopleAndItemsArray;
    private int itemCounter = 0;
    private int PersonCounter = 0;
    private int arraySize = 10;
    private int peopleArraySize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PeopleAndChargesFragment())
                    .commit();
        }*/

        initLayoutContainer = (RelativeLayout) findViewById(R.id.init_layout_container);
        subTotal = (FloatLabelUtil) findViewById(R.id.sub_total_et);
        taxService = (FloatLabelUtil) findViewById(R.id.tax_service_et);
        tip = (FloatLabelUtil) findViewById(R.id.tip_et);
        numberOfPeople = (FloatLabelUtil) findViewById(R.id.number_of_people);
        addItem = (Button) findViewById(R.id.add_item);
        addPerson = (Button) findViewById(R.id.add_person);
        done = (Button) findViewById(R.id.done);
        shareAll = (CheckBox) findViewById(R.id.checkbox);

        itemsScrollView = (HorizontalScrollView) findViewById(R.id.items_scrollview);
        itemsScrollView.setSmoothScrollingEnabled(true);
        itemsListLinearLayout = (LinearLayout) findViewById(R.id.items_linearlayout);
        items = findViewById(R.id.items_from_bill);
        people = findViewById(R.id.people_container);

        addItem.setOnClickListener(this);
        addPerson.setOnClickListener(this);
        shareAll.setOnCheckedChangeListener(this);

        /*View.OnClickListener mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNumPadForcharge();
                v.setTag("waitingForText");
            }
        };

        subTotal.setOnClickListener(mClickListener);
        taxService.setOnClickListener(mClickListener);
        tip.setOnClickListener(mClickListener);
        numberOfPeople.setOnClickListener(mClickListener);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_item:
                startNumPadForItem();
                break;
            case R.id.add_person:
                getPersonName();
                break;
        }
    }

    private void getPersonName(){
        AlertDialog.Builder nameAlert = new AlertDialog.Builder(this);
        nameAlert.setTitle("Enter Name");
        final EditText input = new EditText(this);
        nameAlert.setView(input);
        nameAlert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameToAdd = input.getText().toString();
                updatePeopleAndItemsList(nameToAdd);
                drawPeopleAndCharges(peopleAndItemsArray);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });
        nameAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });
        nameAlert.show();
    }

    private void startNumPadForItem(){
        NumPadDialog dialog = new NumPadDialog();
        dialog.show(this.getFragmentManager(),
                "MyDialogFragment");
    }

    private void startNumPadForcharge(){
        NumPadDialog dialog = new NumPadDialog();
        Bundle args = new Bundle();
        args.putBoolean("isEditText", true);
        dialog.setArguments(args);
        dialog.show(this.getFragmentManager(),
                "MyDialogFragment");
    }

    @Override
    public void OnDialogEnterPressed(String charge, boolean isItEditText) {
        if (!isItEditText) {
            updateItemsList(charge);
        } /*else {
            if (initLayoutContainer != null) {
                Log.i("ahmad", "count: "+ initLayoutContainer.getChildCount());
                for (int i = 0 ; i< initLayoutContainer.getChildCount(); i++){
                    if (initLayoutContainer.getChildAt(i).getTag() != null){
                        Log.i("ahmad", "tag: "+ initLayoutContainer.getChildAt(i).getTag());
                        if (initLayoutContainer.getChildAt(i).getTag().equals("waitingForText")){
                        FloatLabelUtil fl = (FloatLabelUtil) initLayoutContainer.getChildAt(i);
                        fl.getEditText().setText(charge);
                        fl.setTag(null);
                        break;
                        }
                    }
                }
            }
            initLayoutContainer.requestFocus();
        }*/
    }

    public void updateItemsList(String charge){
        if (mItemsArray != null){
            if (itemCounter > arraySize -2){
                mItemsArray = doubleTheArraySize(mItemsArray);
            }
            mItemsArray[itemCounter] = charge;
            addNewItem(mItemsArray);
            itemCounter++;
        } else {
            mItemsArray = new String[arraySize];
            mItemsArray[0] = charge;
            addNewItem(mItemsArray);
            itemCounter++;
        }
    }

    private void updatePeopleAndItemsList(String nameToAdd){
        if (peopleAndItemsArray != null) {
            PersonAndItems newPerson = new PersonAndItems(PersonCounter, nameToAdd, new ArrayList<String>());
            peopleAndItemsArray[PersonCounter] = newPerson;
            PersonCounter++;
            Log.i("ahmad", PersonCounter + ": PersonCounter");
        } else {
            peopleAndItemsArray = new PersonAndItems[peopleArraySize];
            PersonAndItems firstPerson = new PersonAndItems(PersonCounter, nameToAdd, new ArrayList<String>(/*Arrays.asList("16.25", "5.99", "40.99")*/));
            peopleAndItemsArray[PersonCounter] = firstPerson;
            PersonCounter++;

            if (itemsListLinearLayout.getChildCount() > 0) {
                for (int i = 0; i < itemsListLinearLayout.getChildCount(); i++) {
                    itemsListLinearLayout.getChildAt(i).setOnLongClickListener(
                            new ItemButtonLongClickListener(this, true)
                    );
                }
            }
            Log.i("ahmad", PersonCounter + ": PersonCounter" + "\n"
                + " ...itemsLinearLayout Child Count:  " + itemsListLinearLayout.getChildCount());
        }
    }

    private void drawPeopleAndCharges(PersonAndItems[] personToAdd) {
        for (int i = 0 ; i < PersonCounter; i++){
            if (personToAdd[i].getItemsList() != null) {
                for (String s : personToAdd[i].getItemsList()){
                    Log.i("ahmad", personToAdd[i].getName() + ": " + s);
                }
            } else {
                Log.i("ahmad", personToAdd[i].getName() + ": Items null");
            }
        }
        // TODO update the list for person items.
        // inflateListForPerson(); // check if second column add a line in between
        LinearLayout peopleContainer = (LinearLayout) findViewById(R.id.people_linearlayout);
        peopleContainer.removeAllViews();
        for (int i = 0 ; i < PersonCounter; i++) {
            View nameAndItem = getLayoutInflater().inflate(R.layout.person_item_list, peopleContainer, false);
            Button name = (Button) nameAndItem.findViewById(R.id.person_name);
            LinearLayout personItemContainer = (LinearLayout) nameAndItem.findViewById(R.id.person_item_container);
            name.setText(personToAdd[i].getName());
            peopleContainer.addView(nameAndItem);
            if (personToAdd[i].getItemsList().size() > 0) {
                for (String s : personToAdd[i].getItemsList()){
                    TextView pItem = new TextView(this);
                    pItem.setText(s);
                    pItem.setGravity(Gravity.CENTER);
                    pItem.setPadding(4, 4, 4, 4);
                    int textHeight = new DisplayMetricsUtil(this).convertDpToPixel(30);
                    pItem.setHeight(textHeight);// convert to dp
                    personItemContainer.addView(pItem);
                }
            }
            nameAndItem.setOnDragListener(new PersonContainerDragListener(this, this));
        }

        peopleContainer.setVisibility(View.VISIBLE);

    }

    private String[] doubleTheArraySize(String[] itemsList) {
        int newSize = itemsList.length *2;
        String[] newItemList = new String[newSize];
        for(int i=0 ; i<itemsList.length-1 ; i++){
            newItemList[i] = itemsList[i];
        }
        return newItemList;
    }

    public void addNewItem(String[] mItemsList){
        boolean invisible = true;
        if (mItemsList!= null && itemsListLinearLayout != null){
            itemsListLinearLayout.removeAllViews();
            for (String s : mItemsList){
                // TODO make "0" valid value
                if (!TextUtils.isEmpty(s) && !s.equals("0.00")){
                    addButton(s);
                    if (invisible){
                        itemsListLinearLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    itemsScrollView.smoothScrollTo(itemsListLinearLayout.getRight(), itemsListLinearLayout.getTop());
                }
            }, 200);

        }
    }

    private void addButton(String charge) {
        Button newButton = new Button(this);
        // TODO validate number
        newButton.setText(charge);
        newButton.setLayoutParams(new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        newButton.setOnLongClickListener(new ItemButtonLongClickListener(this, PersonCounter > 0));
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO openRemoveItemDialog();
            }
        });
        itemsListLinearLayout.addView(newButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDroppedItem(String name) {
        Log.e("ahmad", "Dropped! name: " + name);
        for (PersonAndItems attendant : peopleAndItemsArray) {
            if (attendant != null ) {
                Log.i("test", attendant.getName());
                if (attendant.getName().equalsIgnoreCase(name)) {
                    for (int i = 0; i < itemsListLinearLayout.getChildCount(); i++) {
                        View v = itemsListLinearLayout.getChildAt(i);
                        if ( v.getTag() != null && v.getTag().equals("selected")) {
                            v.setVisibility(View.GONE);
                            // remove v.gettext from items.
                            v.setTag("done");
                            // make it animation
                            Button b = (Button)v;
                            for (int j =0; j < itemCounter; j++){
                                if (mItemsArray[j].equals(b.getText())){
                                    mItemsArray[j] = "0.00";
                                    break;
                                }
                            }
                            attendant.getItemsList().add(b.getText().toString());
                            break;
                        }
                    }
                    drawPeopleAndCharges(peopleAndItemsArray);
                }
            }
        }
    }

    @Override
    public void onDroppedItemOutside() {
        for (int i = 0; i < itemsListLinearLayout.getChildCount(); i++) {
            View v = itemsListLinearLayout.getChildAt(i);
            if ( v.getTag() != null && v.getTag().equals("selected")) {
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_default));
                Button b = (Button) v;
                b.setTextColor(Color.WHITE);
                v.setTag("done");
                break;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // make items and people invisible and make a float text visible to prompt for
            // number of people sharing this bill
            items.setVisibility(View.GONE);
            people.setVisibility(View.GONE);
            numberOfPeople.setVisibility(View.VISIBLE);
        } else {
            // make number of people invisible and items and people back on screen.
            items.setVisibility(View.VISIBLE);
            people.setVisibility(View.VISIBLE);
            numberOfPeople.setVisibility(View.GONE);
        }
    }

    // TODO updated Aug 11
    /**
     * Make all text sizes bigger
     * Add Calculation function.
     * adjust dome layouts for phone and tablet switch.
     * add onClick for Items in Items view to edit, remove or share between a number of people.
     * add onCLick for items in person charges to move back to Items.
     * Check out onSaveInstanceState to save activity on rotating device.
     * maybe get rid of floating views and have listener for edit text to show customized num pad.
     * */
}
