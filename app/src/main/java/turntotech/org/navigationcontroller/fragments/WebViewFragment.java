package turntotech.org.navigationcontroller.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.Objects;

import turntotech.org.navigationcontroller.DataHandler;
import turntotech.org.navigationcontroller.R;

/**
 * Created by DeanC on 5/23/2016.
 */
public class WebViewFragment extends Fragment {

    int companyPosition;
    int productPosition;
    String companyTitle;
    String productTitle;

    public WebViewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mCustomView = inflater.inflate(R.layout.custom_actionbar, null);
        TextView title = (TextView) mCustomView.findViewById(R.id.title_text);
        mCustomView.findViewById(R.id.back_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);

        companyPosition = DataHandler.getInstance().currentCompanyPosition;
        productPosition = DataHandler.getInstance().currentProductPosition;
        companyTitle = DataHandler.getInstance().currentCompanyTitle;
        productTitle = DataHandler.getInstance().currentProductTitle;

        title.setText(productTitle + " Web Page");

        View view = inflater.inflate(R.layout.web_view, container, false);
        WebView webView = (WebView) view.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(DataHandler.getInstance().getCompanyProductURL(companyPosition, productPosition));

        return view;
    }
}
