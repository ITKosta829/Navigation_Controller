package turntotech.org.navigationcontroller.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import turntotech.org.navigationcontroller.CustomListAdapter;
import turntotech.org.navigationcontroller.R;


public class ProductFragment extends ListFragment {

    ProductFragment productFragment;

    public ProductFragment() {
        // Required empty public constructor
    }

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
        int companyPosition = bundle.getInt("CompanyIndex");
        title.setText(bundle.getString("CompanyTitle") + " Products");



        String[] products = null;
        int[] icons = null;

        if(companyPosition == 0){
            products = new String[] { "iPhone", "iPad", "iPod", "iMac", "Mac Book", "Watch" };
            icons = new int[] {R.drawable.i_phone, R.drawable.i_pad, R.drawable.i_pod,
                    R.drawable.i_mac, R.drawable.macbook, R.drawable.apple_watch };
        }

        if(companyPosition == 1){
            products = new String[] { "Surface", "X-Box", "Windows", "Office", "Smart-Phone" };
            icons = new int[] {R.drawable.microsoft_surface, R.drawable.x_box, R.drawable.ms_windows,
                    R.drawable.ms_office, R.drawable.ms_phone };
        }

        if(companyPosition == 2){
            products = new String[] { "Galaxy Note", "Galaxy Tab", "Galaxy Gear", "TV", "Home Appliances" };
            icons = new int[] {R.drawable.galaxy_note, R.drawable.galaxy_tab, R.drawable.galaxy_gear,
                    R.drawable.samsung_tv_logo, R.drawable.samsung_logo };
        }

        if(companyPosition == 3){
            products = new String[] { "Playstation", "Camera", "Audio", "TV", "Mobile Devices" };
            icons = new int[] {R.drawable.sony_playstation, R.drawable.sony_camera, R.drawable.sony_logo,
                    R.drawable.sony_logo, R.drawable.sony_mobile };
        }


        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, products);
        setListAdapter(new CustomListAdapter(getActivity(), products, icons));

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        // TODO Auto-generated method stub
//        super.onListItemClick(l, v, position, id);
//
//        TextView title = (TextView)v.findViewById(android.R.id.text1);
//
//        Bundle bundle = new Bundle();
//        bundle.putInt("CompanyIndex", position);
//        bundle.putString("CompanyTitle", title.getText().toString());
//
//        productFragment.setArguments(bundle);
//
//
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.addToBackStack(null);
//        transaction.replace(R.id.fragment_container, productFragment);
//        transaction.commit();
//
//
//
//    }



}
