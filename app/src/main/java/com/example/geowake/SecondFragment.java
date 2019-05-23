package com.example.geowake;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;


public class SecondFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private Button startAlarm;

    private OnFragmentInteractionListener mListener;

    private SeekBar radius;

    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance(String param1) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        view.playSoundEffect(SoundEffectConstants.CLICK);

        ImageView circle;
        circle = view.findViewById(R.id.circle2);
        circle.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_OVER);
        circle = view.findViewById(R.id.circle1);
        circle.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_OVER);


        ((MapsActivity) getActivity()).setFragment(1);

        radius = view.findViewById(R.id.progress);
        radius.setMin(50);
        radius.setMax(1000);
        radius.setProgress(300);

        startAlarm = view.findViewById(R.id.start);

        startAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startAlarm.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                ((MapsActivity) getActivity()).changeAlarmStatus();
                openThirdFragment();
            }
        });

        radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((MapsActivity) getActivity()).mCircle.setRadius(progress);

            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
            }
        });


        return view;
    }

    private void openThirdFragment() {
        ThirdFragment thirdFragment = new ThirdFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide, R.anim.slideback);
        transaction.replace(R.id.main, thirdFragment, "THIRD_FRAGMENT").commit();
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
        void onFragmentInteraction(Uri uri);
    }
}
