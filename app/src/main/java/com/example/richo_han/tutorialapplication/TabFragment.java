package com.example.richo_han.tutorialapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Richo_Han on 2016/11/17.
 */

public class TabFragment extends Fragment {

    public static final String ARG_POSITION = "position";
    private int mPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        TextView textView = new TextView(getActivity());
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setText("Tab" + (mPosition + 1));

        fl.addView(textView);
        return fl;
    }

    public static TabFragment newInstance(int position) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
}
