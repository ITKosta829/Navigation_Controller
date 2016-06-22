package turntotech.org.navigationcontroller;

/**
 * Created by DeanC on 6/1/2016.
 */
public class Product {

    private String productName;
    private Integer productIcon;
    private String url;

    public Product (String name, Integer icon){
        this.productName = name;
        this.productIcon = icon;
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

    public Integer getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(Integer icon) {
        this.productIcon = icon;
    }


}
