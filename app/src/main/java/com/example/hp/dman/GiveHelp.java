package com.example.hp.dman;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GiveHelp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class GiveHelp extends Fragment {

    private OnFragmentInteractionListener mListener;


    public GiveHelp() {
        // Required empty public constructor
    }
    String restype;
    Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_give_help, container, false);
        spinner=view.findViewById(R.id.givespinner);
        String[] reslist={" ","Food","Medicine","Clothing"};
        ArrayAdapter<String> resAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, reslist);
        resAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(resAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                restype = parent.getItemAtPosition(position).toString();
                if(restype.equals("Food"))
                {
                   Intent i=new Intent(getActivity(),FoodSelect.class);
                   startActivity(i);
                }
                else if(restype.equals("Medicine")){
                    Intent i= new Intent(getActivity(),MedSelect.class);
                    startActivity(i);
                }
                else if(restype.equals("Clothing")){
                    Intent i = new Intent(getActivity(),ClothSelect.class);
                    startActivity(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Log.d("Resource type",restype);
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
