package com.peoplecoachingworks.leapstudio20.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.peoplecoachingworks.leapstudio20.Data.GoalTipsContract.GoalTipsEntry;

/**
 * {@link ContentProvider} for Pets app.
 */
public class GoalTipsProvider extends ContentProvider {

    //Tag for the log messages
    public static final String LOG_TAG = GoalTipsProvider.class.getSimpleName();

    //URI matcher code for the content URI for the pets table
    private static final int GOAL_TIPS = 100;

    //URI matcher code for the content URI for a single pet in the pets table
    private static final int GOAL_TIPS_ID = 101;
    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(GoalTipsContract.CONTENT_AUTHORITY, GoalTipsContract.PATH_GOAL_TIPS, GOAL_TIPS);
        sUriMatcher.addURI(GoalTipsContract.CONTENT_AUTHORITY, GoalTipsContract.PATH_GOAL_TIPS + "/#", GOAL_TIPS_ID);
    }

    //Db helper object
    private GoalTipsDbHelper mDbHelper;

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new GoalTipsDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case GOAL_TIPS:
                cursor = database.query(GoalTipsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case GOAL_TIPS_ID:

                selection = GoalTipsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(GoalTipsEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    //Insert new data into the provider with the given ContentValues.
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case GOAL_TIPS:
                return insertGoalTip(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertGoalTip(Uri uri, ContentValues values) {

        // (Sanity check) Check that the name is not null
        //TODO:Sanity check not working
        String quote = values.getAsString(GoalTipsEntry.COLUMN_QUOTE);
        if (quote == null) {
            throw new IllegalArgumentException("Please enter quote");
        }


        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(GoalTipsEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    //Updates the data at the given selection and selection arguments, with the new ContentValues.
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case GOAL_TIPS:
                return updateGoal(uri, contentValues, selection, selectionArgs);
            case GOAL_TIPS_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = GoalTipsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateGoal(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateGoal(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(GoalTipsEntry.COLUMN_QUOTE)) {
            String name = values.getAsString(GoalTipsEntry.COLUMN_QUOTE);
            if (name == null) {
                throw new IllegalArgumentException("Please enter quote");
            }
        }

        //IF no values to update, don't try to update database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        return database.update(GoalTipsEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    //Delete the data at the given selection and selection arguments.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case GOAL_TIPS:
                // Delete all rows that match the selection and selection args
                return database.delete(GoalTipsEntry.TABLE_NAME, selection, selectionArgs);
            case GOAL_TIPS_ID:
                // Delete a single row given by the ID in the URI
                selection = GoalTipsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(GoalTipsEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}