package youtube.demo.youtubedemo.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import youtube.demo.youtubedemo.R;


public class LocaisFragment extends android.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_sobre,container,false);
        //TextView loginField = (TextView) view.findViewById(R.id.locais);
        //loginField.setText("local");
        return view;
    }

}
