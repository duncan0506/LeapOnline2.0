/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peoplecoachingworks.leapstudio20.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.peoplecoachingworks.leapstudio20.Data.KeyLessonContract.KeyLessonEntry;


public class KeyLessonProvider extends ContentProvider {


    public static final String LOG_TAG = KeyLessonProvider.class.getSimpleName();

    private static final int KEY_LESSON = 100;

    private static final int KEY_LESSON_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.pets/pets" will map to the
        // integer code {@link #KEY_LESSON}. This URI is used to provide access to MULTIPLE rows
        // of the pets table.
        sUriMatcher.addURI(KeyLessonContract.CONTENT_AUTHORITY, KeyLessonContract.PATH_KEY_LESSON, KEY_LESSON);

        // The content URI of the form "content://com.example.android.pets/pets/#" will map to the
        // integer code {@link #KEY_LESSON_ID}. This URI is used to provide access to ONE single row
        // of the pets table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.pets/pets/3" matches, but
        // "content://com.example.android.pets/pets" (without a number at the end) doesn't match.
        sUriMatcher.addURI(KeyLessonContract.CONTENT_AUTHORITY, KeyLessonContract.PATH_KEY_LESSON + "/#", KEY_LESSON_ID);
    }

    /**
     * Database helper object
     */
    private KeyLessonDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new KeyLessonDbHelper(getContext());
        return true;
    }

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
            case KEY_LESSON:
                // For the KEY_LESSON code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                cursor = database.query(KeyLessonEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case KEY_LESSON_ID:
                // For the KEY_LESSON_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = KeyLessonEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(KeyLessonEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KEY_LESSON:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertPet(Uri uri, ContentValues values) {
        // Check that the key lesson is not null
        String keyLesson = values.getAsString(KeyLessonEntry.COLUMN_KEY_LESSON);
        if (keyLesson == null) {
            throw new IllegalArgumentException("Enter Key Lesson");
        }

        // Check that the actionStep is valid
        String actionStep = values.getAsString(KeyLessonEntry.COLUMN_ACTION_STEP);
        if (actionStep == null) {
            throw new IllegalArgumentException("Enter Action Step");
        }

        // Check that the date is valid
        String date = values.getAsString(KeyLessonEntry.COLUMN_DATE);
        if (date == null) {
            throw new IllegalArgumentException("Enter Date");
        }


        // Check that the time is valid
        String time = values.getAsString(KeyLessonEntry.COLUMN_TIME);
        if (time == null) {
            throw new IllegalArgumentException("Enter Time");
        }


        // Check that the priority is valid
        Integer priority = values.getAsInteger(KeyLessonEntry.COLUMN_PRIORITY);
        if (priority == null || !KeyLessonEntry.isValidPriority(priority)) {
            throw new IllegalArgumentException("Enter valid priority");
        }


        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(KeyLessonEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KEY_LESSON:
                return updateKeyLesson(uri, contentValues, selection, selectionArgs);
            case KEY_LESSON_ID:
                // For the KEY_LESSON_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = KeyLessonEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateKeyLesson(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateKeyLesson(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link PetEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.
        // Check that the key lesson is not null
        String keyLesson = values.getAsString(KeyLessonEntry.COLUMN_KEY_LESSON);
        if (keyLesson == null) {
            throw new IllegalArgumentException("Enter Key Lesson");
        }

        // Check that the actionStep is valid
        String actionStep = values.getAsString(KeyLessonEntry.COLUMN_ACTION_STEP);
        if (actionStep == null) {
            throw new IllegalArgumentException("Enter Action Step");
        }

        // Check that the date is valid
        String date = values.getAsString(KeyLessonEntry.COLUMN_DATE);
        if (date == null) {
            throw new IllegalArgumentException("Enter Date");
        }


        // Check that the time is valid
        String time = values.getAsString(KeyLessonEntry.COLUMN_TIME);
        if (time == null) {
            throw new IllegalArgumentException("Enter Time");
        }


        // Check that the priority is valid
        Integer priority = values.getAsInteger(KeyLessonEntry.COLUMN_PRIORITY);
        if (priority == null || !KeyLessonEntry.isValidPriority(priority)) {
            throw new IllegalArgumentException("Enter valid priority");
        }

        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(KeyLessonEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KEY_LESSON:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(KeyLessonEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case KEY_LESSON_ID:
                // Delete a single row given by the ID in the URI
                selection = KeyLessonEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(KeyLessonEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KEY_LESSON:
                return KeyLessonEntry.CONTENT_LIST_TYPE;
            case KEY_LESSON_ID:
                return KeyLessonEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
