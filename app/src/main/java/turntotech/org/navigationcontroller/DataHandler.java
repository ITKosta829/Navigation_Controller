package turntotech.org.navigationcontroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanC on 6/1/2016.
 */
public class DataHandler implements DataHandlerINT{

    private List <Company> companyList;
    private List <Product> productList;

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




}
