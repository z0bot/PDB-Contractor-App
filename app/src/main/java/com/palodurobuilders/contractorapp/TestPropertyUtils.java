package com.palodurobuilders.contractorapp;

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

        return dummyList;
    }
}
