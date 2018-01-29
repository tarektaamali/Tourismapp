package com.mpdam.info.myapplication.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.mpdam.info.myapplication.internet.InternetConnection;
import com.mpdam.info.myapplication.model.RegionList;
import com.mpdam.info.myapplication.model.geocode.Example;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.mpdam.info.myapplication.adapter.MyAdapter.regionname;
import static com.mpdam.info.myapplication.ui.MainActivity.image_ids;
import static com.mpdam.info.myapplication.ui.MainActivity.image_titles;
import static com.mpdam.info.myapplication.ui.Utils.IMGS;


public class Main2Activity extends AppCompatActivity implements OnMapReadyCallback {
    TextView tx ,contentdesc,histodesc,specialitedesc ;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    ImageView img;
    public static double res,res1;
    String Currentregion ="";
    int y ;static  int result1;
    private GoogleMap mMap;
    String url = "http://192.168.43.167:8000/";
    Button viewmap,NavigateMap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tx=(TextView)findViewById(R.id.descId);
        viewmap=(Button) findViewById(R.id.viewmap);
        NavigateMap=(Button) findViewById(R.id.navigatemap);
        img=(ImageView)findViewById(R.id.profile_id);
        contentdesc=(TextView)findViewById(R.id.desContent);
        histodesc=(TextView)findViewById(R.id.histoContent);
        specialitedesc=(TextView)findViewById(R.id.specialiteContent);
        Bundle extras = getIntent().getExtras();
       // String value=extras.getString("a");
        String value;
        value=regionname;
        //int value1=extras.getInt("b");
        /********************************/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(value);
        /*************************************************/
        int x=R.drawable.img11;
        if (!InternetConnection.checkConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.string_internet_connection_not_available),Toast.LENGTH_LONG).show();

        }
        for(int i=0;i<image_titles.length;i++){

            if(image_titles[i].equals(value)){
            y=i;
                x=i;break;

            }
        }

        //Toast.makeText(getApplicationContext(),x+"",Toast.LENGTH_LONG).show();
       y++;
        img.setImageResource(image_ids[x]);
        Currentregion=value;
     //   mapfn();
        getgeocodeapi();
        getRetrofitObject();
        //   dynamicToolbarColor();
       // toolbarTextAppernce();
     //////////////////////////////////////////////////

        viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");36.733,9.183
                Intent i=new Intent(getApplicationContext() ,MapsActivity.class);
                i.putExtra("a",Currentregion);
                startActivity(i);

            }
        });
        NavigateMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=&daddr="+res+","+res1);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    public  void mapfn() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    void getRetrofitObject() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitObjectAPI service = retrofit.create(RetrofitObjectAPI.class);
//id =x

        final Call<RegionList> call = service.getRegionDetails(y);


        call.enqueue(new Callback<RegionList>() {
            @Override
            public void onResponse(Response<RegionList> response, Retrofit retrofit) {

                try {
                    Log.v("string", response.body().toString());
                    result1=response.body().getImg().size();
                 //   Toast.makeText(getApplicationContext(),response.body().getImg().size()+"",Toast.LENGTH_LONG).show();
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
    void getgeocodeapi(){
          String reg=Currentregion+",tn";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitObjectAPI service1 = retrofit.create(RetrofitObjectAPI.class);
//id =x
        final Call<Example> call = service1.getCityResults(reg,"AIzaSyDaz2-65AW95bN5Zcwv-nM4gNL1hha8En0" );


        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {

                try {


                     res=response.body().getResults().get(0).getGeometry().getLocation().getLat();
                    res1=response.body().getResults().get(0).getGeometry().getLocation().getLng();

                   mapfn();

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
 /* void vider(){
        IMGS = new String[IMGS.length];
        IMGS = new String[0];
        contentdesc.setText("");
        histodesc.setText("");
        specialitedesc.setText("");

    }*/
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
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);               break;
            case R.id.about:
                LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.about, (ViewGroup)findViewById(R.id.root));
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setView(layout);
                adb.show();

                                   break;}
            return super.onOptionsItemSelected(item);

        }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng( res,res1);
        mMap.addMarker(new MarkerOptions().position(sydney).title(Currentregion));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
    }

}
