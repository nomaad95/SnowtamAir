package com.example.snowtamair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mapbox.mapboxsdk.maps.MapboxMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RunwayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RunwayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RunwayFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NUM_TRACK = "numTrack";
    private static final String CONDITION_TRACK = "conditionTrack";

    private String mNum;
    private String mCondition;

    private Button btnDialogCodeSnowTam;

    private OnFragmentInteractionListener mListener;

    public RunwayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param num Parameter 1.
     * @param condition Parameter 2.
     * @return A new instance of fragment RunwayFragment.
     */
    public static RunwayFragment newInstance(String num, String condition) {
        RunwayFragment fragment = new RunwayFragment();
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
        //return inflater.inflate(R.layout.fragment_runway, container, false);
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_runway, container, false);

        /* Init dialog btn
        btnDialogCodeSnowTam = rootView.findViewById(R.id.btn_dialog_snowtam);
        btnDialogCodeSnowTam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(rootView.getContext())
                        .setTitle("Code SnowTam")
                        .setMessage(snowtamCode)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })
                        //.setNegativeButton(android.R.string.no, null)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                Log.d(snowtamCode, "onClick CODE SNOW TAM: ");
            }
        });

         */

        /*
        // Set Time
        textViewTime = findViewById(R.id.airport_time);
        String time = "" + airportObject.getTimezone();
        textViewTime.setText(time);
        Log.d(airportObject.getTz_database_time_zone(), "onCreateView TIME : ");
         */

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
