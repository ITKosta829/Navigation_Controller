package turntotech.org.navigationcontroller.fragments;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import turntotech.org.navigationcontroller.CustomListAdapter;
import turntotech.org.navigationcontroller.R;


public class CompanyFragment extends ListFragment {

    ProductFragment productFragment;
    String companyTitle;
    String[] companies;
    Integer[] icons;

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

        if (companies == null) {
            companies = new String[]{"Apple", "Microsoft", "Samsung", "Sony"};
            icons = new Integer[]{R.drawable.apple_logo, R.drawable.microsoft_logo, R.drawable.samsung_logo, R.drawable.sony_logo};
        }

        final List<String> CL = new ArrayList<>();
        final List<Integer> IL = new ArrayList<>();

        Collections.addAll(CL, companies);
        Collections.addAll(IL, icons);

        companies = CL.toArray(new String[CL.size()]);
        icons = IL.toArray(new Integer[IL.size()]);

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) view.findViewById(android.R.id.list);

        setListAdapter(new CustomListAdapter(getActivity(), companies, icons));

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                companyTitle = (String) ((TextView) view.findViewById(R.id.txtStatus)).getText();

                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + companyTitle + " from the list?");
                //final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CL.remove(position);
                        IL.remove(position);
                        companies = CL.toArray(new String[CL.size()]);
                        icons = IL.toArray(new Integer[IL.size()]);
                        setListAdapter(new CustomListAdapter(getActivity(), companies, icons));

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
