package com.mpdam.info.myapplication.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mpdam.info.myapplication.R;
import com.mpdam.info.myapplication.Retrofit.api.RetrofitObjectAPI;
import com.mpdam.info.myapplication.model.RegionList;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.mpdam.info.myapplication.ui.MainActivity.image_ids;
import static com.mpdam.info.myapplication.ui.MainActivity.image_titles;
import static com.mpdam.info.myapplication.ui.Utils.IMGS;


public class Main2Activity extends AppCompatActivity implements OnMapReadyCallback {
    TextView tx ,contentdesc,histodesc,specialitedesc ;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    ImageView img;
    String Currentregion ="";
   // public   static String IMGS[];
   private GoogleMap mMap;
    String url = "http://anproip.co.nf/";
    TextView viewmap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tx=(TextView)findViewById(R.id.descId);
        viewmap=(TextView)findViewById(R.id.viewmap);
        img=(ImageView)findViewById(R.id.profile_id);
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

        contentdesc=(TextView)findViewById(R.id.desContent);
        histodesc=(TextView)findViewById(R.id.histoContent);
        specialitedesc=(TextView)findViewById(R.id.specialiteContent);

        tx.setText("Description");
      //  contentdesc.setText("beja est un Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(value);
        img.setImageResource(image_ids[x]);
        getRetrofitObject();
        //   dynamicToolbarColor();
       // toolbarTextAppernce();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");36.733,9.183
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=&daddr=36.733,9.183");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
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
                    if (response.body().getImg().size()>0) {
                        for (int i =0; i < response.body().getImg().size(); i++) {
                          //  Toast.makeText(getApplicationContext(),response.body().getImg().size()+"",Toast.LENGTH_LONG).show();
                            IMGS[i]=response.body().getImg().get(i).toString();


                        }}

                    //     text_id.setText("ReggionId  :  " + response.body().getRegion().getId());
                    //nameRegion.setText("RegionName  :  " + response.body().getRegion().getNom());
                    contentdesc.setText(response.body().getRegion().getDescription());
                    Currentregion=response.body().getRegion().getNom();
                   histodesc.setText(response.body().getRegion().getHistoire());
            specialitedesc.setText(response.body().getRegion().getSpecialite());
                    String[] result;

                    //    a=response.body().getImg().get(0).toString();
                 /*   Glide.with(getApplicationContext())
                            .load((a))
                            .into(textViewImageUrl);*/
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.content95, RecyclerViewFragment.newInstance())
                        .commit();

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /*private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng( 36.733,9.183);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
    }
}
