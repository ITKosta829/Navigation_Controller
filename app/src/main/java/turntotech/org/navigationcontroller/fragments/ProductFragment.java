package turntotech.org.navigationcontroller.fragments;

import android.app.AlertDialog;
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

    public ProductFragment() {
        webViewFragment = new WebViewFragment();
    }

    int companyPosition;
    String companyTitle, productTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mCustomView = inflater.inflate(R.layout.custom_actionbar, null);


        TextView title = (TextView) mCustomView.findViewById(R.id.title_text);
        mCustomView.findViewById(R.id.back_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);

        Bundle bundle = this.getArguments();

        companyPosition = bundle.getInt("CompanyIndex");
        companyTitle = bundle.getString("CompanyTitle");
        title.setText(companyTitle + " Products");

        if (Objects.equals(companyTitle, "Apple")) {
            if (DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts().isEmpty()) {

                DataHandler.getInstance().addProduct(companyPosition, "iPhone", R.drawable.i_phone);
                DataHandler.getInstance().addProduct(companyPosition, "iPad", R.drawable.i_pad);
                DataHandler.getInstance().addProduct(companyPosition, "iPod", R.drawable.i_pod);
                DataHandler.getInstance().addProduct(companyPosition, "iMac", R.drawable.i_mac);
                DataHandler.getInstance().addProduct(companyPosition, "Mac Book", R.drawable.macbook);
                DataHandler.getInstance().addProduct(companyPosition, "Watch", R.drawable.apple_watch);

            }
        }

        if (Objects.equals(companyTitle, "Microsoft")) {
            if (DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts().isEmpty()) {

                DataHandler.getInstance().addProduct(companyPosition, "Surface", R.drawable.ms_surface);
                DataHandler.getInstance().addProduct(companyPosition, "X-Box", R.drawable.x_box);
                DataHandler.getInstance().addProduct(companyPosition, "Windows", R.drawable.microsoft_logo);
                DataHandler.getInstance().addProduct(companyPosition, "Office", R.drawable.ms_office);
                DataHandler.getInstance().addProduct(companyPosition, "Smart Phone", R.drawable.ms_phone);

            }
        }

        if (Objects.equals(companyTitle, "Samsung")) {
            if (DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts().isEmpty()) {

                DataHandler.getInstance().addProduct(companyPosition, "Galaxy Note", R.drawable.galaxy_note);
                DataHandler.getInstance().addProduct(companyPosition, "Galaxy Tab", R.drawable.galaxy_tab);
                DataHandler.getInstance().addProduct(companyPosition, "Galaxy Gear", R.drawable.galaxy_gear);
                DataHandler.getInstance().addProduct(companyPosition, "TV", R.drawable.samsung_tv);
                DataHandler.getInstance().addProduct(companyPosition, "Home Appliances", R.drawable.samsung_logo);

            }
        }

        if (Objects.equals(companyTitle, "Sony")) {
            if (DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts().isEmpty()) {

                DataHandler.getInstance().addProduct(companyPosition, "Playstation", R.drawable.sony_playstation);
                DataHandler.getInstance().addProduct(companyPosition, "Camera", R.drawable.sony_camera);
                DataHandler.getInstance().addProduct(companyPosition, "Audio", R.drawable.sony_logo);
                DataHandler.getInstance().addProduct(companyPosition, "TV", R.drawable.sony_logo);
                DataHandler.getInstance().addProduct(companyPosition, "Mobile Devices", R.drawable.sony_mobile);

            }
        }

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);

        if (Objects.equals(companyTitle, "Apple"))
            setListAdapter(new ProductCustomListAdapter(getActivity(), DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts()));
        if (Objects.equals(companyTitle, "Microsoft"))
            setListAdapter(new ProductCustomListAdapter(getActivity(), DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts()));
        if (Objects.equals(companyTitle, "Samsung"))
            setListAdapter(new ProductCustomListAdapter(getActivity(), DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts()));
        if (Objects.equals(companyTitle, "Sony"))
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

                        if (Objects.equals(companyTitle, "Apple"))
                            setListAdapter(new ProductCustomListAdapter(getActivity(), DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts()));
                        if (Objects.equals(companyTitle, "Microsoft"))
                            setListAdapter(new ProductCustomListAdapter(getActivity(), DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts()));
                        if (Objects.equals(companyTitle, "Samsung"))
                            setListAdapter(new ProductCustomListAdapter(getActivity(), DataHandler.getInstance().getAllCompanies().get(companyPosition).getProducts()));
                        if (Objects.equals(companyTitle, "Sony"))
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
