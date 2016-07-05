package turntotech.org.navigationcontroller.fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import turntotech.org.navigationcontroller.CompanyCustomListAdapter;
import turntotech.org.navigationcontroller.DataHandler;
import turntotech.org.navigationcontroller.DatabaseAccess;
import turntotech.org.navigationcontroller.R;


public class CompanyFragment extends ListFragment {

    private static final String TAG_PRODUCT_FRAG = "prod";

    ProductFragment productFragment;
    String companyTitle;
    AddCompanyForm addCompanyForm;

    DataHandler dataHandler;

    public CompanyFragment() {
        productFragment = new ProductFragment();
        addCompanyForm = new AddCompanyForm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mCustomView = inflater.inflate(R.layout.custom_actionbar, null);
        TextView title = (TextView) mCustomView.findViewById(R.id.title_text);
        mCustomView.findViewById(R.id.back_text).setVisibility(View.INVISIBLE);
        mCustomView.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getFragmentManager();
                AddCompanyForm addCompanyForm = new AddCompanyForm();
                addCompanyForm.show(fm, "Add Company");

            }
        });

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
        title.setText(R.string.watch_list);



        dataHandler = DataHandler.getInstance();

        dataHandler.financeQuery();

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) view.findViewById(android.R.id.list);

        setListAdapter(new CompanyCustomListAdapter(getActivity(), dataHandler.getAllCompanies()));

        registerForContextMenu(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                companyTitle = (String) ((TextView) view.findViewById(R.id.txtStatus)).getText();
                dataHandler.currentCompanyTitle = companyTitle;
                dataHandler.currentCompanyPosition = position;
                Log.d("Long Click", "Company Position: " + position);

                return false;
            }
        });

        dataHandler.adapter = (ArrayAdapter) getListAdapter();

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        companyTitle = (String) ((TextView) v.findViewById(R.id.txtStatus)).getText();
        dataHandler.currentCompanyTitle = companyTitle;
        dataHandler.currentCompanyPosition = position;

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, productFragment,TAG_PRODUCT_FRAG);
        transaction.commit();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add("Edit");
        menu.add("Delete");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        String title = (String) item.getTitle();

        if (title.equals("Edit")) {

            FragmentManager fm = getActivity().getFragmentManager();
            EditCompanyForm editCompanyForm = new EditCompanyForm();
            editCompanyForm.show(fm, "Edit Company");

        } else if (title.equals("Delete")) {

            AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
            adb.setTitle("Delete?");
            adb.setMessage("Are you sure you want to delete " + companyTitle + " from the list?");
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dataHandler.deleteCompany(dataHandler.currentCompanyPosition);
                    setListAdapter(new CompanyCustomListAdapter(getActivity(), dataHandler.getAllCompanies()));
                }
            });
            adb.show();
        }
        return true;
    }
}
