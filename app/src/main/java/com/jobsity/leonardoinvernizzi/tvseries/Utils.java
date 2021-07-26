package com.jobsity.leonardoinvernizzi.tvseries;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.room.Room;

public class Utils {

    public static final String EXTRA_MESSAGE = "com.jobsity.leonardoinvernizzi.tvseries.MESSAGE";

    public static void createAlert(Context context, String message) {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message)
                .setTitle("Alert");

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String cleanTags(String string) {
        if (string == null){
            return "";
        }
        String result = string;
        result = result.replace("<p>", "");
        result = result.replace("</p>", "");
        result = result.replace("<b>", "");
        result = result.replace("</b>", "");
        result = result.replace("<i>", "");
        result = result.replace("</i>", "");
        result = result.trim();
        return result;
    }

    public static void setListViewHeight(ListView listView, BaseAdapter adapter) {
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        //listAdapter.getCount() returns the number of data items
        for (int i = 0,len = adapter.getCount(); i < len; i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight() gets the height occupied by the separator between children
        // params.height finally gets the height of the entire ListView full display
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *  (adapter .getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "series")
                .addMigrations(AppDatabase.MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();
    }

}
