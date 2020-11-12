package com.palodurobuilders.contractorapp.utilities;

import com.palodurobuilders.contractorapp.models.DummyHouse;
import com.palodurobuilders.contractorapp.R;

import java.util.ArrayList;
import java.util.List;

public class TestPropertyUtils
{
    public static List<DummyHouse> CreateImageList()
    {
        List<DummyHouse> dummyList = new ArrayList<>();

        DummyHouse dh1 = new DummyHouse();
        dh1.setHomeAddress("2020 Biden St.");
        dh1.setHomeImage(R.drawable.house);
        dummyList.add(dh1);

        DummyHouse dh2 = new DummyHouse();
        dh2.setHomeAddress("2020 ByeTrump St.");
        dh2.setHomeImage(R.drawable.house);
        dummyList.add(dh2);

        DummyHouse dh3 = new DummyHouse();
        dh3.setHomeAddress("2024 Aboubakar Ln.");
        dh3.setHomeImage(R.drawable.house);
        dummyList.add(dh3);

        DummyHouse dh4 = new DummyHouse();
        dh4.setHomeAddress("1234 Stalin Rd.");
        dh4.setHomeImage(R.drawable.house_placeholder);
        dummyList.add(dh4);

        DummyHouse dh5 = new DummyHouse();
        dh5.setHomeAddress("4321 Adams Dr.");
        dh5.setHomeImage(R.drawable.house_placeholder_two);
        dummyList.add(dh5);

        DummyHouse dh6 = new DummyHouse();
        dh6.setHomeAddress("6969 Manquero Tr.");
        dh6.setHomeImage(R.drawable.house_placeholder_three);
        dummyList.add(dh6);

        DummyHouse dh7 = new DummyHouse();
        dh7.setHomeAddress("5678 Duc Huy Do.");
        dh7.setHomeImage(R.drawable.house_placeholder_four);
        dummyList.add(dh7);

        DummyHouse dh8 = new DummyHouse();
        dh8.setHomeAddress("3456 Dalton Cave");
        dh8.setHomeImage(R.drawable.house_placeholder_five);
        dummyList.add(dh8);

        DummyHouse dh9 = new DummyHouse();
        dh9.setHomeAddress("4567 Love Ln.");
        dh9.setHomeImage(R.drawable.house_placeholder_six);
        dummyList.add(dh9);

        return dummyList;
    }
}