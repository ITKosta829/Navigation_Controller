package turntotech.org.navigationcontroller;

import java.util.List;

/**
 * Created by DeanC on 6/7/2016.
 */
public interface DataHandlerINT {

    public List <Company> getAllCompanies();
    public  void addCompany (String name, Integer logo);
    public  void deleteCompany (int index);
    public  void editCompany (Company company);
    public void addProduct(int companyIndex, String name, Integer logo);
    public void deleteProduct(int companyIndex, int productIndex);

}
