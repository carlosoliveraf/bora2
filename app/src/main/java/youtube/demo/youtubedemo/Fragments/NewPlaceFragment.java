package youtube.demo.youtubedemo.Fragments;


import android.app.Fragment;
        import android.app.FragmentManager;
import android.os.Bundle;
        import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import youtube.demo.youtubedemo.R;
import youtube.demo.youtubedemo.entity.LatLongEntity;
import youtube.demo.youtubedemo.entity.LocalEntity;
        import youtube.demo.youtubedemo.entity.UserEntity;
import youtube.demo.youtubedemo.util.JsonUtil;


public class NewPlaceFragment extends Fragment {

    //private static final String LOCAL_KEY = "local_key";
    private static final String USER_KEY = "user_key";
    private static final String LATLNG_KEY = "latlng_key";

    private UserEntity mUser;
    //private LocalEntity mLocal;
    private LatLongEntity mLatLng;

    private boolean hasPosts;

    public static NewPlaceFragment newInstance(UserEntity user, LatLongEntity latLng) {
        NewPlaceFragment fragment = new NewPlaceFragment();
        Bundle bundle = new Bundle();
        //bundle.putSerializable(LOCAL_KEY, (Serializable) local);
        bundle.putSerializable(USER_KEY, (Serializable) user);
        bundle.putSerializable(LATLNG_KEY, latLng);


        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hasPosts = false;
        container.clearDisappearingChildren();
        View view =  inflater.inflate(R.layout.fragment_new_place,container,false);
        //mLocal = (LocalEntity) getArguments().getSerializable(LOCAL_KEY);
        mUser = (UserEntity) getArguments().getSerializable(
                USER_KEY);
        mLatLng = (LatLongEntity) getArguments().getSerializable(
                LATLNG_KEY);

        final EditText nameField = (EditText) view.findViewById(R.id.nameField);
        final EditText emailField = (EditText) view.findViewById(R.id.emailField);
        final EditText funcField = (EditText) view.findViewById(R.id.funcField);
        final EditText sitefield = (EditText) view.findViewById(R.id.siteField);
        final EditText telefoneField = (EditText) view.findViewById(R.id.telefoneField);
        final EditText enderecoField = (EditText) view.findViewById(R.id.enderecoField);

        Button avaliarButton = (Button) view.findViewById(R.id.TESTEBOTAO);
        avaliarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double lat = mLatLng.getLatitude();
                double longi = mLatLng.getLongitude();


                JsonUtil json = new JsonUtil();
                String urlPlaces = "https://boraws.herokuapp.com/places";
                List param = new ArrayList();
                param.add(new BasicNameValuePair("name", nameField.getText().toString()));
                param.add(new BasicNameValuePair("email", emailField.getText().toString()));
                param.add(new BasicNameValuePair("funcionamento", funcField.getText().toString()));
                param.add(new BasicNameValuePair("url", sitefield.getText().toString()));
                param.add(new BasicNameValuePair("telefone", telefoneField.getText().toString()));
                param.add(new BasicNameValuePair("endereco", enderecoField.getText().toString()));
                param.add(new BasicNameValuePair("lat", mLatLng.getLatitude().toString().substring(0, 9)));
                param.add(new BasicNameValuePair("long", mLatLng.getLongitude().toString().substring(0, 9)));

                JSONObject obj = json.getJSONFromUrl(urlPlaces, param);
                if(obj != null){
                    Snackbar.make(view, "Estabelecimento cadastrado para aprovação! Obrigado pela contribuição! :)", Snackbar.LENGTH_LONG).show();
                    //Snackbar.make(view, param.toString(), Snackbar.LENGTH_INDEFINITE).show();

                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();


                }            }
        });




        return view;
    }


    public void submitPlace(View view){




    }




}