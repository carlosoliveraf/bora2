package youtube.demo.youtubedemo.Fragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import youtube.demo.youtubedemo.R;
import youtube.demo.youtubedemo.entity.UserEntity;


public class MensagensContatoFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private Chat chat;
    private static final String CHAT_KEY = "chat_key";
    private UserEntity user;
    String[] nomes;


    String[] mensagens;
    String[] ownMsgs;
    String[] theirMsgs;

    public static MensagensContatoFragment newInstance(UserEntity user) {
        MensagensContatoFragment fragment = new MensagensContatoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CHAT_KEY, (Serializable) user);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

      //  user = (UserEntity) getArguments().getSerializable(CHAT_KEY);
       // System.out.println(user.get_id());

        try {
            getFromDb();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<theirMsgs.length;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            //hm.put("txt", "Country : " + countries[i]);
            //hm.put("cur","Currency : " + currency[i]);
            //hm.put("flag", Integer.toString(flags[i]) );
           // hm.put("txt", ownMsgs[i]);
            hm.put("cur",theirMsgs[i]);
           // hm.put("flag", Integer.toString(imagens[i]) );
            aList.add(hm);
        }


        // Keys used in Hashmap
        String[] from = { "cur" };

        // Ids of views in listview_layout
        int[] to = {R.id.cur};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.fragment_chat, from, to);

        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public JSONObject getFromDb() throws JSONException {

        JSONObject retornoGet = null;
        theirMsgs = new String[]{
                "eai, blz?",
                "tambem",
                "como tá o trabalho?",
                "masssa",
                "masssa!!",
                "hahahah",
                "hello world",
                "café",
                "kkkkkkkkkkk",
                "hola que tal"
        };


        return retornoGet;
    }

    private class Chat {

        private String nome;
        private String msg;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }





    /*private OnFragmentInteractionListener mListener;
    private SimpleCursorAdapter adapter;
    public static final String COL_MSG = "msg";
    public static final String COL_FROM = "email";
    public static final String COL_AT = "at";
    public static final String COL_EMAIL = "email";


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.chat_list_item,
                null,
                new String[]{COL_MSG, COL_AT},
                new int[]{R.id.text1, R.id.text2},
                0);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                switch(view.getId()) {
                    case R.id.text1:
                        LinearLayout root = (LinearLayout) view.getParent().getParent();
                        if (cursor.getString(cursor.getColumnIndex(COL_FROM)) == null) {
                            root.setGravity(Gravity.RIGHT);
                            root.setPadding(50, 10, 10, 10);
                        } else {
                            root.setGravity(Gravity.LEFT);
                            root.setPadding(10, 10, 50, 10);
                        }
                        break;
                }
                return false;
            }
        });
        setListAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = new Bundle();
        args.putString(COL_EMAIL, mListener.getProfileEmail());
        getLoaderManager().initLoader(0, args, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface OnFragmentInteractionListener {
        public String getProfileEmail();
    }*/
}
