package edu.uz.crawler.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

public class DatabaseHelper extends SQLiteOpenHelper {
	private final CrawledData table;

	private static final String DATABASE_NAME = "crawler.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(final Context context, final CrawledData table) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.table = table;
		this.table.open(getWritableDatabase());
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		db.execSQL(table.CREATE);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		db.execSQL(table.DROP);
		onCreate(db);
	}

	public void insert(final CrawledPage page) {
		table.insert(page);
	}

	public SimpleCursorAdapter cursorAdapter(final Context context, final int[] rowsInViewList) {
		return table.cursorAdapter(context, rowsInViewList);
	}

	public void deleteAll() {
		table.deleteAll();
	}

	public void close() {
		table.close();
		this.close();
	}
}
