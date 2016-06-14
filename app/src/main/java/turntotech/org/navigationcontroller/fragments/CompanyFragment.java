package turntotech.org.navigationcontroller.fragments;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import turntotech.org.navigationcontroller.Company;
import turntotech.org.navigationcontroller.CompanyCustomListAdapter;
import turntotech.org.navigationcontroller.DataHandler;
import turntotech.org.navigationcontroller.R;


public class CompanyFragment extends ListFragment {

    ProductFragment productFragment;
    String companyTitle;

    public CompanyFragment() {
        productFragment = new ProductFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mCustomView = inflater.inflate(R.layout.custom_actionbar, null);
        TextView title = (TextView) mCustomView.findViewById(R.id.title_text);
        mCustomView.findViewById(R.id.back_text).setVisibility(View.INVISIBLE);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
        title.setText(R.string.watch_list);

        if (DataHandler.getInstance().getAllCompanies().isEmpty()) {
            DataHandler.getInstance().addCompany("Apple", R.drawable.apple_logo);
            DataHandler.getInstance().addCompany("Microsoft", R.drawable.microsoft_logo);
            DataHandler.getInstance().addCompany("Samsung", R.drawable.samsung_logo);
            DataHandler.getInstance().addCompany("Sony", R.drawable.sony_logo);
        }

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) view.findViewById(android.R.id.list);

        setListAdapter(new CompanyCustomListAdapter(getActivity(), DataHandler.getInstance().getAllCompanies()));

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                companyTitle = (String) ((TextView) view.findViewById(R.id.txtStatus)).getText();

                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + companyTitle + " from the list?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        DataHandler.getInstance().deleteCompany(position);

                        setListAdapter(new CompanyCustomListAdapter(getActivity(), DataHandler.getInstance().getAllCompanies()));

                    }
                });
                adb.show();

                return true;
            }
        });

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        companyTitle = (String) ((TextView) v.findViewById(R.id.txtStatus)).getText();

        Bundle bundle = new Bundle();
        bundle.putInt("CompanyIndex", position);
        bundle.putString("CompanyTitle", companyTitle);

        productFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, productFragment);
        transaction.commit();
    }

}
