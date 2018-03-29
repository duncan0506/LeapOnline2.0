
package com.peoplecoachingworks.leapstudio20.Data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.peoplecoachingworks.leapstudio20.R;


public class KeyLessonCursorAdapter extends CursorAdapter {

    public KeyLessonCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.key_lesson_list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView keyLessonTextView = view.findViewById(R.id.tvKeyLesson);
        TextView actionStepTextView = view.findViewById(R.id.tvActionStep);
        TextView dateTextView = view.findViewById(R.id.tvDate);
        TextView timeTextView = view.findViewById(R.id.tvTime);


        // Find the columns of pet attributes that we're interested in
        int keyLessonColumnIndex = cursor.getColumnIndex(KeyLessonContract.KeyLessonEntry.COLUMN_KEY_LESSON);
        int actionStepColumnIndex = cursor.getColumnIndex(KeyLessonContract.KeyLessonEntry.COLUMN_ACTION_STEP);
        int dateColumnIndex = cursor.getColumnIndex(KeyLessonContract.KeyLessonEntry.COLUMN_DATE);
        int timeColumnIndex = cursor.getColumnIndex(KeyLessonContract.KeyLessonEntry.COLUMN_TIME);

        // Read the pet attributes from the Cursor for the current pet
        String keyLesson = cursor.getString(keyLessonColumnIndex);
        String actionStep = cursor.getString(actionStepColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String time = cursor.getString(timeColumnIndex);


        // Update the TextViews with the attributes for the current pet
        keyLessonTextView.setText(keyLesson);
        actionStepTextView.setText(actionStep);
        dateTextView.setText(date);
        timeTextView.setText(time);
    }
}
