package com.example.snowtamair;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.maps.MapboxMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PisteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PisteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PisteFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NUM_TRACK = "numTrack";
    private static final String CONDITION_TRACK = "conditionTrack";

    private String mNum;
    private String mCondition;

    private OnFragmentInteractionListener mListener;

    public PisteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param num Parameter 1.
     * @param condition Parameter 2.
     * @return A new instance of fragment PisteFragment.
     */
    public static PisteFragment newInstance(String num, String condition) {
        PisteFragment fragment = new PisteFragment();
        Bundle args = new Bundle();
        args.putString(NUM_TRACK, num);
        args.putString(CONDITION_TRACK, condition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNum = getArguments().getString(NUM_TRACK);
            mCondition = getArguments().getString(CONDITION_TRACK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_piste, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_piste, container, false);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onMapReady(@NonNull MapboxMap mapboxMap);

        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
