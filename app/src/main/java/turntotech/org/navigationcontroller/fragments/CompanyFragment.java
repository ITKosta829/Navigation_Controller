package turntotech.org.navigationcontroller.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import turntotech.org.navigationcontroller.CompanyViewAdapter;
import turntotech.org.navigationcontroller.DataHandler;
import turntotech.org.navigationcontroller.DatabaseAccess;
import turntotech.org.navigationcontroller.R;


public class CompanyFragment extends Fragment {

    private static final String TAG_PRODUCT_FRAG = "product";

    ProductFragment productFragment;
    String companyTitle;
    AddCompanyForm addCompanyForm;
    CompanyViewAdapter adapter;

    DataHandler DH;

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

        DH = DataHandler.getInstance();
        DH.getDatabaseData();
        DH.financeQuery();

        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.grid);

        adapter = new CompanyViewAdapter(getActivity(), DH.getAllCompanies());

        gridView.setAdapter(adapter);

        registerForContextMenu(gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                companyTitle = (String) ((TextView) view.findViewById(R.id.txtStatus)).getText();
                DH.currentCompanyTitle = companyTitle;
                DH.currentCompanyPosition = position;

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, productFragment, TAG_PRODUCT_FRAG);
                transaction.commit();

            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                companyTitle = (String) ((TextView) view.findViewById(R.id.txtStatus)).getText();
                DH.currentCompanyTitle = companyTitle;
                DH.currentCompanyPosition = position;
                Log.d("Long Click", "Company Position: " + position);

                return false;
            }
        });

        DH.adapter = this.adapter;

        return view;
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

                    DatabaseAccess.getInstance().deleteCompany();

                    //adapter = new CompanyViewAdapter(getActivity(), DH.getAllCompanies());
                    adapter.notifyDataSetChanged();
                }
            });
            adb.show();
        }
        return true;
    }
}
