package turntotech.org.navigationcontroller.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import turntotech.org.navigationcontroller.DataHandler;
import turntotech.org.navigationcontroller.R;

/**
 * Created by DeanC on 6/14/2016.
 */
public class AddProductForm extends android.app.DialogFragment {

    private EditText et;
    String new_product_name, companyTitle;
    int companyPosition;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.add_form, null);
        et = (EditText) v.findViewById(R.id.add_form_edit_txt);

        companyPosition = DataHandler.getInstance().currentCompanyPosition;
        companyTitle = DataHandler.getInstance().currentCompanyTitle;

        AlertDialog.Builder b;
        b = new AlertDialog.Builder(getActivity());
        b.setView(v)

                .setTitle(getActivity().getString(R.string.enter_product_name))
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new_product_name = et.getText().toString();
                                DataHandler.getInstance().addProduct(companyPosition, new_product_name, R.drawable.unknown_logo);
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
