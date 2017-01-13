package youtube.demo.youtubedemo.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

import youtube.demo.youtubedemo.R;
import youtube.demo.youtubedemo.entity.UserEntity;

public class HomeFragment extends Fragment {

    private static final String HOME_KEY = "home_key";
    private UserEntity user;

    public static HomeFragment newInstance(UserEntity user) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(HOME_KEY, (Serializable) user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        user = (UserEntity) getArguments().getSerializable(
                HOME_KEY);
        View view =  inflater.inflate(R.layout.fragment_home,container,false);
        TextView profilename = (TextView) view.findViewById(R.id.profilename);
        profilename.setText(user.getName());
        return view;
    }

}
