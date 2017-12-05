package com.example.pinarmnkl.kullanici_giris;

import java.util.ArrayList;

/**
 * Created by g1409 on 5.12.2017.
 */

public class Esya {
    private String title;
    private int imageId;

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public static ArrayList<Esya> getData(){

        ArrayList<Esya> data=new ArrayList<Esya>();
        int[] images={
                R.drawable.ic_koltukresmi2,
                R.drawable.cekyatt,
                R.drawable.ic_perdee,
                R.drawable.dolapp,



        };

        String[] Categories = {"KOLTUK DİZAYNINIZI YAPINIZ","CEKYA DİZAYNINIZI YAPINIZ","DOLAP DIZAYNINIZI YAPINIZ","DOLAP DİZAYNINIZI YAPINIZ"};

        for(int i=0; i<images.length; i++){

            Esya gecici=new Esya();
            gecici.setImageId(images[i]);
            gecici.setTitle(Categories[i]);
            data.add(gecici);

        }



        return data;
    }
}
