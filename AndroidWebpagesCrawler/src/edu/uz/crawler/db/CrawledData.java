package edu.uz.crawler.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import edu.uz.crawler.R;

@SuppressLint("NewApi")
public class CrawledData {
	private static final String TAG = CrawledData.class.getName();
	private static SimpleCursorAdapter cursorAdapter;
	private static SQLiteDatabase database;
	private static Cursor cursor;

	public final static String TABLE_NAME = "Pages";
	public final static String ID = "_id";
	public final static String DATE = "DATE";
	public final static String WEBURL = "WEBURL";
	public final static String TITLE = "TITLE";
	public final static String TOPICS = "FOUND_TOPICS";
	public final static String CONTENT = "CONTENT";

	public final String[] columns = new String[] { ID, DATE, WEBURL, TITLE, TOPICS, CONTENT };

	public final String CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ID
			+ " integer PRIMARY KEY autoincrement," + DATE + "," + WEBURL + "," + TITLE + "," + TOPICS + "," + CONTENT
			+ ");";
	public final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

	public void open(final SQLiteDatabase database) {
		CrawledData.database = database;
	}

	public void insert(final CrawledPage page) {
		Log.i(TAG, page.getDate());
		Log.i(TAG, page.getUrl());
		Log.i(TAG, page.getTitle());
		Log.i(TAG, page.getFoundTopics());

		ContentValues values = new ContentValues();
		values.put(DATE, page.getDate());
		values.put(WEBURL, page.getUrl());
		values.put(TITLE, page.getTitle());
		values.put(TOPICS, page.getFoundTopics());
		values.put(CONTENT, page.getContent());

		database.insert(TABLE_NAME, null, values);

		refreshCursor();
	}

	public void deleteAll() {
		database.delete(TABLE_NAME, null, null);

		refreshCursor();
	}

	private void refreshCursor() {
		if (cursorAdapter != null) {
			cursorAdapter.changeCursor(cursor());
		}
	}

	private Cursor cursor() {
		if (cursor != null) {
			cursor.close();
		}

		cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		return cursor;
	}

	public SimpleCursorAdapter cursorAdapter(final Context context, final int[] rowsInViewList) {
		if (cursorAdapter == null) {
			String[] rowsInDatabase = new String[] { WEBURL, TITLE, TOPICS, DATE, ID };
			cursorAdapter = new SimpleCursorAdapter(context, R.layout.history_list_item, cursor(), rowsInDatabase,
					rowsInViewList, 0);
		}

		return cursorAdapter;
	}

	public void close() {
		cursor.close();
		database.close();
	}

	public void delete(int id) {
		database.delete(TABLE_NAME, ID + " = " + id, null);

		refreshCursor();
	}

	public String getContentFromPageWithId(int id) {
		Cursor cursor = database.rawQuery("select * from " + TABLE_NAME + " where " + ID + " = " + id, null);
		String result = cursor.getString(cursor.getColumnIndex(CONTENT));
		cursor.close();

		return result;
	}
}
