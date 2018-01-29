package com.mpdam.info.myapplication.ui;

import java.util.ArrayList;

import static com.mpdam.info.myapplication.ui.Main2Activity.result1;


public class Utils {


//  public static String IMGS[10] ;
    public static String[] IMGS = new String[15];
    public static ArrayList<ImageModel> getData() {
        ArrayList<ImageModel> arrayList = new ArrayList<>();
        for (int i = 0; i < result1; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setName("Image " + i);
            imageModel.setUrl(IMGS[i]);
            arrayList.add(imageModel);
        }
        return arrayList;
    }
}
