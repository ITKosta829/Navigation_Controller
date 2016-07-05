package turntotech.org.navigationcontroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanC on 6/1/2016.
 */
public class Company {

    private String companyName;
    private String companyIcon;
    private String companyStockName;
    private String companyStockPrice;
    private List <Product> productList;

    public Company (String name, String icon){
        this.companyName = name;
        this.companyIcon = icon;
        this.productList = new ArrayList<>();
    }

    public Company (String name, String icon, String stockName){
        this(name, icon);
        this.companyStockName = stockName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }

    public String getCompanyStockName() {
        return companyStockName;
    }

    public void setCompanyStockName(String name) {
        this.companyStockName = name;
    }

    public String getCompanyStockPrice() {
        return companyStockPrice;
    }

    public void setCompanyStockPrice(String price) {
        this.companyStockPrice = price;
    }

    public String getCompanyIcon() {
        return companyIcon;
    }

    public void setCompanyIcon(String icon) {
        this.companyIcon = icon;
    }

    public List<Product> getProducts() {
        return productList;
    }

    public void addProducts(String name, String logo){
        Product product = new Product(name, logo);
        productList.add(product);
    }

    public void deleteProduct(int index) {
        productList.remove(index);

    }

}
