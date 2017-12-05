package com.example.pinarmnkl.kullanici_giris;

import java.util.ArrayList;

/**
 * Created by g1409 on 5.12.2017.
 */

public class NavigationDrawerItem {
    String baslik;
    int resimID;

    public String getBaslik() {
        return baslik;
    }

    public int getResimID() {
        return resimID;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public void setResimID(int resimID) {
        this.resimID = resimID;
    }

    public static ArrayList<NavigationDrawerItem> getData(){

        ArrayList<NavigationDrawerItem> dataList=new ArrayList<NavigationDrawerItem>();
        int[] resimIDs=getImages();
        String[] basliklar=getBasliklar();

        for(int i=0; i<basliklar.length; i++)
        {
            NavigationDrawerItem navItem=new NavigationDrawerItem();
            navItem.setBaslik(basliklar[i]);
            navItem.setResimID(resimIDs[i]);

            dataList.add(navItem);
        }

        return dataList;


    }

    private static int[] getImages() {
        return new int[]{
                R.drawable.ic_kanepe, R.drawable.ic_cekyat,
                R.drawable.ic_masaaa, R.drawable.ic_dolap,
                R.drawable.ic_discard, R.drawable.ic_perdee};

    }

    private static String[] getBasliklar() {

        return new String[] {
                "Koluk", "Kanepe", "masa", "dolap", "perde", "halı"
        };
    }}