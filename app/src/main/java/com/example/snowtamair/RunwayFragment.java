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
import android.widget.TextView;

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
    private static final String DATE_TRACK = "observationDate";
    private static final String FRICTION_TRACK = "frictionCoefficient";

    private String mNum;
    private String mCondition;
    private String mDate;
    private String mFriction;

    private Runway runwayObject;
    private static String snowtamCode;

    private OnFragmentInteractionListener mListener;

    public RunwayFragment(Runway runway, String snowtam) {
        // Required empty public constructor
        runwayObject = runway;
        Log.d(runwayObject.getId(), "newInstance RUNWAY OBJECT: ");
        snowtamCode = snowtam;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param runway Parameter 1.
     * @return A new instance of fragment RunwayFragment.
     */
    public RunwayFragment newInstance(Runway runway) {
        RunwayFragment fragment = new RunwayFragment(runway, snowtamCode);
        Bundle args = new Bundle();
        Log.d(runway.getId(), "newInstance RUNWAY : ");

        args.putString(NUM_TRACK, runway.getId());
        args.putString(CONDITION_TRACK, runway.getCondition());
        args.putString(DATE_TRACK, runway.getObservationDate(this));
        args.putString(FRICTION_TRACK, runway.getFrictionCoefficient());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNum = getArguments().getString(NUM_TRACK);
            mCondition = getArguments().getString(CONDITION_TRACK);
            mDate = getArguments().getString(DATE_TRACK);
            mFriction = getArguments().getString(FRICTION_TRACK);
            Log.d(mNum, "onCreate ARGS: ");
        }
        //Log.d(runwayObject.getId(), "onCreate RUNWAY : ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_runway, container, false);
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_runway, container, false);
    //  Log.d(runwayObject.getId(), "onCreateView RUNWAY : ");

        // Init dialog btn
        Button btnDialogCodeSnowTam = rootView.findViewById(R.id.btn_dialog_snowtam);
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
            }
        });

        // Set Runway ID
        TextView textViewId = rootView.findViewById(R.id.textView_runway_id);
        String id = getString(R.string.airport_track_name) + " " + runwayObject.getId();
        textViewId.setText(id);
        Log.d(id, "onCreateView RUNWAY ID : ");


        // Set Runway Date
        TextView textViewDate = rootView.findViewById(R.id.textView_runway_date);
        String date = runwayObject.getObservationDate(this);
        textViewDate.setText(date);
        Log.d(date, "onCreateView RUNWAY DATE : ");

        // Set Runway Condition
        TextView textViewCondition = rootView.findViewById(R.id.textView_runway_condition);
        String condition = runwayObject.getCondition();
        textViewCondition.setText(condition);
        Log.d(date, "onCreateView RUNWAY CONDITION : ");

        // Set Runway Friction
        TextView textViewFriction = rootView.findViewById(R.id.textView_runway_friction);
        String friction = "" + runwayObject.getFrictionCoefficient();
        textViewFriction.setText(friction);
        Log.d(date, "onCreateView RUNWAY CONDITION : ");

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
