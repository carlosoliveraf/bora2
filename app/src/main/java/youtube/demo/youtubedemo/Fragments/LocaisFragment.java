package youtube.demo.youtubedemo.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import youtube.demo.youtubedemo.R;
import youtube.demo.youtubedemo.entity.LocalEntity;
import youtube.demo.youtubedemo.entity.UserEntity;
import youtube.demo.youtubedemo.util.JsonUtil;


public class LocaisFragment extends Fragment {

    private static final String LOCAL_KEY = "local_key";
    private static final String USER_KEY = "user_key";
    private UserEntity mUser;
    private LocalEntity mLocal;
    private boolean hasPosts;


    public static LocaisFragment newInstance(LocalEntity local, UserEntity user) {
        LocaisFragment fragment = new LocaisFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LOCAL_KEY, (Serializable) local);
        bundle.putSerializable(USER_KEY, (Serializable) user);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hasPosts = false;
        container.clearDisappearingChildren();
        View view =  inflater.inflate(R.layout.fragment_locais,container,false);
        mLocal = (LocalEntity) getArguments().getSerializable(
                LOCAL_KEY);
        mUser = (UserEntity) getArguments().getSerializable(
                USER_KEY);
        //metodo que pesquisa os posts desse estabelecimento e retorna com o cálculo.
        try {
            getNotas(mLocal);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView titleField = (TextView) view.findViewById(R.id.placetitle);
        titleField.setText(mLocal.getName());
        TextView endField = (TextView) view.findViewById(R.id.placeadress);
        endField.setText(mLocal.getEndereco());
        Button avaliarButton = (Button) view.findViewById(R.id.avaliarButton);
        avaliarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                Fragment fragment = AvaliacaoFragment.newInstance(mLocal, mUser);
                fm.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "avaliacao" ).commit();
            }
        });

        if(hasPosts) {
            TextView notaAtend = (TextView) view.findViewById(R.id.notaAtend);

                if (mLocal.getAtendimento() > 7.0) {
                    notaAtend.setTextColor(Color.GREEN);
                } else {
                    notaAtend.setTextColor(Color.RED);
                }

                notaAtend.setText(mLocal.getAtendimento().toString().substring(0, 3));
                TextView notaProd = (TextView) view.findViewById(R.id.notaProd);

                if (mLocal.getProduto() > 7.0) {
                    notaProd.setTextColor(Color.GREEN);
                } else {
                    notaProd.setTextColor(Color.RED);
                }

                notaProd.setText(mLocal.getProduto().toString().substring(0, 3));
                TextView notaLota = (TextView) view.findViewById(R.id.notaLot);

                if (mLocal.getLotacao() > 7.0) {
                    notaLota.setTextColor(Color.GREEN);
                } else {
                    notaLota.setTextColor(Color.RED);
                }

                notaLota.setText(mLocal.getLotacao().toString().substring(0, 3));
                TextView notaPrec = (TextView) view.findViewById(R.id.notaPrec);

                if (mLocal.getPrecos() > 7.0) {
                    notaPrec.setTextColor(Color.GREEN);
                } else {
                    notaPrec.setTextColor(Color.RED);
                }

                notaPrec.setText(mLocal.getPrecos().toString().substring(0, 3));

                TextView semNotasView = (TextView) view.findViewById(R.id.semNotasField);
                semNotasView.setVisibility(View.INVISIBLE);


        }else{
                TextView notaAtend = (TextView) view.findViewById(R.id.notaAtend);
                notaAtend.setText("S/N*");
                TextView notaProd = (TextView) view.findViewById(R.id.notaProd);
                notaProd.setText("S/N*");
                TextView notaLota = (TextView) view.findViewById(R.id.notaLot);
                notaLota.setText("S/N*");
                TextView notaPrec = (TextView) view.findViewById(R.id.notaPrec);
                notaPrec.setText("S/N*");
                notaAtend.setTextColor(Color.BLACK);
                notaProd.setTextColor(Color.BLACK);
                notaLota.setTextColor(Color.BLACK);
                notaPrec.setTextColor(Color.BLACK);

        }

        ArrayList<String> observacoes = getObs(mLocal);
        TextView post1 = (TextView) view.findViewById(R.id.post1);
        TextView post2 = (TextView) view.findViewById(R.id.post2);
        if(observacoes.size() == 0){
            post1.setVisibility(View.INVISIBLE);
            post2.setVisibility(View.INVISIBLE);
        }
        if(observacoes.size() == 1){
            post1.setText(observacoes.get(0));
            post2.setVisibility(View.INVISIBLE);
        }

        if (observacoes.size() == 2){
            post1.setText(observacoes.get(0));
            post2.setText(observacoes.get(1));
        }




        return view;
    }


    public ArrayList<String> getObs(LocalEntity localParam){
        ArrayList<String> observacoes = new ArrayList<>();
        JsonUtil json = new JsonUtil();
        String urlPosts = "https://boraws.herokuapp.com/obsbyplace";
        List param = new ArrayList();
        param.add(new BasicNameValuePair("place", localParam.get_id()));
        JSONArray retornoPost = json.getJSONFromUrlPostArray(urlPosts, param);
        if (retornoPost.length() > 0) {
            JSONObject jsonObj;
            for (int i = 0; i < retornoPost.length(); i++) {
                try {
                    jsonObj = retornoPost.getJSONObject(i);
                    observacoes.add(jsonObj.getString("obs"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        return observacoes;
    }


    public void getNotas(LocalEntity localParam) throws JSONException {
        JsonUtil json = new JsonUtil();
        String urlPosts = "https://boraws.herokuapp.com/postsbyplace";
        List param = new ArrayList();
        param.add(new BasicNameValuePair("place", localParam.get_id()));
        JSONArray retornoPost = json.getJSONFromUrlPostArray(urlPosts, param);
        ArrayList<Double> notasProd = new ArrayList<>();
        ArrayList<Double> notasLota = new ArrayList<>();
        ArrayList<Double> notasPrec = new ArrayList<>();
        ArrayList<Double> notasAtend = new ArrayList<>();
        //System.out.print(retornoPost.length());
        if (retornoPost.length() > 0) {
            hasPosts = true;
            JSONObject jsonObj;
            for (int i = 0; i < retornoPost.length(); i++) {
                jsonObj = retornoPost.getJSONObject(i);
                System.out.print(json.toString());
                notasProd.add(jsonObj.getDouble("produto"));
                notasLota.add(jsonObj.getDouble("lotacao"));
                notasPrec.add(jsonObj.getDouble("precos"));
                notasAtend.add(jsonObj.getDouble("atendimento"));
            }
            mLocal.setProduto(calculaNota(notasProd));
            mLocal.setLotacao(calculaNota(notasLota));
            mLocal.setPrecos(calculaNota(notasPrec));
            mLocal.setAtendimento(calculaNota(notasAtend));

        }else{
            //tratar caso nao tenha posts
            hasPosts = false;
        }

    }

    public Double calculaNota(ArrayList<Double> notas){
        Double media = 0.0;
        Double nota = 0.0;
        int i = 0;
        for(Double n : notas){
            nota += n;
            i++;
        }
        media = nota/i;

        return media;
    }

}
