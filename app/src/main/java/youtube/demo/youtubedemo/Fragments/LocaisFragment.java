package youtube.demo.youtubedemo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        TextView titleField = (TextView) view.findViewById(R.id.placetitle);
        titleField.setText(mLocal.getName());
        TextView endField = (TextView) view.findViewById(R.id.placeadress);
        endField.setText(mLocal.getEndereco());
        return view;
    }


}
