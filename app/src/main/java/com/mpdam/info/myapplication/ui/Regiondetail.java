package com.mpdam.info.myapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mpdam.info.myapplication.R;
import com.mpdam.info.myapplication.Retrofit.api.RetrofitObjectAPI;
import com.mpdam.info.myapplication.model.RegionList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Regiondetail extends AppCompatActivity {
    ImageView img;
    TextView nameRegion;
    public String a="";
    String url = "http://anproip.co.nf/";
    public final String image_titles[] = {
            "Tunis","Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba", "Kairouan",
            "Kasserine", "Kébili", "Kef", "Mahdia", "Manouba", "Médenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Zaghouan",
    };
    public final Integer image_ids[] = {
            R.drawable.tun, R.drawable.ariana, R.drawable.img11,
            R.drawable.benarous, R.drawable.bizerte, R.drawable.gabes, R.drawable.gafsa1, R.drawable.img9, R.drawable.karioune, R.drawable.img13, R.drawable.gbeli,
            R.drawable.img12, R.drawable.mahdia, R.drawable.manouba, R.drawable.medenine, R.drawable.monastir, R.drawable.nabeul, R.drawable.sfax, R.drawable.image14, R.drawable.seliana, R.drawable.sousse, R.drawable.tatouine, R.drawable.touzeurr, R.drawable.zaghouan,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        nameRegion =(TextView)findViewById(R.id.regionname) ;
        img=(ImageView)findViewById(R.id.img1);
     Bundle extras = getIntent().getExtras();
        String value=extras.getString("a");
        //int value1=extras.getInt("b");
        int x=R.drawable.img11;
        for(int i=0;i<image_titles.length;i++){
            if(image_titles[i].equals(value)){
                x=i;
                break;
            }

        }
        getRetrofitObject();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
      getSupportActionBar().setTitle(value);

        img.setImageResource(image_ids[x]);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    void getRetrofitObject() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitObjectAPI service = retrofit.create(RetrofitObjectAPI.class);
//id =x
        final Call<RegionList> call = service.getStudentDetails(/*x*/);


        call.enqueue(new Callback<RegionList>() {
            @Override
            public void onResponse(Response<RegionList> response, Retrofit retrofit) {

                try {

               //     text_id.setText("ReggionId  :  " + response.body().getRegion().getId());
                    nameRegion.setText("RegionName  :  " + response.body().getRegion().getNom());
                 //   text_desc.setText("Regionsdesc  : " + response.body().getRegion().getDescription());
                 //   text_his.setText("region histo   : " +response.body().getRegion().getHistoire());
                  //  texturl.setText("region url   : " +response.body().getImg().get(0));
                    String[] result;
                    if (response.body().getImg().size()>0) {
                        for (int i = 0; i < response.body().getImg().size(); i++) {
                            Toast.makeText(getApplicationContext(),response.body().getImg().size()+"",Toast.LENGTH_LONG).show();
                            // result += "Array :[" + i + ']' + student.interests.get(i) + 'n';
                        }}
                //    a=response.body().getImg().get(0).toString();
                 /*   Glide.with(getApplicationContext())
                            .load((a))
                            .into(textViewImageUrl);*/
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
}
