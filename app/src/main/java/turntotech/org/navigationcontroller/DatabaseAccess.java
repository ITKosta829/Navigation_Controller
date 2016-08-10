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
    DataHandler DH;

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
        DH = DataHandler.getInstance();
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
                while (mCur.moveToNext()) {
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

    public void populateCompanyList() {

        try {
            String sql = "SELECT _id, company_name, company_icon, company_stock_name FROM Company";

            Cursor mCur = database.rawQuery(sql, null);

            if (mCur != null) {
                DH.getAllCompanies().clear();
                while (mCur.moveToNext()) {
                    String _id = mCur.getString(0);
                    int ID = Integer.valueOf(_id);
                    String company_name = mCur.getString(1);
                    String company_logo = mCur.getString(2);
                    String company_stock_name = mCur.getString(3);

                    DH.addCompany(ID, company_name, company_logo, company_stock_name);
                }
                mCur.close();
            }

        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }

        for (Company c: DH.getAllCompanies()) {
            populateProductList(c);
        }
    }

    public void populateProductList(Company c) {

        try {
            String sql = "SELECT _id, product_name, product_icon, product_url FROM Product " +
                    "WHERE company_id = " + c.getCompany_ID();

            Cursor mCur = database.rawQuery(sql, null);

            if (mCur != null) {

                while (mCur.moveToNext()) {

                    String product_id = mCur.getString(0);
                    int id = Integer.valueOf(product_id);
                    String product_name = mCur.getString(1);
                    String product_icon = mCur.getString(2);
                    String product_url = mCur.getString(3);

                    c.addProducts(id, product_name, product_icon, product_url);

                }
                mCur.close();
            }

        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public void updateCompanyStockPrice(String price, int id) {

        String s1 = "UPDATE Company SET company_stock_price = ";
        String s2 = " WHERE _id = ";
        String s3 = String.valueOf(id);

        database.execSQL(s1 + price + s2 + s3);
    }

//    public int howManyCompanies() {
//
//        String sql = "SELECT COUNT(_id) FROM Company";
//        String result = "";
//        Cursor mCur = database.rawQuery(sql, null);
//        result = mCur.getString(0);
//
//        return Integer.parseInt(result);
//    }

    public void addCompany(String name, String stock_name) {

        try {

            String sql = "INSERT INTO Company (company_name, company_icon, company_stock_name)" +
                    " VALUES (\"" + name + "\", \"unknown_logo\", \"" + stock_name + "\")";

            database.execSQL(sql);

            DH.addCompany(name, "unknown_logo", stock_name);

        } catch (SQLException mSQLException) {
            Log.e(TAG, "addCompany >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public void deleteCompany(){

        int id = DH.getAllCompanies().get(DH.currentCompanyPosition).getCompany_ID();

        String s1 = "DELETE FROM Company WHERE _id = ";
        String s2 = String.valueOf(id);

        database.execSQL(s1 + s2);

        DH.deleteCompany(DH.currentCompanyPosition);
    }

    public void editCompanyName (String name){

        int id = DH.getAllCompanies().get(DH.currentCompanyPosition).getCompany_ID();

        String s1 = "UPDATE Company SET company_name = \"";
        String s2 = "\" WHERE _id = ";
        String s3 = String.valueOf(id);

        database.execSQL(s1 + name + s2 + s3);

        DH.editCompany(DH.currentCompanyPosition, name);
    }

    public void addCompanyProduct(String name){

        int c_id = DH.getAllCompanies().get(DH.currentCompanyPosition).getCompany_ID();

        try {

            String sql = "INSERT INTO Product (company_id, product_name, product_icon)" +
                    " VALUES (" + c_id+ ", \"" + name + "\", \"unknown_logo\")";

            database.execSQL(sql);

            DH.addProduct(DH.currentCompanyPosition, name, "unknown_logo");

        } catch (SQLException mSQLException) {
            Log.e(TAG, "addCompanyProduct >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public void deleteCompanyProduct(){

        int p_id = DH.getAllCompanies().get(DH.currentCompanyPosition).getProducts().get(DH.currentProductPosition).getProduct_ID();
        String id = String.valueOf(p_id);

        try {

            String sql = "DELETE FROM Product WHERE _id = " + id;

            database.execSQL(sql);

            DH.getAllCompanies().get(DH.currentCompanyPosition).deleteProduct(DH.currentProductPosition);

        } catch (SQLException mSQLException) {
            Log.e(TAG, "addCompany >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public void editCompanyProduct(String name){

        int p_id = DH.getAllCompanies().get(DH.currentCompanyPosition).getProducts().get(DH.currentProductPosition).getProduct_ID();
        String id = String.valueOf(p_id);

        try {

            String s1 = "UPDATE Product SET product_name = \"";
            String s2 = "\" WHERE _id = ";
            String s3 = String.valueOf(id);

            database.execSQL(s1 + name + s2 + s3);

            DH.editCompanyProduct(DH.currentCompanyPosition, DH.currentProductPosition, name);

            //DH.getAllCompanies().get(DH.currentCompanyPosition).deleteProduct(DH.currentProductPosition);

        } catch (SQLException mSQLException) {
            Log.e(TAG, "addCompany >>" + mSQLException.toString());
            throw mSQLException;
        }

    }

}
