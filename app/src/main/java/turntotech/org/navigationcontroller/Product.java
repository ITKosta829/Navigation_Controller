package turntotech.org.navigationcontroller;

/**
 * Created by DeanC on 6/1/2016.
 */
public class Product {

    private int product_id;
    private String productName;
    private String productIcon;
    private String url;

    public Product (String name, String icon){
        this.productName = name;
        this.productIcon = icon;
    }

    public Product (String name, String icon, String url){
        this(name, icon);
        this.url = url;
    }

    public Product (int id, String name, String icon, String url){
        this(name, icon, url);
        this.product_id = id;
    }

    public int getProduct_ID(){
        return product_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public  String getProductURL(){
        return url;
    }

    public void setProductURL (String url){
        this.url = url;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String icon) {
        this.productIcon = icon;
    }


}
