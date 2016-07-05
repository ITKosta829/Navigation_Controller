package turntotech.org.navigationcontroller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
        new convertUSDtoKRW().execute(USDtoKRW);
        companyList = new ArrayList<>();
        companiesAndProducts();
        DBA = DatabaseAccess.getInstance();
        getDatabaseData();

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

    public void companiesAndProducts() {

        //Apple Products
        addCompany("Apple", "apple_logo");
        setCompanyStockName(0, "NASDAQ:AAPL");
        setCompanyStockPrice(0, "0.00");
        addProduct(0, "iPhone", "i_phone");
        addProduct(0, "iPad", "i_pad");
        addProduct(0, "iPod", "i_pod");
        addProduct(0, "iMac", "i_mac");
        addProduct(0, "Mac Book", "macbook");
        addProduct(0, "Watch", "apple_watch");
        addCompanyProductURL(0, 0, "http://www.apple.com/iphone/");
        addCompanyProductURL(0, 1, "http://www.apple.com/ipad/");
        addCompanyProductURL(0, 2, "http://www.apple.com/ipod/");
        addCompanyProductURL(0, 3, "http://www.apple.com/imac/");
        addCompanyProductURL(0, 4, "http://www.apple.com/macbook-pro/");
        addCompanyProductURL(0, 5, "http://www.apple.com/watch/");

        // Microsoft Products
        addCompany("Microsoft", "microsoft_logo");
        setCompanyStockName(1, "NASDAQ:MSFT");
        setCompanyStockPrice(1, "0.00");
        addProduct(1, "Surface", "ms_surface");
        addProduct(1, "X-Box", "x_box");
        addProduct(1, "Windows", "microsoft_logo");
        addProduct(1, "Office", "ms_office");
        addProduct(1, "Smart Phone", "ms_phone");
        addCompanyProductURL(1, 0, "https://www.microsoft.com/surface/en-us"); // Surface URL
        addCompanyProductURL(1, 1, "http://www.xbox.com/en-US/"); // X-Box URL
        addCompanyProductURL(1, 2, "https://www.microsoft.com/en-us/windows"); // Windows URL
        addCompanyProductURL(1, 3, "https://products.office.com/en-US/"); // Office URL
        addCompanyProductURL(1, 4, "https://www.microsoft.com/en-us/windows/phones"); // Phone URL

        // Samsung Products
        addCompany("Samsung", "samsung_logo");
        setCompanyStockName(2, "KRX:005930");
        setCompanyStockPrice(2, "0.00");
        addProduct(2, "Galaxy Note", "galaxy_note");
        addProduct(2, "Galaxy Tab", "galaxy_tab");
        addProduct(2, "Galaxy Gear", "galaxy_gear");
        addProduct(2, "TV", "samsung_tv");
        addProduct(2, "Home Appliances", "samsung_logo");
        addCompanyProductURL(2, 0, "http://www.samsung.com/us/explore/galaxy-note-5-features-and-specs/?cid=ppc-"); // Note URL
        addCompanyProductURL(2, 1, "http://www.samsung.com/us/mobile/galaxy-tab/"); // Tab URL
        addCompanyProductURL(2, 2, "http://www.samsung.com/us/mobile/wearable-tech/all-products?filter=smartwatches"); // Gear URL
        addCompanyProductURL(2, 3, "http://www.samsung.com/us/televisions/"); // TV URL
        addCompanyProductURL(2, 4, "http://www.samsung.com/us/homeappliances/"); // Appliances URL

        // Sony Products
        addCompany("Sony", "sony_logo");
        setCompanyStockName(3, "NYSE:SNE");
        setCompanyStockPrice(3, "0.00");
        addProduct(3, "Playstation", "sony_playstation");
        addProduct(3, "Camera", "sony_camera");
        addProduct(3, "Audio", "sony_logo");
        addProduct(3, "TV", "sony_logo");
        addProduct(3, "Mobile Devices", "sony_mobile");
        addCompanyProductURL(3, 0, "https://www.playstation.com/en-us/"); // Playstation URL
        addCompanyProductURL(3, 1, "http://www.sony.com/electronics/cameras"); // Camera URL
        addCompanyProductURL(3, 2, "http://www.sony.com/electronics/audio"); // Audio URL
        addCompanyProductURL(3, 3, "http://www.sony.com/electronics/televisions-home-theater"); // TV URL
        addCompanyProductURL(3, 4, "http://www.sony.com/electronics/mobile-tablets"); // Mobile URL

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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void getDatabaseData() {

        DBA.open();
        DBA.getTestData();

        new updateDatabaseFinanceData().execute(url + DBA.getCompanyStockNames());

    }

    private class updateDatabaseFinanceData extends AsyncTask<String, String, String> {

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
                result = result.substring(3);

                JSONArray jarray = new JSONArray(result);
                String stockPrice;
                String exchange;

                for (int i = 0; i < DBA.howManyCompanies(); i++) {

                    JSONObject json = jarray.getJSONObject(i);
                    stockPrice = json.getString("l");
                    exchange = json.getString("e");

                    if (exchange.equals("KRX")) {
                        stockPrice = stockPrice.replaceAll("[^\\d.]+", "");
                        Double d = Double.parseDouble(stockPrice);
                        d = d / valueOfKRW;
                        NumberFormat nf = NumberFormat.getCurrencyInstance();
                        stockPrice = nf.format(d);
                        stockPrice = stockPrice.substring(1);
                    }
                    DBA.updateCompanyStockPrice(stockPrice, i + 1);
                }

                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
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
