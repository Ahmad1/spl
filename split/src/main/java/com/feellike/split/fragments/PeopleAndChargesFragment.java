package com.feellike.split.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feellike.split.R;

/**
 * Created by root on 6/24/14.
 */
public class PeopleAndChargesFragment extends Fragment {
    public PeopleAndChargesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        return rootView;
    }
}
