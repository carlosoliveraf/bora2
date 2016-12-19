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

import java.io.Serializable;

import youtube.demo.youtubedemo.R;
import youtube.demo.youtubedemo.entity.LocalEntity;


public class LocaisFragment extends android.app.Fragment {

    private static final String LOCAL_KEY = "local_key";
    private LocalEntity mLocal;


    public static LocaisFragment newInstance(LocalEntity local) {
        LocaisFragment fragment = new LocaisFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LOCAL_KEY, (Serializable) local);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_locais,container,false);
        mLocal = (LocalEntity) getArguments().getSerializable(
                LOCAL_KEY);

        TextView titleField = (TextView) view.findViewById(R.id.textView5);
        titleField.setText(mLocal.getName());
        return view;
    }


}
