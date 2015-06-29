package com.ril.cardmanager.Leading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseDump {

	private String mDestXmlFilename = "/sdcard/export.xml";
	private SQLiteDatabase mDb;
	private Exporter mExporter;
	private String TAG = "DatabaseDump";

	public DatabaseDump(SQLiteDatabase db, String destXml) {
		mDb = db;
		mDestXmlFilename = destXml;

		try {
			// create a file on the sdcard to export the
			// database contents to
			File myFile = new File(mDestXmlFilename);
			myFile.createNewFile();

			FileOutputStream fOut = new FileOutputStream(myFile);
			// BufferedOutputStream bos = new BufferedOutputStream(fOut);

			mExporter = new Exporter(fOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exportData() {

		try {
			mExporter.startDbExport(mDb.getPath());

			// get the tables out of the given sqlite database
			String sql = "SELECT * FROM sqlite_master";

			Cursor cur = mDb.rawQuery(sql, new String[0]);
			cur.moveToFirst();

			String tableName;
			while (cur.getPosition() < cur.getCount()) {
				tableName = cur.getString(cur.getColumnIndex("name"));

				// don't process these two tables since they are used
				// for metadata
				if (!tableName.equals("android_metadata")
						&& !tableName.equals("sqlite_sequence")) {
					exportTable(tableName);
				}

				cur.moveToNext();
			}
			mExporter.endDbExport();
			mExporter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exportTable(String tableName) throws IOException {
		mExporter.startTable(tableName);

		// get everything from the table
		String sql = "select * from " + tableName;
		Cursor cur = mDb.rawQuery(sql, new String[0]);
		int numcols = cur.getColumnCount();

		cur.moveToFirst();
		Log.d(TAG, "exportTable");
		// move through the table, creating rows
		// and adding each column with name and value
		// to the row
		while (cur.getPosition() < cur.getCount()) {
			mExporter.startRow();
			String name;
			String val;
			for (int idx = 0; idx < numcols; idx++) {
				name = cur.getColumnName(idx);
				val = cur.getString(idx);
				mExporter.addColumn(name, val);
			}

			mExporter.endRow();
			cur.moveToNext();
		}

		cur.close();

		mExporter.endTable();
	}

	class Exporter {
		private String TAG = "Exporter";
		private static final String CLOSING_WITH_TICK = "'>";
		private static final String START_DB = "<export-database name='";
		private static final String END_DB = "</export-database>\n";
		private static final String START_TABLE = "<table name='";
		private static final String END_TABLE = "</table>\n";
		private static final String START_ROW = "\n<Model>\n";
		private static final String END_ROW = "</Model>\n";
		private static final String START_COL = "<";
		private static final String MIDDLE_COL = "</";
		private static final String END_COL = ">";

		private FileOutputStream mfOut;

		public Exporter(FileOutputStream fOut) {
			mfOut = fOut;
		}

		public void close() throws IOException {
			if (mfOut != null) {
				mfOut.close();
			}
		}

		public void startDbExport(String dbName) throws IOException {
			Log.d(TAG, "startDbExport");
			String stg = START_DB + dbName + CLOSING_WITH_TICK;
			mfOut.write(stg.getBytes());
		}

		public void endDbExport() throws IOException {
			mfOut.write(END_DB.getBytes());
		}

		public void startTable(String tableName) throws IOException {
			Log.d(TAG, "startDbExport");
			String stg = START_TABLE + tableName + CLOSING_WITH_TICK;
			mfOut.write(stg.getBytes());
		}

		public void endTable() throws IOException {
			mfOut.write(END_TABLE.getBytes());
		}

		public void startRow() throws IOException {
			mfOut.write(START_ROW.getBytes());
		}

		public void endRow() throws IOException {
			mfOut.write(END_ROW.getBytes());
		}

		public void addColumn(String name, String val) throws IOException {
			String stg = START_COL + name + END_COL + val + MIDDLE_COL + name
					+ END_COL + "\n";
			mfOut.write(stg.getBytes());
		}
	}

}