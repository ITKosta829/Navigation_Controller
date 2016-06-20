package turntotech.org.navigationcontroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanC on 6/1/2016.
 */
public class DataHandler implements DataHandlerINT{

    private List <Company> companyList;
    private List <Product> productList;

    public int currentCompanyPosition;

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
    public void deleteCompany(int index) {
        companyList.remove(index);

    }

    @Override
    public void editCompany(Company company) {

    }

    @Override
    public void addProduct(int companyIndex, String name, Integer logo) {
        companyList.get(companyIndex).addProducts(name, logo);

    }

    @Override
    public void deleteProduct(int companyIndex, int productIndex) {
        companyList.get(companyIndex).deleteProduct(productIndex);

    }

    private static DataHandler instance = new DataHandler();

    private DataHandler(){
        companyList = new ArrayList<>() ;
    }

    public static DataHandler getInstance(){
        return instance;
    }

    public void showMessage(){
        System.out.println("Hello World!");
    }

    public void companiesAndProducts(){

        DataHandler.getInstance().addCompany("Apple", R.drawable.apple_logo);
        DataHandler.getInstance().addProduct(0, "iPhone", R.drawable.i_phone);
        DataHandler.getInstance().addProduct(0, "iPad", R.drawable.i_pad);
        DataHandler.getInstance().addProduct(0, "iPod", R.drawable.i_pod);
        DataHandler.getInstance().addProduct(0, "iMac", R.drawable.i_mac);
        DataHandler.getInstance().addProduct(0, "Mac Book", R.drawable.macbook);
        DataHandler.getInstance().addProduct(0, "Watch", R.drawable.apple_watch);

        DataHandler.getInstance().addCompany("Microsoft", R.drawable.microsoft_logo);
        DataHandler.getInstance().addProduct(1, "Surface", R.drawable.ms_surface);
        DataHandler.getInstance().addProduct(1, "X-Box", R.drawable.x_box);
        DataHandler.getInstance().addProduct(1, "Windows", R.drawable.microsoft_logo);
        DataHandler.getInstance().addProduct(1, "Office", R.drawable.ms_office);
        DataHandler.getInstance().addProduct(1, "Smart Phone", R.drawable.ms_phone);

        DataHandler.getInstance().addCompany("Samsung", R.drawable.samsung_logo);
        DataHandler.getInstance().addProduct(2, "Galaxy Note", R.drawable.galaxy_note);
        DataHandler.getInstance().addProduct(2, "Galaxy Tab", R.drawable.galaxy_tab);
        DataHandler.getInstance().addProduct(2, "Galaxy Gear", R.drawable.galaxy_gear);
        DataHandler.getInstance().addProduct(2, "TV", R.drawable.samsung_tv);
        DataHandler.getInstance().addProduct(2, "Home Appliances", R.drawable.samsung_logo);

        DataHandler.getInstance().addCompany("Sony", R.drawable.sony_logo);
        DataHandler.getInstance().addProduct(3, "Playstation", R.drawable.sony_playstation);
        DataHandler.getInstance().addProduct(3, "Camera", R.drawable.sony_camera);
        DataHandler.getInstance().addProduct(3, "Audio", R.drawable.sony_logo);
        DataHandler.getInstance().addProduct(3, "TV", R.drawable.sony_logo);
        DataHandler.getInstance().addProduct(3, "Mobile Devices", R.drawable.sony_mobile);
    }




}
