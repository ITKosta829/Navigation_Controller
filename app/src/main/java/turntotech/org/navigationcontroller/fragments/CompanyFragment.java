package turntotech.org.navigationcontroller.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import turntotech.org.navigationcontroller.CustomListAdapter;
import turntotech.org.navigationcontroller.R;


public class CompanyFragment extends ListFragment {

    ProductFragment productFragment;

    public CompanyFragment() {
        productFragment = new ProductFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mCustomView = inflater.inflate(R.layout.custom_actionbar, null);
        TextView title = (TextView)mCustomView.findViewById(R.id.title_text);
        mCustomView.findViewById(R.id.back_text).setVisibility(View.INVISIBLE);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
        title.setText(R.string.watch_list);

        String[] companies = new String[] { "Apple", "Microsoft", "Samsung", "Sony" };

        int[] icons = { R.drawable.apple_logo, R.drawable.microsoft_logo, R.drawable.samsung_logo, R.drawable.sony_logo };


        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, companies);
        setListAdapter(new CustomListAdapter(getActivity(), companies, icons));

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        //TextView title = (TextView)v.findViewById(android.R.id.text1);
        String s = (String) ((TextView)v.findViewById(R.id.txtStatus)).getText();

        Bundle bundle = new Bundle();
        bundle.putInt("CompanyIndex", position);
        //bundle.putString("CompanyTitle", title.getText().toString());
        bundle.putString("CompanyTitle", s);

        productFragment.setArguments(bundle);


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, productFragment);
        transaction.commit();



    }


}
