package com.saibaba.hackathon.PoliceStation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saibaba.hackathon.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Inspector.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Inspector#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Inspector extends Fragment {

    public Inspector() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inspector, container, false);
    }
}