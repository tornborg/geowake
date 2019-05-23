package com.example.geowake;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ThirdFragment extends Fragment {

    private Button stop;


    private OnFragmentInteractionListener mListener;

    public ThirdFragment() {
        // Required empty public constructor
    }


    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
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
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        ImageView circle;
        circle = view.findViewById(R.id.circle3);
        circle.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_OVER);
        circle = view.findViewById(R.id.circle2);
        circle.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_OVER);
        circle = view.findViewById(R.id.circle1);
        circle.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_OVER);


        ((MapsActivity) getActivity()).setFragment(2);

        stop = view.findViewById(R.id.stop);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stop.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                ((MapsActivity) getActivity()).changeAlarmStatus();
                openStartFragment();
                ((MapsActivity) getActivity()).reset();

            }
        });

        TextView myText = view.findViewById(R.id.textView7);

        Animation anim = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade);
        myText.startAnimation(anim);


        return view;
    }

    public void openStartFragment() {
        StartFragment startFragment = new StartFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide, 0, 0, R.anim.slide);
        transaction.replace(R.id.main, startFragment, "START_FRAGMENT").commit();
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
