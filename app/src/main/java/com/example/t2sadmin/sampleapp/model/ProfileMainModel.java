package com.example.t2sadmin.sampleapp.model;

import java.util.ArrayList;

public class ProfileMainModel {

    private String Title;

    private ArrayList<ProfileChildModel> ChildList;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<ProfileChildModel> getChildList() {
        return ChildList;
    }

    public void setChildList(ArrayList<ProfileChildModel> childList) {
        ChildList = childList;
    }
}
