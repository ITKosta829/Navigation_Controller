package turntotech.org.navigationcontroller.fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
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
import java.util.Objects;

import turntotech.org.navigationcontroller.ProductCustomListAdapter;
import turntotech.org.navigationcontroller.DataHandler;
import turntotech.org.navigationcontroller.R;


public class ProductFragment extends ListFragment {

    WebViewFragment webViewFragment;
    AddProductForm addProductForm;

    public ProductFragment() {
        webViewFragment = new WebViewFragment();
        addProductForm = new AddProductForm();
    }

    int companyPosition;
    String companyTitle, productTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        companyPosition = DataHandler.getInstance().currentCompanyPosition;
        companyTitle = bundle.getString("CompanyTitle");

        View mCustomView = inflater.inflate(R.layout.custom_actionbar, null);
        TextView title = (TextView) mCustomView.findViewById(R.id.title_text);
        title.setText(companyTitle + " Products");
        mCustomView.findViewById(R.id.back_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        mCustomView.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getFragmentManager();
                AddProductForm addProductForm = new AddProductForm();
                Bundle bundle = new Bundle();
                bundle.putInt("CompanyIndex", companyPosition);
                bundle.putString("CompanyTitle", companyTitle);
                addProductForm.setArguments(bundle);
                addProductForm.show(fm,"Add Product");

            }
        });

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);



        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);

        setListAdapter(new ProductCustomListAdapter(getActivity(), DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts()));

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                productTitle = (String) ((TextView) view.findViewById(R.id.txtStatus)).getText();

                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + productTitle + " from the list?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        DataHandler.getInstance().getAllCompanies().get(companyPosition).deleteProduct(position);

                        setListAdapter(new ProductCustomListAdapter(getActivity(), DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts()));

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

        productTitle = (String) ((TextView) v.findViewById(R.id.txtStatus)).getText();

        Bundle bundle = new Bundle();
        bundle.putInt("CompanyIndex", companyPosition);
        bundle.putInt("ProductIndex", position);
        bundle.putString("CompanyTitle", companyTitle);
        bundle.putString("ProductTitle", productTitle);

        webViewFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, webViewFragment);
        transaction.commit();
    }


}
