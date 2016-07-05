package turntotech.org.navigationcontroller.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import turntotech.org.navigationcontroller.DataHandler;
import turntotech.org.navigationcontroller.R;

/**
 * Created by DeanC on 6/14/2016.
 */
public class AddCompanyForm extends android.app.DialogFragment {

    private EditText C, E, T;
    String company_name, exchange, ticker_symbol;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.add_company_form, null);
        C = (EditText) v.findViewById(R.id.add_form_enter_company);
        E = (EditText) v.findViewById(R.id.add_form_enter_exchange);
        E.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        T = (EditText) v.findViewById(R.id.add_form_enter_stock_symbol);
        T.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        AlertDialog.Builder b;
        b = new AlertDialog.Builder(getActivity());
        b.setView(v)

                .setTitle(getActivity().getString(R.string.new_company_form))
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                company_name = C.getText().toString();
                                exchange = E.getText().toString();
                                exchange = exchange.toUpperCase();
                                ticker_symbol = T.getText().toString();
                                ticker_symbol = ticker_symbol.toUpperCase();

                                DataHandler.getInstance().addCompany(company_name, "unknown_logo", exchange + ":" + ticker_symbol);
                                DataHandler.getInstance().financeQuery();
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );


        return b.create();

    }
}
