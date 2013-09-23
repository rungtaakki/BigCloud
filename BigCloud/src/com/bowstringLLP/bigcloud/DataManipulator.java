package com.bowstringLLP.bigcloud;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataManipulator {
	private static final String DATABASE_NAME = "favourites.db";
	private static final int DATABASE_VERSION = 1;
	static final String TABLE_NAME = "favlist";
	static SQLiteDatabase db;

	public DataManipulator(Context context) {
		OpenHelper openHelper = new OpenHelper(context);
		db = openHelper.getWritableDatabase();
	}

	public long insert(VideoItem item) {
		ContentValues value = new ContentValues();
		value.clear();
		value.put("Video_ID", item.id);
		value.put("Title", item.title);
		value.put("Thumbnail", item.thumb);
		value.put("Duration", item.duration);
		value.put("Link", item.link);

		return db.insert("favlist", null, value);
	}

	public List<VideoItem> read() {
		SQLiteCursor cursor = (SQLiteCursor) db.rawQuery("Select*From "
				+ TABLE_NAME, null);

		List<VideoItem> favList = new ArrayList<VideoItem>();

		if (cursor.moveToFirst()) {
			do {
				favList.add(new VideoItem(cursor.getInt(cursor.getColumnIndex("Video_ID")), cursor.getString(cursor
						.getColumnIndex("Title")), cursor.getInt(cursor
						.getColumnIndex("Duration")), cursor.getString(cursor
						.getColumnIndex("Link")), null, cursor.getString(cursor
						.getColumnIndex("Thumbnail"))));
			} while (cursor.moveToNext());
		}
		cursor.close();

		return favList;
	}

	public void deleteAll() {
		db.delete(TABLE_NAME, null, null);
	}

	private static class OpenHelper extends SQLiteOpenHelper {
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "
					+ TABLE_NAME
					+ " (Video_ID INTEGER PRIMARY KEY, Title TEXT, Thumbnail TEXT, Duration INTEGER, Link TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

	public void delete(VideoItem videoItem) {
		db.delete(TABLE_NAME, "WHERE Video_ID=videoItem.id", null);
	}
}