package youtube.demo.youtubedemo.util;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by oliverace on 09/12/2016.
 */

public class DataAdapter extends SimpleCursorAdapter {

    public DataAdapter(Context context, int layout) {
        super(context, layout, null, new String[]{
                //DataProvider.COL_CONTENT
                "teste"
        }, new int[]{android.R.id.text1}, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //String content = cursor.getString(cursor.getColumnIndex(DataProvider.COL_CONTENT));
        //Data data = new Data(content);

        //String title = data.getTitle();
        //String iconUrl = data.getIcon();

        TextView titleText = (TextView) view.findViewById(android.R.id.text1);
        //titleText.setText(title);

        //NetworkImageView iconView = (NetworkImageView) view.findViewById(R.id.imageView1);
        //iconView.setDefaultImageResId(R.drawable.ic_launcher);
        //iconView.setImageUrl(iconUrl, App.getInstance().getImageLoader());
    }

}