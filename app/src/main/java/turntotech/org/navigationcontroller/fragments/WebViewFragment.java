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

import turntotech.org.navigationcontroller.R;

/**
 * Created by DeanC on 5/23/2016.
 */
public class WebViewFragment extends Fragment {

    int companyPosition;
    int productPosition;
    String companyTitle;

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

        Bundle bundle = this.getArguments();

        companyPosition = bundle.getInt("CompanyIndex");
        productPosition = bundle.getInt("ProductIndex");
        companyTitle = bundle.getString("CompanyTitle");
        title.setText(bundle.getString("ProductTitle") + " Web Page");

        String url = null;

        // Apple Products
        if (companyPosition == 0) {
            if (productPosition == 0) url = "http://www.apple.com/iphone/"; // iPhone URL
            if (productPosition == 1) url = "http://www.apple.com/ipad/"; // iPad URL
            if (productPosition == 2) url = "http://www.apple.com/ipod/"; // iPod URL
            if (productPosition == 3) url = "http://www.apple.com/imac/"; // iMac URL
            if (productPosition == 4) url = "http://www.apple.com/macbook-pro/"; // Macbook URL
            if (productPosition == 5) url = "http://www.apple.com/watch/"; // Watch URL
        }

        // Microsoft Products
        if (companyPosition == 1) {
            if (productPosition == 0) url = "https://www.microsoft.com/surface/en-us"; // Surface URL
            if (productPosition == 1) url = "http://www.xbox.com/en-US/"; // X-Box URL
            if (productPosition == 2) url = "https://www.microsoft.com/en-us/windows"; // Windows URL
            if (productPosition == 3) url = "https://products.office.com/en-US/"; // Office URL
            if (productPosition == 4) url = "https://www.microsoft.com/en-us/windows/phones"; // Phone URL
        }

        // Samsung Products
        if (companyPosition == 2) {
            if (productPosition == 0) url = "http://www.samsung.com/us/explore/galaxy-note-5-features-and-specs/?cid=ppc-"; // Note URL
            if (productPosition == 1) url = "http://www.samsung.com/us/mobile/galaxy-tab/"; // Tab URL
            if (productPosition == 2) url = "http://www.samsung.com/us/mobile/wearable-tech/all-products?filter=smartwatches"; // Gear URL
            if (productPosition == 3) url = "http://www.samsung.com/us/televisions/"; // TV URL
            if (productPosition == 4) url = "http://www.samsung.com/us/homeappliances/"; // Appliances URL
        }

        // Sony Products
        if (companyPosition == 3) {
            if (productPosition == 0) url = "https://www.playstation.com/en-us/"; // Playstation URL
            if (productPosition == 1) url = "http://www.sony.com/electronics/cameras"; // Camera URL
            if (productPosition == 2) url = "http://www.sony.com/electronics/audio"; // Audio URL
            if (productPosition == 3) url = "http://www.sony.com/electronics/televisions-home-theater"; // TV URL
            if (productPosition == 4) url = "http://www.sony.com/electronics/mobile-tablets"; // Mobile URL
        }

        View view = inflater.inflate(R.layout.web_view, container, false);
        WebView webView = (WebView) view.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        return view;
    }
}
