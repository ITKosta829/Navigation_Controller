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

        Bundle bundle = this.getArguments();

        companyPosition = bundle.getInt("CompanyIndex");
        productPosition = bundle.getInt("ProductIndex");
        companyTitle = bundle.getString("CompanyTitle");
        productTitle = bundle.getString("ProductTitle");
        title.setText(productTitle + " Web Page");

        String url = null;

        // Apple Products
        // products = new String[] { "iPhone", "iPad", "iPod", "iMac", "Mac Book", "Watch" };
        if (Objects.equals(companyTitle, "Apple")) {
            if (Objects.equals(productTitle, "iPhone"))
                url = "http://www.apple.com/iphone/"; // iPhone URL
            if (Objects.equals(productTitle, "iPad"))
                url = "http://www.apple.com/ipad/"; // iPad URL
            if (Objects.equals(productTitle, "iPod"))
                url = "http://www.apple.com/ipod/"; // iPod URL
            if (Objects.equals(productTitle, "iMac"))
                url = "http://www.apple.com/imac/"; // iMac URL
            if (Objects.equals(productTitle, "Mac Book"))
                url = "http://www.apple.com/macbook-pro/"; // Macbook URL
            if (Objects.equals(productTitle, "Watch"))
                url = "http://www.apple.com/watch/"; // Watch URL
        }

        // Microsoft Products
        // products = new String[] { "Surface", "X-Box", "Windows", "Office", "Smart Phone" };
        if (Objects.equals(companyTitle, "Microsoft")) {
            if (Objects.equals(productTitle, "Surface"))
                url = "https://www.microsoft.com/surface/en-us"; // Surface URL
            if (Objects.equals(productTitle, "X-Box"))
                url = "http://www.xbox.com/en-US/"; // X-Box URL
            if (Objects.equals(productTitle, "Windows"))
                url = "https://www.microsoft.com/en-us/windows"; // Windows URL
            if (Objects.equals(productTitle, "Office"))
                url = "https://products.office.com/en-US/"; // Office URL
            if (Objects.equals(productTitle, "Smart Phone"))
                url = "https://www.microsoft.com/en-us/windows/phones"; // Phone URL
        }

        // Samsung Products
        // products = new String[] { "Galaxy Note", "Galaxy Tab", "Galaxy Gear", "TV", "Home Appliances" };
        if (Objects.equals(companyTitle, "Samsung")) {
            if (Objects.equals(productTitle, "Galaxy Note"))
                url = "http://www.samsung.com/us/explore/galaxy-note-5-features-and-specs/?cid=ppc-"; // Note URL
            if (Objects.equals(productTitle, "Galaxy Tab"))
                url = "http://www.samsung.com/us/mobile/galaxy-tab/"; // Tab URL
            if (Objects.equals(productTitle, "Galaxy Gear"))
                url = "http://www.samsung.com/us/mobile/wearable-tech/all-products?filter=smartwatches"; // Gear URL
            if (Objects.equals(productTitle, "TV"))
                url = "http://www.samsung.com/us/televisions/"; // TV URL
            if (Objects.equals(productTitle, "Home Appliances"))
                url = "http://www.samsung.com/us/homeappliances/"; // Appliances URL
        }

        // Sony Products
        // products = new String[] { "Playstation", "Camera", "Audio", "TV", "Mobile Devices" };
        if (Objects.equals(companyTitle, "Sony")) {
            if (Objects.equals(productTitle, "Playstation"))
                url = "https://www.playstation.com/en-us/"; // Playstation URL
            if (Objects.equals(productTitle, "Camera"))
                url = "http://www.sony.com/electronics/cameras"; // Camera URL
            if (Objects.equals(productTitle, "Audio"))
                url = "http://www.sony.com/electronics/audio"; // Audio URL
            if (Objects.equals(productTitle, "TV"))
                url = "http://www.sony.com/electronics/televisions-home-theater"; // TV URL
            if (Objects.equals(productTitle, "Mobile Devices"))
                url = "http://www.sony.com/electronics/mobile-tablets"; // Mobile URL
        }

        View view = inflater.inflate(R.layout.web_view, container, false);
        WebView webView = (WebView) view.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        return view;
    }
}
