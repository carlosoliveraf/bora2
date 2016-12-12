//package youtube.demo.youtubedemo.Fragments;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.ListFragment;
//import android.app.LoaderManager;
//import android.content.CursorLoader;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.content.Loader;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import youtube.demo.youtubedemo.R;
//import youtube.demo.youtubedemo.interf.OnFragmentInteractionListener;
//import youtube.demo.youtubedemo.util.DataAdapter;
//
//public class MensagensFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{
//        private OnFragmentInteractionListener mListener;
//        private DataAdapter adapter;
//
//        /**
//         * Mandatory empty constructor for the fragment manager to instantiate the
//         * fragment (e.g. upon screen orientation changes).
//         */
//        //public ItemListFragment() {
//        //}
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            adapter = new DataAdapter(getActivity(), R.layout.fragment_mensagens);
//            setListAdapter(adapter);
//
//            getLoaderManager().initLoader(0, null, this);
//        }
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.fragment_mensagens,container,false);
//    }
//
//        @Override
//        public void onAttach(Activity activity) {
//            super.onAttach(activity);
//            try {
//                mListener = (OnFragmentInteractionListener) activity;
//            } catch (ClassCastException e) {
//                throw new ClassCastException(activity.toString()
//                        + " must implement OnFragmentInteractionListener");
//            }
//        }
//
//        @Override
//        public void onDetach() {
//            super.onDetach();
//            mListener = null;
//        }
//
//        @Override
//        public void onListItemClick(ListView l, View v, int position, long id) {
//            super.onListItemClick(l, v, position, id);
//
//            if (null != mListener) {
//                // Notify the active callbacks interface (the activity, if the
//                // fragment is attached to one) that an item has been selected.
//                mListener.onItemSelected(id);
//            }
//        }
//
//        @Override
//        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//            CursorLoader loader = new CursorLoader(getActivity(),
//                    DataProvider.CONTENT_URI_DATA,
//                    new String[]{DataProvider.COL_ID, DataProvider.COL_CONTENT},
//                    null,
//                    null,
//                    null);
//            return loader;
//        }
//
//        @Override
//        public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
//
//        }
//
//        @Override
//        public void onLoaderReset(android.content.Loader<Cursor> loader) {
//
//        }
//
//        @Override
//        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//            adapter.swapCursor(data);
//        }
//
//        @Override
//        public void onLoaderReset(Loader<Cursor> loader) {
//            adapter.swapCursor(null);
//        }
//} {
//}
