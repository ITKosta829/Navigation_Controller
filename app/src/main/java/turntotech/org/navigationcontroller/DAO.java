package turntotech.org.navigationcontroller;

/**
 * Created by DeanC on 6/1/2016.
 */
public class DAO {

    private static DAO instance = new DAO();

    private DAO(){}

    public static DAO getInstance(){
        return instance;
    }

    public void showMessage(){
        System.out.println("Hello World!");
    }
}
