package turntotech.org.navigationcontroller;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanC on 6/1/2016.
 */
public class DataHandler implements DataHandlerINT {

    private List<Company> companyList;
    // private List<Product> productList;

    public int currentCompanyPosition;
    public int currentProductPosition;
    public String currentCompanyTitle;
    public String currentProductTitle;
    Double valueOfKRW;
    //public CompanyFragment CF;

    private final static String url = "http://finance.google.com/finance/info?q=";
    private final static String USDtoKRW = "http://apilayer.net/api/live?access_key=1561b5de150a11191c5b37bf6e910e1f&currencies=KRW&source=USD&format=1";

    public ArrayAdapter adapter;

    private static DataHandler instance = new DataHandler();

    DatabaseAccess DBA;

    @Override
    public List<Company> getAllCompanies() {
        return companyList;
    }

    @Override
    public void addCompany(String name, String logo) {
        Company company = new Company(name, logo);
        companyList.add(company);

    }

    @Override
    public void addCompany(String name, String logo, String stockName) {
        Company company = new Company(name, logo, stockName);
        companyList.add(company);
    }

    @Override
    public void addCompany(int id, String name, String logo, String stockName) {
        Company company = new Company(id, name, logo, stockName);
        companyList.add(company);
    }

    @Override
    public void deleteCompany(int index) {
        companyList.remove(index);

    }

    @Override
    public void editCompany(int companyIndex, String name) {
        companyList.get(companyIndex).setCompanyName(name);

    }

    @Override
    public void editCompanyProduct(int companyIndex, int productIndex, String name) {
        companyList.get(companyIndex).getProducts().get(productIndex).setProductName(name);
    }

    @Override
    public void addProduct(int companyIndex, String name, String logo) {
        companyList.get(companyIndex).addProducts(name, logo);

    }

    @Override
    public void deleteProduct(int companyIndex, int productIndex) {
        companyList.get(companyIndex).deleteProduct(productIndex);

    }

    @Override
    public void addCompanyProductURL(int companyIndex, int productIndex, String url) {
        companyList.get(companyIndex).getProducts().get(productIndex).setProductURL(url);

    }

    @Override
    public void addProduct(int companyIndex, String name, String logo, String url) {
        companyList.get(companyIndex).addProducts(companyIndex, name, logo, url);
    }

    @Override
    public String getCompanyProductURL(int companyIndex, int productIndex) {
        return companyList.get(companyIndex).getProducts().get(productIndex).getProductURL();

    }

    @Override
    public void setCompanyStockName(int companyIndex, String name) {
        companyList.get(companyIndex).setCompanyStockName(name);
    }

    @Override
    public void setCompanyStockPrice(int companyIndex, String price) {
        companyList.get(companyIndex).setCompanyStockPrice(price);
    }

    @Override
    public String getCompanyStockName(int companyIndex) {
        return companyList.get(companyIndex).getCompanyStockName();
    }

    private DataHandler() {
        DBA = DatabaseAccess.getInstance();
        new convertUSDtoKRW().execute(USDtoKRW);
        companyList = new ArrayList<>();

    }

    public static DataHandler getInstance() {
        return instance;
    }

    public void financeQuery() {
        String query = "";

        for (int i = 0; i < companyList.size(); i++) {
            query += getCompanyStockName(i) + ",";
        }
        query = query.substring(0, query.length() - 1);

        new GetFinanceData().execute(url + query);
    }

    private class GetFinanceData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            StringBuilder sb = new StringBuilder();

            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();

                int HttpResult = urlConnection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(), "utf-8"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    in.close();

                    //System.out.println("" + sb.toString());
                    return sb.toString();

                } else {
                    System.out.println(urlConnection.getResponseMessage());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                result = result.substring(3);

                JSONArray jarray = new JSONArray(result);
                String stockPrice;
                String exchange;

                for (int i = 0; i < companyList.size(); i++) {

                    JSONObject json = jarray.getJSONObject(i);
                    stockPrice = json.getString("l");
                    exchange = json.getString("e");

                    Company c = companyList.get(i);

                    if (exchange.equals("KRX")) {
                        stockPrice = stockPrice.replaceAll("[^\\d.]+", "");
                        Double d = Double.parseDouble(stockPrice);
                        d = d / valueOfKRW;
                        NumberFormat nf = NumberFormat.getCurrencyInstance();
                        stockPrice = nf.format(d);
                        stockPrice = stockPrice.substring(1);
                    }

                    c.setCompanyStockPrice(stockPrice);
                }

                adapter.notifyDataSetChanged();
                updateDatabaseStockPrices();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void getDatabaseData() {
        DBA.open();
        DBA.getTestData();
        DBA.populateCompanyList();

    }

    public void updateDatabaseStockPrices(){

        for (Company c: getAllCompanies()) {

            String price = c.getCompanyStockPrice();
            int id = c.getCompany_ID();

            DBA.updateCompanyStockPrice(price, id);
        }
    }

    private class convertUSDtoKRW extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            StringBuilder sb = new StringBuilder();

            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();

                int HttpResult = urlConnection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(), "utf-8"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    in.close();

                    System.out.println("" + sb.toString());
                    return sb.toString();

                } else {
                    System.out.println(urlConnection.getResponseMessage());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            try {

                String s;

                JSONObject json = new JSONObject(result);
                JSONObject quotes = json.getJSONObject("quotes");
                s = quotes.getString("USDKRW");

                valueOfKRW = Double.parseDouble(s);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



}
