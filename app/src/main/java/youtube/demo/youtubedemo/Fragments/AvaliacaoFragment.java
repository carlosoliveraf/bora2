package youtube.demo.youtubedemo.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import youtube.demo.youtubedemo.MainActivity;
import youtube.demo.youtubedemo.R;
import youtube.demo.youtubedemo.entity.LocalEntity;
import youtube.demo.youtubedemo.entity.UserEntity;
import youtube.demo.youtubedemo.util.JsonUtil;


public class AvaliacaoFragment extends Fragment {
    private static final String LOCAL_KEY = "local_key";
    private LocalEntity mLocal;
    private static final String USER_KEY = "user_key";
    private UserEntity mUser;


    public static AvaliacaoFragment newInstance(LocalEntity local, UserEntity user) {
        AvaliacaoFragment fragment = new AvaliacaoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LOCAL_KEY, (Serializable) local);
        bundle.putSerializable(USER_KEY, (Serializable) user);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.clearDisappearingChildren();
        View view =  inflater.inflate(R.layout.fragment_avaliacao,container,false);
        mLocal = (LocalEntity) getArguments().getSerializable(
                LOCAL_KEY);
        mUser = (UserEntity) getArguments().getSerializable(
                USER_KEY);

        TextView titleField = (TextView) view.findViewById(R.id.avaliacaoBar);
        titleField.setText(mLocal.getName());
        final EditText atendNota = (EditText) view.findViewById(R.id.atendNota);
        final EditText lotNota = (EditText) view.findViewById(R.id.atendLot);
        final EditText prodNota = (EditText) view.findViewById(R.id.atendProd);
        final EditText precNota = (EditText) view.findViewById(R.id.atendPrec);
        final EditText obsField = (EditText) view.findViewById(R.id.obsField);

        Button enviarButton = (Button) view.findViewById(R.id.enviarButton);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enviarAvaliacao(view);
            if(mUser != null) {
                double atend = Double.valueOf(atendNota.getText().toString());
                double lot = Double.valueOf(lotNota.getText().toString());
                double prod = Double.valueOf(prodNota.getText().toString());
                double prec = Double.valueOf(precNota.getText().toString());


                JsonUtil json = new JsonUtil();
                String urlPosts = "https://boraws.herokuapp.com/posts";
                List param = new ArrayList();
                param.add(new BasicNameValuePair("produto", prodNota.getText().toString()));
                param.add(new BasicNameValuePair("lotacao", lotNota.getText().toString()));
                param.add(new BasicNameValuePair("precos", precNota.getText().toString()));
                param.add(new BasicNameValuePair("atendimento", atendNota.getText().toString()));
                param.add(new BasicNameValuePair("obs", obsField.getText().toString()));
                param.add(new BasicNameValuePair("user", mUser.get_id()));
                param.add(new BasicNameValuePair("place", mLocal.get_id()));
                System.out.print(param);
                JSONObject obj = json.getJSONFromUrl(urlPosts, param);
                if (obj != null) {

                    Snackbar.make(view, "Avaliação enviada! Obrigado pela contribuição! :)", Snackbar.LENGTH_LONG).show();
                    FragmentManager fm = getFragmentManager();
                    Fragment fragment = LocaisFragment.newInstance(mLocal, mUser);
                    fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }else{
                    Snackbar.make(view, "Algo deu errado! Favor repetir a operação.", Snackbar.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(view, "Oops! Perdi a referência do seu usuário, faça o login novamente!", Snackbar.LENGTH_LONG).show();
            }

        }
        });



        return view;
    }


    public void enviarAvaliacao(View view){





    }



}

