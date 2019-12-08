package com.example.snowtamair;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AirportResultCardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AirportResultCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AirportResultCardFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME_AIRPORT = "nameAirport";
    private static final String CODE_OACI = "codeOACI";

    private String mName;
    private String mCode;

    private OnFragmentInteractionListener mListener;
    private Button btnGoAer;
    private CardView cardView;
    private TextView textViewName;
    private TextView textViewCode;

    public AirportResultCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @param code Parameter 2.
     * @return A new instance of fragment AirportResultCardFragment.
     */
    public static AirportResultCardFragment newInstance(String name, String code) {
        AirportResultCardFragment fragment = new AirportResultCardFragment();
        Bundle args = new Bundle();
        args.putString(NAME_AIRPORT, name);
        args.putString(CODE_OACI, code);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(NAME_AIRPORT);
            mCode = getArguments().getString(CODE_OACI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_airport_result_card, container, false);

        // Set Card click to airportActivity
        cardView = root.findViewById(R.id.cardView_result_airport);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass( root.getContext(), AirportActivity.class);
                intent.putExtra("search", CODE_OACI);
                startActivity(intent);
            }
        });

        /*
        // Set btn remove card
        btnGoAer = findViewById(R.id.btn_result_airport);
        btnGoAer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass( MainActivity.this, AirportActivity.class);
                startActivity(intent);
            }
        });
        */

        // Set airport name
        textViewName = root.findViewById(R.id.textView_result_name);
        textViewName.setText(mName);
        Log.d(mName, "onCreateView mName: ");
        Log.d(NAME_AIRPORT, "onCreateView NAME_AIRPORT: ");

        // Set airport oaci code
        textViewCode = root.findViewById(R.id.textView_result_code);
        textViewCode.setText(mCode);
        Log.d(mCode, "onCreateView mCode: ");
        Log.d(CODE_OACI, "onCreateView CODE_OACI: ");

        return root;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
