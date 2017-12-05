package com.mpdam.info.myapplication;

/**
 * Created by Info on 12/3/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable  {
    private ArrayList<CreateList> galleryList;
    private Context context;
    private ArrayList<CreateList> mFilteredList;

    public MyAdapter(Context context, ArrayList<CreateList> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
        mFilteredList = galleryList;

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder viewHolder, int i) {

        viewHolder.title.setText(galleryList.get(i).getImage_title());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));


        //Picasso.with(context).load(galleryList.get(i).getImage_ID()).resize(240, 120).into(viewHolder.img);
        viewHolder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,viewHolder.title.getText(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), Regiondetail.class);
                v.getContext().startActivity(intent);









            }
        });
    }
    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

               if(!charString.isEmpty()) {

                    ArrayList<CreateList> filteredList = new ArrayList<>();

                    for (CreateList androidVersion : galleryList) {
                        do{
                        if (androidVersion.getImage_title().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }}while (charString.isEmpty());
                    }

                    galleryList = filteredList;
                }
               else {

                   galleryList = mFilteredList;
               }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }
        @Override
        protected void publishResults(CharSequence charSequence, Filter.FilterResults
        filterResults) {
            mFilteredList = (ArrayList<CreateList>) filterResults.values;
            notifyDataSetChanged();
        }
    };
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

}