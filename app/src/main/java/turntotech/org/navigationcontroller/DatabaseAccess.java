package turntotech.org.navigationcontroller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

/**
 * Created by DeanC on 6/27/2016.
 */
public class DatabaseAccess {

    protected static final String TAG = "DataBaseAdapter";
    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private final Context mContext;

    private DatabaseAccess(Context context) {
        this.mContext = context;
        this.openHelper = new DatabaseOpenHelper(mContext);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public static DatabaseAccess getInstance() {
        return getInstance(null);
    }

    public DatabaseAccess createDatabase() throws SQLException {
        try {
            openHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DatabaseAccess open() throws SQLException {
        try {
            database = openHelper.openDataBase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public Cursor getTestData() {
        try {
            String sql = "SELECT * FROM Product";

            Cursor mCur = database.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();
            }
            return mCur;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public String getCompanyStockNames() {
        try {
            String sql = "SELECT company_stock_name FROM Company";
            String result = "";
            Cursor mCur = database.rawQuery(sql, null);

            if (mCur != null) {
                while (mCur.moveToNext()){
                    result += mCur.getString(0) + ",";
                }
                result = result.substring(0, result.length() - 1);
                mCur.close();
            }
            return result;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public void updateCompanyStockPrice (String price, int id){

        String s1 = "UPDATE Company SET company_stock_price = ";
        String s2 = " WHERE _id = ";
        String s3 = String.valueOf(id);

        database.execSQL(s1 + price + s2 + s3);
    }

    public int howManyCompanies(){

        String sql = "SELECT COUNT(_id) FROM Company";
        String result = "";
        Cursor mCur = database.rawQuery(sql, null);
        result = mCur.getString(0);

        return Integer.parseInt(result);

    }


}
