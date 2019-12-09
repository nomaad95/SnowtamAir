package com.example.snowtamair;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchInputFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchInputFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    private OnFragmentInteractionListener mListener;
    private ImageButton buttonSearch;
    private EditText inputSearch;
    public static JSONArray oaciList;
    public static Menu menu;

    public SearchInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SearchInputFragment.
     */
    public static SearchInputFragment newInstance(String param1) {
        SearchInputFragment fragment = new SearchInputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_search_input, container, false);

        // Init Btn Search
        inputSearch = root.findViewById(R.id.input_search);
        InputFilter[] filterArray = new InputFilter[2];
        filterArray[0] = new InputFilter.LengthFilter(4);
        filterArray[1] = new InputFilter.AllCaps();
        inputSearch.setFilters(filterArray);

        buttonSearch = root.findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oaciCheck(inputSearch.getText().toString(), root.getContext())){
                    Intent intent = new Intent();
                    intent.setClass( root.getContext(), AirportActivity.class);
                    intent.putExtra("search", inputSearch.getText().toString());
                    startActivity(intent);
                    // TODO : MainActivity.searchInputNb --;
                }
                else{
                    Toast.makeText(root.getContext(), getString(R.string.toast_wrong_ICAO), Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }

    public boolean oaciCheck(String oaci, Context context) {
        try {
            InputStream is = context.getAssets().open("airport.json");
            int size = 0;
            try {
                size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");
                try {
                    oaciList = new JSONArray(json);
                    for(int i = 0; i < oaciList.length(); i++){
                        JSONObject oaciJson = oaciList.getJSONObject((i));
                        if(oaci.equals(oaciJson.getString("ICAO"))){
                            return true;
                        }
                    }
                    return false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*private void updateMenuTitle(){
        MenuItem airport1 = this.menu.getItem(0);
        if(airport1 != null){
            Log.d("menuTitle", "ok");
        }

        Log.d("menuTitle","updateMenu");
        Log.d("menuTitle", airport1.getTitle().toString());
    }*/

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
        void onFragmentInteraction(Uri uri);
    }
}
