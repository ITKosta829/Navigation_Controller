package turntotech.org.navigationcontroller.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

import turntotech.org.navigationcontroller.CustomListAdapter;
import turntotech.org.navigationcontroller.R;


public class ProductFragment extends ListFragment {

    WebViewFragment webViewFragment;

    public ProductFragment() {
        webViewFragment = new WebViewFragment();
    }

    int companyPosition;
    String companyTitle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mCustomView = inflater.inflate(R.layout.custom_actionbar, null);
        TextView title = (TextView)mCustomView.findViewById(R.id.title_text);
        mCustomView.findViewById(R.id.back_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);

        Bundle bundle = this.getArguments();

        companyPosition = bundle.getInt("CompanyIndex");
        companyTitle = bundle.getString("CompanyTitle");
        title.setText(companyTitle + " Products");

        String[] products = null;
        int[] icons = null;

        if(Objects.equals(companyTitle, "Apple")){
            products = new String[] { "iPhone", "iPad", "iPod", "iMac", "Mac Book", "Watch" };
            icons = new int[] {R.drawable.i_phone, R.drawable.i_pad, R.drawable.i_pod,
                    R.drawable.i_mac, R.drawable.macbook, R.drawable.apple_watch };
        }

        if(Objects.equals(companyTitle, "Microsoft")){
            products = new String[] { "Surface", "X-Box", "Windows", "Office", "Smart Phone" };
            icons = new int[] {R.drawable.ms_surface, R.drawable.x_box, R.drawable.microsoft_logo,
                    R.drawable.ms_office, R.drawable.ms_phone };
        }

        if(Objects.equals(companyTitle, "Samsung")){
            products = new String[] { "Galaxy Note", "Galaxy Tab", "Galaxy Gear", "TV", "Home Appliances" };
            icons = new int[] {R.drawable.galaxy_note, R.drawable.galaxy_tab, R.drawable.galaxy_gear,
                    R.drawable.samsung_tv, R.drawable.samsung_logo };
        }

        if(Objects.equals(companyTitle, "Sony")){
            products = new String[] { "Playstation", "Camera", "Audio", "TV", "Mobile Devices" };
            icons = new int[] {R.drawable.sony_playstation, R.drawable.sony_camera, R.drawable.sony_logo,
                    R.drawable.sony_logo, R.drawable.sony_mobile };
        }

        setListAdapter(new CustomListAdapter(getActivity(), products, icons));

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        String productTitle = (String) ((TextView)v.findViewById(R.id.txtStatus)).getText();

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
