package com.example.geowake;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;


public class StartFragment extends Fragment {
    private AutoCompleteTextView textInput;
    private Button favorites;

    private static final String[] PLACES = new String[]{"Lunds Tekniska Hogskola", "Vastgota Nation", "High Chaparall", "Liseberg"};




    private OnFragmentInteractionListener mListener;

    public StartFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_start, container, false);
        favorites = view.findViewById(R.id.button2);


        textInput = view.findViewById(R.id.autoCompleteTextView2);
        textInput.setImeActionLabel("Set destination..", KeyEvent.KEYCODE_ENTER);


        textInput.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event)
            {
                boolean handled = false;
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    swapFragment();
                    handled = true;
                }
                return handled;
            }
        });
        return view;
    }

      private void swapFragment(){
        SecondFragment secondFragment = new SecondFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
          transaction.setCustomAnimations(R.anim.slide, R.anim.slide);
          transaction.replace(R.id.main, secondFragment, "START_FRAGMENT").commit();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void nextFragment() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
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
        void onFragmentInteraction();
    }
}
