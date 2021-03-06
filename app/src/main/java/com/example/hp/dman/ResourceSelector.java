package com.example.hp.dman;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResourceSelector.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ResourceSelector extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ResourceSelector() {
        // Required empty public constructor
    }
    Spinner spinner;
    Button loadMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_resource_selector, container, false);
        spinner=(Spinner)view.findViewById(R.id.ResSpinner);
        String[] reslist={" ","Food","Medicine","Clothing"};
        ArrayAdapter<String> resAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, reslist);
        resAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(resAdapter);
        loadMap=(Button)view.findViewById(R.id.mapbtn);
        loadMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String restype=spinner.getSelectedItem().toString();
                Intent i=new Intent(getActivity(),MapsActivity.class);
                i.putExtra("ResourceType",restype);
                startActivity(i);
            }
        });

        return view;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
