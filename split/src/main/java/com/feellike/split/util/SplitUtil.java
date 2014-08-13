package com.feellike.split.util;

/**
 * Created by root on 8/11/14.
 */
public class SplitUtil {
    private PersonAndItems[] peopleAndItemsArray;
    private double subTotal, taxService, tip;

    public PersonAndItems[] getPeopleAndItemsArray() {
        return peopleAndItemsArray;
    }

    public void setPeopleAndItemsArray(PersonAndItems[] peopleAndItemsArray) {
        this.peopleAndItemsArray = peopleAndItemsArray;
    }

    public SplitUtil(PersonAndItems[] peopleAndItemsArray) {
        this.peopleAndItemsArray = peopleAndItemsArray;
    }

    public PersonAndItems[] doMath(){
        // TODO: add calculated values with tag and show it to user from activity.
        return peopleAndItemsArray;
    }
    /*
    on "done" click generate this class to do the math
    gets person name and charges
    adds up the items for each person
    compares sum total with values entered by user
    splits the whole thing and shows in highlights:
    -total per person
    -tip
    -services/ tax amount
    in each column
     */
}
