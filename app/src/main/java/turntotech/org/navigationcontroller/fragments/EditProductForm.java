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
import turntotech.org.navigationcontroller.DatabaseAccess;
import turntotech.org.navigationcontroller.R;

/**
 * Created by DeanC on 6/14/2016.
 */
public class EditProductForm extends android.app.DialogFragment {

    private EditText et;
    String new_product_name;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.add_product_form, null);
        et = (EditText) v.findViewById(R.id.add_form_edit_txt);

        AlertDialog.Builder b;
        b = new AlertDialog.Builder(getActivity());
        b.setView(v)

                .setTitle("Change Product \"" + DataHandler.getInstance().currentProductTitle + "\" to:")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new_product_name = et.getText().toString();
                                DatabaseAccess.getInstance().editCompanyProduct(new_product_name);
//                                DataHandler.getInstance().editCompanyProduct(DataHandler.getInstance().currentCompanyPosition,
//                                        DataHandler.getInstance().currentProductPosition, new_product_name);
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
