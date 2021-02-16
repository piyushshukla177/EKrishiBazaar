package com.service.ekrishibazaar.adapter;
import com.service.ekrishibazaar.AdsListActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.model.CategoryListModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

public class CategoryListAdapter extends BaseAdapter {

    ArrayList<CategoryListModel> product_list = new ArrayList<>();
    Context context;
    private static LayoutInflater inflater = null;

    public CategoryListAdapter(Context mainActivity, ArrayList productList) {
        // TODO Auto-generated constructor stub
        product_list = productList;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return product_list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        public TextView product_name_tv;
        ImageView product_image;
        RelativeLayout relative_layout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.product_layout, null);
        holder.relative_layout = rowView.findViewById(R.id.relative_layout);
        holder.product_name_tv = rowView.findViewById(R.id.product_name_tv);
        holder.product_image = rowView.findViewById(R.id.product_image);
        final CategoryListModel m = product_list.get(position);
        holder.product_name_tv.setText(m.getProduct_name());
        Picasso.get().load(m.getProduct_image()).fit().into(holder.product_image);
        holder.relative_layout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, AdsListActivity.class);
                        intent.putExtra("category", m.getProduct_name());
                        context.startActivity(intent);
                    }
                }
        );
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rowView;
    }
    public void filterList(ArrayList<CategoryListModel> filteredList) {
        product_list = filteredList;
        notifyDataSetChanged();
    }
}
