package com.celticwolf.alex;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	
	private static String DB_URL = "http://schmidt-mathias.ch/Alex/sqlbeerlist.sqlite";

	private static String DB_PATH = "/data/data/com.celticwolf.alex/databases/";

	private static String DB_NAME = "sqlbeerlist.sqlite";

	private static final String DATABASE_TABLE_BEERS = "beers";

	private static final String DATABASE_TABLE_NOBEERS = "nobeers";

	private SQLiteDatabase myDataBase;

	public static final String KEY_ROWID = "_id";

	public static final String KEY_NAME = "brand";

	public static final String KEY_COUNTRY = "country";

	public static final String KEY_NONAME = "fakebrand";

	public static final String KEY_NOCOUNTRY = "nobeercountry";

	private final Context myContext;

	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, 1);

		this.myContext = context;

	}

	/**
	 * 
	 * Creates a empty database on the system and rewrites it with your own
	 * 
	 * database.
	 * 
	 * */

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();
		SQLiteDatabase db_Read = null;

		if (dbExist) {

			// do nothing - database already exist

		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.

			db_Read = this.getReadableDatabase();
			db_Read.close();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}

		}

	}

	/**
	 * 
	 * Check if the database already exist to avoid re-copying the file each
	 * 
	 * time you open the application.
	 * 
	 * 
	 * 
	 * @return true if it exists, false if it doesn't
	 */

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {

			String myPath = DB_PATH + DB_NAME;

			checkDB = SQLiteDatabase
					.openDatabase(
							myPath,
							null,
							(SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS));

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;

	}

	/**
	 * 
	 * Copies your database from your local assets-folder to the just created
	 * 
	 * empty database in the system folder, from where it can be accessed and
	 * 
	 * handled. This is done by transfering bytestream.
	 * 
	 * */

	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		downloadDatabase(DB_URL);

		//InputStream myInput = myContext.getAssets().open(DB_NAME);
		InputStream myInput = new FileInputStream("/sdcard/"+DB_NAME);

		// Path to the just created empty db

		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream

		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile

		byte[] buffer = new byte[1024];

		int length;

		while ((length = myInput.read(buffer)) > 0) {

			myOutput.write(buffer, 0, length);

		}

		// Close the streams

		myOutput.flush();

		myOutput.close();

		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database

		String myPath = DB_PATH + DB_NAME;

		myDataBase = SQLiteDatabase
				.openDatabase(
						myPath,
						null,
						(SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS));

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)

			myDataBase.close();

		super.close();

	}
	/*
	public ArrayList<String> getContent(String sTable, String sColumn) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		Cursor c = myDataBase.rawQuery("SELECT _id, "+sColumn+" FROM " + sTable, null);
		c.moveToFirst();
		// Check if our result was valid.
		if (c != null) {
			// Loop through all Results
			do {
				result.add(c.getString(c.getColumnIndex(KEY_NAME)));
			} while (c.moveToNext());
		}
		close();
		return result;
	}*/

	public ArrayList<String> getbeers() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		Cursor c = myDataBase.rawQuery("SELECT _id, brand FROM " + DATABASE_TABLE_BEERS, null);
		c.moveToFirst();
		// Check if our result was valid.
		if (c != null) {
			// Loop through all Results
			do {
				result.add(c.getString(c.getColumnIndex(KEY_NAME)));
			} while (c.moveToNext());
		}
		close();
		return result;
	}

	public ArrayList<String> getcountries() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		Cursor c = myDataBase.rawQuery("SELECT _id, country FROM "
				+ DATABASE_TABLE_BEERS, null);
		c.moveToFirst();
		// Check if our result was valid.
		if (c != null) {
			// Loop through all Results
			do {
				result.add(c.getString(c.getColumnIndex(KEY_COUNTRY)));
			} while (c.moveToNext());
		}
		close();

		return result;

	}

	public ArrayList<String> getnobeers() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		Cursor c = myDataBase.rawQuery("SELECT _id, fakebrand FROM " + DATABASE_TABLE_NOBEERS, null);
		c.moveToFirst();
		// Check if our result was valid.
		if (c != null) {
			// Loop through all Results
			do {
				result.add(c.getString(c.getColumnIndex(KEY_NONAME)));
			} while (c.moveToNext());
		}
		close();
		return result;
	}

	public ArrayList<String> getnobeercountries() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		Cursor c = myDataBase.rawQuery("SELECT _id, nobeercountry FROM "
				+ DATABASE_TABLE_NOBEERS, null);
		c.moveToFirst();
		// Check if our result was valid.
		if (c != null) {
			// Loop through all Results
			do {
				result.add(c.getString(c.getColumnIndex(KEY_NOCOUNTRY)));
			} while (c.moveToNext());
		}
		close();

		return result;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	private void downloadDatabase(String sUrl){
		DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute(new String[] { sUrl });
	}
	
	public void updateDatabase() throws IOException {
		try {

			copyDataBase();

		} catch (IOException e) {
			
			throw new Error("Error copying database");

		}
		
	}

	// Add your public helper methods to access and get content from the

	// database.

	// You could return cursors by doing "return myDataBase.query(....)" so it'd

	// be easy

	// to you to create adapters for your views.
}