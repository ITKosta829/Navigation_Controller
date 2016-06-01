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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import turntotech.org.navigationcontroller.CustomListAdapter;
import turntotech.org.navigationcontroller.R;


public class ProductFragment extends ListFragment {

    WebViewFragment webViewFragment;

    public ProductFragment() {
        webViewFragment = new WebViewFragment();
    }

    int companyPosition;
    String companyTitle, productTitle;
    String[] appleProducts, msProducts, samsungProducts, sonyProducts;
    Integer[] appleIcons, msIcons, samsungIcons, sonyIcons;

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
            if (appleProducts == null) {
                appleProducts = new String[]{"iPhone", "iPad", "iPod", "iMac", "Mac Book", "Watch"};
                appleIcons = new Integer[]{R.drawable.i_phone, R.drawable.i_pad, R.drawable.i_pod,
                        R.drawable.i_mac, R.drawable.macbook, R.drawable.apple_watch};
            }
        }

        if (Objects.equals(companyTitle, "Microsoft")) {
            if (msProducts == null) {
                msProducts = new String[]{"Surface", "X-Box", "Windows", "Office", "Smart Phone"};
                msIcons = new Integer[]{R.drawable.ms_surface, R.drawable.x_box, R.drawable.microsoft_logo,
                        R.drawable.ms_office, R.drawable.ms_phone};
            }
        }

        if (Objects.equals(companyTitle, "Samsung")) {
            if (samsungProducts == null) {
                samsungProducts = new String[]{"Galaxy Note", "Galaxy Tab", "Galaxy Gear", "TV", "Home Appliances"};
                samsungIcons = new Integer[]{R.drawable.galaxy_note, R.drawable.galaxy_tab, R.drawable.galaxy_gear,
                        R.drawable.samsung_tv, R.drawable.samsung_logo};
            }
        }

        if (Objects.equals(companyTitle, "Sony")) {
            if (sonyProducts == null) {
                sonyProducts = new String[]{"Playstation", "Camera", "Audio", "TV", "Mobile Devices"};
                sonyIcons = new Integer[]{R.drawable.sony_playstation, R.drawable.sony_camera, R.drawable.sony_logo,
                        R.drawable.sony_logo, R.drawable.sony_mobile};
            }
        }

        // Apple Data Objects
        final List<String> AP = new ArrayList<>();
        final List<Integer> AI = new ArrayList<>();
        if (appleProducts != null) {
            Collections.addAll(AP, appleProducts);
            Collections.addAll(AI, appleIcons);
            appleProducts = AP.toArray(new String[AP.size()]);
            appleIcons = AI.toArray(new Integer[AI.size()]);
        }

        // Microsoft Data Objects
        final List<String> MP = new ArrayList<>();
        final List<Integer> MI = new ArrayList<>();
        if (msProducts != null) {
            Collections.addAll(MP, msProducts);
            Collections.addAll(MI, msIcons);
            msProducts = MP.toArray(new String[MP.size()]);
            msIcons = MI.toArray(new Integer[MI.size()]);
        }

        // Samsung Data Objects
        final List<String> SaP = new ArrayList<>();
        final List<Integer> SaI = new ArrayList<>();
        if (samsungProducts != null) {
            Collections.addAll(SaP, samsungProducts);
            Collections.addAll(SaI, samsungIcons);
            samsungProducts = SaP.toArray(new String[SaP.size()]);
            samsungIcons = SaI.toArray(new Integer[SaI.size()]);
        }

        // Sony Data Objects
        final List<String> SoP = new ArrayList<>();
        final List<Integer> SoI = new ArrayList<>();
        if (sonyProducts != null) {
            Collections.addAll(SoP, sonyProducts);
            Collections.addAll(SoI, sonyIcons);
            sonyProducts = SoP.toArray(new String[SoP.size()]);
            sonyIcons = SoI.toArray(new Integer[SoI.size()]);
        }

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);

        if (Objects.equals(companyTitle, "Apple"))
            setListAdapter(new CustomListAdapter(getActivity(), appleProducts, appleIcons));
        if (Objects.equals(companyTitle, "Microsoft"))
            setListAdapter(new CustomListAdapter(getActivity(), msProducts, msIcons));
        if (Objects.equals(companyTitle, "Samsung"))
            setListAdapter(new CustomListAdapter(getActivity(), samsungProducts, samsungIcons));
        if (Objects.equals(companyTitle, "Sony"))
            setListAdapter(new CustomListAdapter(getActivity(), sonyProducts, sonyIcons));

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

                        if (Objects.equals(companyTitle, "Apple")) {
                            AP.remove(position);
                            AI.remove(position);
                            appleProducts = AP.toArray(new String[AP.size()]);
                            appleIcons = AI.toArray(new Integer[AI.size()]);
                            setListAdapter(new CustomListAdapter(getActivity(), appleProducts, appleIcons));
                        }
                        if (Objects.equals(companyTitle, "Microsoft")) {
                            MP.remove(position);
                            MI.remove(position);
                            msProducts = MP.toArray(new String[MP.size()]);
                            msIcons = MI.toArray(new Integer[MI.size()]);
                            setListAdapter(new CustomListAdapter(getActivity(), msProducts, msIcons));
                        }
                        if (Objects.equals(companyTitle, "Samsung")) {
                            SaP.remove(position);
                            SaI.remove(position);
                            samsungProducts = SaP.toArray(new String[SaP.size()]);
                            samsungIcons = SaI.toArray(new Integer[SaI.size()]);
                            setListAdapter(new CustomListAdapter(getActivity(), samsungProducts, samsungIcons));
                        }
                        if (Objects.equals(companyTitle, "Sony")) {
                            SoP.remove(position);
                            SoI.remove(position);
                            sonyProducts = SoP.toArray(new String[SoP.size()]);
                            sonyIcons = SoI.toArray(new Integer[SoI.size()]);
                            setListAdapter(new CustomListAdapter(getActivity(), sonyProducts, sonyIcons));
                        }

                    }
                });
                adb.show();

                Toast.makeText(getActivity(), "List item was long pressed. " + position, Toast.LENGTH_SHORT).show();

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
