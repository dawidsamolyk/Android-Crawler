package edu.uz.crawler.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import edu.uz.crawler.R;

@SuppressLint("NewApi")
public class CrawledData {
	private SimpleCursorAdapter cursorAdapter;
	private SQLiteDatabase database;
	private Cursor cursor;

	public final String TABLE_NAME = "Pages";
	private final String ID = "_ID";
	public final String DATE = "DATE";
	public final String WEBURL = "WEBURL";
	public final String TITLE = "TITLE";
	public final String TOPICS = "FOUND_TOPICS";
	public final String CONTENT = "CONTENT";

	public final String[] columns = new String[] { ID, DATE, WEBURL, TITLE, TOPICS };

	public final String CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ID
			+ " integer PRIMARY KEY autoincrement," + DATE + "," + WEBURL + "," + TITLE + "," + TOPICS + "," + CONTENT
			+ ");";
	public final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

	public void open(final SQLiteDatabase database) {
		this.database = database;
	}

	public void insert(final CrawledPage page) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(DATE, page.getDate());
		initialValues.put(WEBURL, page.getUrl());
		initialValues.put(TITLE, page.getTitle());
		initialValues.put(TOPICS, page.getFoundTopics());
		initialValues.put(CONTENT, page.getContent());

		database.insert(TABLE_NAME, null, initialValues);
		cursorAdapter.changeCursor(cursor());
	}

	public void deleteAll() {
		database.delete(TABLE_NAME, null, null);
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
			cursorAdapter = new SimpleCursorAdapter(context, R.layout.history_list_item, cursor(), new String[] { DATE,
					WEBURL, TITLE, TOPICS }, rowsInViewList, 0);
		}

		return cursorAdapter;
	}

	public void close() {
		cursor.close();
		database.close();
	}
}
