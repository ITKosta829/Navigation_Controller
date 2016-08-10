package turntotech.org.navigationcontroller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CompanyViewAdapter extends ArrayAdapter<String> {

	private final Context context;
//	private final String[] values;
//	private final Integer[] icons;
	private  List<Company> companyList;

	public CompanyViewAdapter(Context context, String[] values, Integer[] icons) {
		super(context, R.layout.row_layout, values);
		this.context = context;
//		this.values = values;
//		this.icons = icons;
		//this.companyList = companyList;

	}
	public CompanyViewAdapter(Context context, List companyList) {
		super(context, R.layout.row_layout, companyList);
		this.context = context;
		this.companyList = companyList;

	}

	public Drawable getImage(String name){

		Resources resources = context.getResources();
		final int resourceId = resources.getIdentifier(name, "drawable",
				context.getPackageName());
		return resources.getDrawable(resourceId);
	}


	@Override
	// Get a View that displays the data at the specified position in the data set.
	public View getView(int position, View convertView, ViewGroup parent) {

		View grid;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {

			grid = inflater.inflate(R.layout.company_gridview_layout, parent, false);

		}else {
			grid = convertView;
		}

		ImageView logo = (ImageView) grid.findViewById(R.id.logo);
		TextView company_name = (TextView) grid.findViewById(R.id.txtStatus);
		company_name.setTextSize(20);
		company_name.setTypeface(null, Typeface.BOLD);
		company_name.setText(this.companyList.get(position).getCompanyName());
		TextView stock_price = (TextView) grid.findViewById(R.id.stock_price);
		stock_price.setTextSize(20);
		stock_price.setTypeface(null, Typeface.BOLD);
		stock_price.setText("$" + this.companyList.get(position).getCompanyStockPrice());

		Drawable draw = getImage(this.companyList.get(position).getCompanyIcon());
		Bitmap bitmap = ((BitmapDrawable) draw).getBitmap();
		int h = bitmap.getHeight();
		int w = bitmap.getWidth();
		Drawable newDraw = new BitmapDrawable(context.getResources(),
				Bitmap.createScaledBitmap(bitmap, 60 * w / h, 60, true));

		logo.setImageDrawable(newDraw);

		return grid;
	}
}