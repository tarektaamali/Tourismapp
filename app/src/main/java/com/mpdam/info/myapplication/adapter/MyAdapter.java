package com.mpdam.info.myapplication.adapter;

/**
 * Created by Info on 12/3/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mpdam.info.myapplication.R;
import com.mpdam.info.myapplication.ui.Main2Activity;
import com.mpdam.info.myapplication.model.CreateList;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable  {
    private ArrayList<CreateList> galleryList;
    private Context context;
    public static String regionname ;
    private ArrayList<CreateList> mFilteredList;
    int r;

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
//        YoYo.with(Techniques.FadeInRight).duration(100).playOn(viewHolder.img);
        viewHolder.title.setText(galleryList.get(i).getImage_title());
       // viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));
        //            setImageBitmap( R.drawable.icon
        /*
        options.inJustDecodeBounds = true;
options.inSampleSize = 3;


         */
       // BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = true;
        //options.inSampleSize = 2;
     /* Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),galleryList.get(i).getImage_ID());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        BitmapDrawable ob = new BitmapDrawable(context.getResources(), bitmap);*/
       // Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),galleryList.get(i).getImage_ID());
        viewHolder.img.setBackgroundResource(galleryList.get(i).getImage_ID());


        //Picasso.with(context).load(galleryList.get(i).getImage_ID()).resize(240, 120).into(viewHolder.img);
        viewHolder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

        //        Toast.makeText(context,viewHolder.title.getText(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), Main2Activity.class);
                regionname=viewHolder.title.getText().toString() ;
                intent.putExtra("a",viewHolder.title.getText());
            //    intent.putExtra("b",viewHolder.img.getId());
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

                        if (androidVersion.getImage_title().toLowerCase().contains(charString)||androidVersion.getImage_title().toUpperCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
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
        protected void publishResults(CharSequence charSequence,FilterResults
        filterResults) {
            mFilteredList = (ArrayList<CreateList>) filterResults.values;
            notifyDataSetChanged();
        }
    };
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private LinearLayout img;
        private LinearLayout lin;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (LinearLayout) view.findViewById(R.id.img);
            lin=(LinearLayout) view.findViewById(R.id.lin) ;
        }
    }

}