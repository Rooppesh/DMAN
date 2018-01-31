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
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GetHelp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class GetHelp extends Fragment {

    private OnFragmentInteractionListener mListener;

    public GetHelp() {
        // Required empty public constructor
    }
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_get_help, container, false);
        spinner=view.findViewById(R.id.getspinner);
        String[] reslist={" ","Food","Medicine","Clothing"};
        ArrayAdapter<String> resAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, reslist);
        resAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(resAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String restype = parent.getItemAtPosition(position).toString();
                if(restype.equals("Food"))
                {
                    Intent i=new Intent(getActivity(),FoodGet.class);
                    i.putExtra("ResType",restype);
                    startActivity(i);
                }
                else if(restype.equals("Medicine")){
                    Intent i= new Intent(getActivity(),MedGet.class);
                    i.putExtra("ResType",restype);
                    startActivity(i);
                }
                else if(restype.equals("Clothing")){
                    Intent i = new Intent(getActivity(),ClothGet.class);
                    i.putExtra("ResType",restype);
                    startActivity(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
