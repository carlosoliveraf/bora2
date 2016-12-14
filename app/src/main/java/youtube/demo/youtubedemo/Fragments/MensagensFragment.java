package youtube.demo.youtubedemo.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import youtube.demo.youtubedemo.R;

public class MensagensFragment extends ListFragment{
    // Array of strings storing country names
    String[] countries = new String[] {
            "Carlos Olivera",
            "Joe Joe Oliveira",
            "Ivan Amorim",
            "Guilherme Nascimento",
            "Alisson Vargas",
            "Fernando Rodrigues",
            "Matheus Caiser",
            "João da Silva",
            "Chuck Norris",
            "Roberto Gabriel"

    };

    // Array of integers points to images stored in /res/drawable/
    int[] flags = new int[]{
            R.mipmap.avatar_sm,
            R.mipmap.avatar_sm,
            R.mipmap.avatar_sm,
            R.mipmap.avatar_sm,
            R.mipmap.avatar_sm,
            R.mipmap.avatar_sm,
            R.mipmap.avatar_sm,
            R.mipmap.avatar_sm,
            R.mipmap.avatar_sm,
            R.mipmap.avatar_sm
    };

    // Array of strings to store currencies
    String[] currency = new String[]{
            "E aí... de boa?",
            "Flww",
            "Claroo!",
            "192234",
            "Mensagem teste 123",
            "hahahah",
            "hello world",
            "café",
            "kkkkkkkkkkk",
            "hola que tal"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            //hm.put("txt", "Country : " + countries[i]);
            //hm.put("cur","Currency : " + currency[i]);
            //hm.put("flag", Integer.toString(flags[i]) );
            hm.put("txt", countries[i]);
            hm.put("cur",currency[i]);
            hm.put("flag", Integer.toString(flags[i]) );
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
}
