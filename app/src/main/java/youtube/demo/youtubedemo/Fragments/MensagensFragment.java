package youtube.demo.youtubedemo.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import youtube.demo.youtubedemo.LoginActivity;
import youtube.demo.youtubedemo.R;
import youtube.demo.youtubedemo.entity.LocalEntity;
import youtube.demo.youtubedemo.entity.UserEntity;
import youtube.demo.youtubedemo.util.JsonUtil;

import static android.support.design.R.styleable.AlertDialog;

public class MensagensFragment extends ListFragment{


    private JsonUtil json;
    private static final String MSG_KEY = "msg_key";
    private UserEntity user;

    // Array of strings storing country names
    String[] nomes;

    // Array of integers points to images stored in /res/drawable/
    int[] imagens = new int[]{
            R.mipmap.avatar_sm,
            R.mipmap.avatar_sm,
            //R.mipmap.avatar_sm,
            //R.mipmap.avatar_sm,
            //R.mipmap.avatar_sm,
            //R.mipmap.avatar_sm,
            //R.mipmap.avatar_sm,
            //R.mipmap.avatar_sm,
            //R.mipmap.avatar_sm,
            //R.mipmap.avatar_sm
    };

    // Array of strings to store currencies
    String[] mensagens;

    public JSONObject getFromDb() throws JSONException {
        json = new JsonUtil();
        String urlLastChat = "https://boraws.herokuapp.com/lastchatbysender/"+ user.get_id();
        //String urlLastChat = "https://boraws.herokuapp.com/lastchatbysender/5859b4ab26a5fa382ffb5b67";
        JSONObject retornoGet = json.getJSONFromUrlGet(urlLastChat);
        mensagens = new String[]{
                retornoGet.getString("msg"),
                "Flww",
                //"Claroo!",
                //"192234",
                //"Mensagem teste 123",
                //"hahahah",
                //"hello world",
                //"café",
                //"kkkkkkkkkkk",
                //"hola que tal"
        };

        nomes = new String[] {
                (retornoGet.getString("userSend").equals(user.get_id())? retornoGet.getString("userReceiName") : user.getName()),
                "Ivan Amorim",
                //"Guilherme Nascimento",
                //"Alisson Vargas",
                //"Fernando Rodrigues",
                //"Matheus Caiser",
                //"João da Silva",
                //"Chuck Norris",
                //"Roberto Gabriel"

        };
        return retornoGet;
    }


    public static MensagensFragment newInstance(UserEntity user) {
        MensagensFragment fragment = new MensagensFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MSG_KEY, (Serializable) user);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        user = (UserEntity) getArguments().getSerializable(
                MSG_KEY);
        System.out.println(user.get_id());


        try {
            getFromDb();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<2;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            //hm.put("txt", "Country : " + countries[i]);
            //hm.put("cur","Currency : " + currency[i]);
            //hm.put("flag", Integer.toString(flags[i]) );
            hm.put("txt", nomes[i]);
            hm.put("cur",mensagens[i]);
            hm.put("flag", Integer.toString(imagens[i]) );
            aList.add(hm);
        }


        // Keys used in Hashmap
        String[] from = { "flag","txt","cur" };

        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.txt,R.id.cur};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.fragment_mensagens, from, to);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        // Do whatever you need to do here.
        Toast.makeText(getActivity().getBaseContext(), "Item clicked: "+position, Toast.LENGTH_LONG).show();
        //Log.d("settings", "click worked");
    }
}
