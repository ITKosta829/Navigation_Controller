package turntotech.org.navigationcontroller;

import java.util.List;

/**
 * Created by DeanC on 6/7/2016.
 */
public interface DataHandlerINT {

    List <Company> getAllCompanies();
    void addCompany (String name, String logo);
    void addCompany (String name, String logo, String stockName);
    void addCompany (int id, String name, String logo, String stockName);
    void deleteCompany (int index);
    void editCompany (int companyIndex, String name);
    void addProduct (int companyIndex, String name, String logo);
    void addProduct (int companyIndex, String name, String logo, String url);
    void deleteProduct (int companyIndex, int productIndex);
    void editCompanyProduct (int companyIndex, int productIndex, String name);
    void addCompanyProductURL (int companyIndex, int productIndex, String url);
    String getCompanyProductURL (int companyIndex, int productIndex);
    void setCompanyStockName (int companyIndex, String name);
    void setCompanyStockPrice (int companyIndex, String price);
    String getCompanyStockName (int companyIndex);


}
