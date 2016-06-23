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
    private List<Product> productList;

    public int currentCompanyPosition;
    public int currentProductPosition;
    public String currentCompanyTitle;
    public String currentProductTitle;

    private static String url = "http://finance.google.com/finance/info?q=";

    public ArrayAdapter adapter;

    @Override
    public List<Company> getAllCompanies() {
        return companyList;
    }

    @Override
    public void addCompany(String name, Integer logo) {
        Company company = new Company(name, logo);
        companyList.add(company);

    }

    @Override
    public void addCompany(String name, Integer logo, String stockName) {
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
    public void addProduct(int companyIndex, String name, Integer logo) {
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
        return DataHandler.getInstance().companyList.get(companyIndex).getCompanyStockName();
    }

    private static DataHandler instance = new DataHandler();

    private DataHandler() {
        companyList = new ArrayList<>();
    }

    public static DataHandler getInstance() {
        return instance;
    }

    public void financeQuery() {
        String query = "";

        for (int i = 0; i < companyList.size(); i++) {
            query += DataHandler.getInstance().getCompanyStockName(i) + ",";
        }
        query = query.substring(0, query.length() - 1);

        new GetFinanceData().execute(url + query);
    }

    public void companiesAndProducts() {

        //Apple Products
        DataHandler.getInstance().addCompany("Apple", R.drawable.apple_logo);
        DataHandler.getInstance().setCompanyStockName(0, "NASDAQ:AAPL");
        DataHandler.getInstance().setCompanyStockPrice(0, "0.00");
        DataHandler.getInstance().addProduct(0, "iPhone", R.drawable.i_phone);
        DataHandler.getInstance().addProduct(0, "iPad", R.drawable.i_pad);
        DataHandler.getInstance().addProduct(0, "iPod", R.drawable.i_pod);
        DataHandler.getInstance().addProduct(0, "iMac", R.drawable.i_mac);
        DataHandler.getInstance().addProduct(0, "Mac Book", R.drawable.macbook);
        DataHandler.getInstance().addProduct(0, "Watch", R.drawable.apple_watch);
        DataHandler.getInstance().addCompanyProductURL(0, 0, "http://www.apple.com/iphone/");
        DataHandler.getInstance().addCompanyProductURL(0, 1, "http://www.apple.com/ipad/");
        DataHandler.getInstance().addCompanyProductURL(0, 2, "http://www.apple.com/ipod/");
        DataHandler.getInstance().addCompanyProductURL(0, 3, "http://www.apple.com/imac/");
        DataHandler.getInstance().addCompanyProductURL(0, 4, "http://www.apple.com/macbook-pro/");
        DataHandler.getInstance().addCompanyProductURL(0, 5, "http://www.apple.com/watch/");

        // Microsoft Products
        DataHandler.getInstance().addCompany("Microsoft", R.drawable.microsoft_logo);
        DataHandler.getInstance().setCompanyStockName(1, "NASDAQ:MSFT");
        DataHandler.getInstance().setCompanyStockPrice(1, "0.00");
        DataHandler.getInstance().addProduct(1, "Surface", R.drawable.ms_surface);
        DataHandler.getInstance().addProduct(1, "X-Box", R.drawable.x_box);
        DataHandler.getInstance().addProduct(1, "Windows", R.drawable.microsoft_logo);
        DataHandler.getInstance().addProduct(1, "Office", R.drawable.ms_office);
        DataHandler.getInstance().addProduct(1, "Smart Phone", R.drawable.ms_phone);
        DataHandler.getInstance().addCompanyProductURL(1, 0, "https://www.microsoft.com/surface/en-us"); // Surface URL
        DataHandler.getInstance().addCompanyProductURL(1, 1, "http://www.xbox.com/en-US/"); // X-Box URL
        DataHandler.getInstance().addCompanyProductURL(1, 2, "https://www.microsoft.com/en-us/windows"); // Windows URL
        DataHandler.getInstance().addCompanyProductURL(1, 3, "https://products.office.com/en-US/"); // Office URL
        DataHandler.getInstance().addCompanyProductURL(1, 4, "https://www.microsoft.com/en-us/windows/phones"); // Phone URL

        // Samsung Products
        DataHandler.getInstance().addCompany("Samsung", R.drawable.samsung_logo);
        DataHandler.getInstance().setCompanyStockName(2, "KRX:005930");
        DataHandler.getInstance().setCompanyStockPrice(2, "0.00");
        DataHandler.getInstance().addProduct(2, "Galaxy Note", R.drawable.galaxy_note);
        DataHandler.getInstance().addProduct(2, "Galaxy Tab", R.drawable.galaxy_tab);
        DataHandler.getInstance().addProduct(2, "Galaxy Gear", R.drawable.galaxy_gear);
        DataHandler.getInstance().addProduct(2, "TV", R.drawable.samsung_tv);
        DataHandler.getInstance().addProduct(2, "Home Appliances", R.drawable.samsung_logo);
        DataHandler.getInstance().addCompanyProductURL(2, 0, "http://www.samsung.com/us/explore/galaxy-note-5-features-and-specs/?cid=ppc-"); // Note URL
        DataHandler.getInstance().addCompanyProductURL(2, 1, "http://www.samsung.com/us/mobile/galaxy-tab/"); // Tab URL
        DataHandler.getInstance().addCompanyProductURL(2, 2, "http://www.samsung.com/us/mobile/wearable-tech/all-products?filter=smartwatches"); // Gear URL
        DataHandler.getInstance().addCompanyProductURL(2, 3, "http://www.samsung.com/us/televisions/"); // TV URL
        DataHandler.getInstance().addCompanyProductURL(2, 4, "http://www.samsung.com/us/homeappliances/"); // Appliances URL

        // Sony Products
        DataHandler.getInstance().addCompany("Sony", R.drawable.sony_logo);
        DataHandler.getInstance().setCompanyStockName(3, "NYSE:SNE");
        DataHandler.getInstance().setCompanyStockPrice(3, "0.00");
        DataHandler.getInstance().addProduct(3, "Playstation", R.drawable.sony_playstation);
        DataHandler.getInstance().addProduct(3, "Camera", R.drawable.sony_camera);
        DataHandler.getInstance().addProduct(3, "Audio", R.drawable.sony_logo);
        DataHandler.getInstance().addProduct(3, "TV", R.drawable.sony_logo);
        DataHandler.getInstance().addProduct(3, "Mobile Devices", R.drawable.sony_mobile);
        DataHandler.getInstance().addCompanyProductURL(3, 0, "https://www.playstation.com/en-us/"); // Playstation URL
        DataHandler.getInstance().addCompanyProductURL(3, 1, "http://www.sony.com/electronics/cameras"); // Camera URL
        DataHandler.getInstance().addCompanyProductURL(3, 2, "http://www.sony.com/electronics/audio"); // Audio URL
        DataHandler.getInstance().addCompanyProductURL(3, 3, "http://www.sony.com/electronics/televisions-home-theater"); // TV URL
        DataHandler.getInstance().addCompanyProductURL(3, 4, "http://www.sony.com/electronics/mobile-tablets"); // Mobile URL

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

                for (int i = 0; i < DataHandler.getInstance().companyList.size(); i++) {

                    JSONObject json = jarray.getJSONObject(i);
                    stockPrice = json.getString("l");
                    exchange = json.getString("e");

                    Company c = DataHandler.getInstance().companyList.get(i);

                    if (exchange.equals("KRX")){
                        stockPrice = stockPrice.replaceAll("[^\\d.]+", "");
                        Double d = Double.parseDouble(stockPrice);
                        d = d / 1143.30;
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

}
