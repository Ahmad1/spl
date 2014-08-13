package com.feellike.split.util;

import java.util.ArrayList;

/**
 * Created by root on 7/2/14.
 */
public class PersonAndItems {
    private int index;
    private String name ;
    private ArrayList<String> itemsList ;

    public PersonAndItems(int index, String name, ArrayList<String> itemsList) {
        this.index = index;
        this.name = name;
        this.itemsList = itemsList;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getIndex() {
        return index;
    }

    /*public void setIndex(int index) {
        this.index = index;
    }*/

    public ArrayList<String> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ArrayList<String> itemsList) {
        this.itemsList = itemsList;
    }
}
