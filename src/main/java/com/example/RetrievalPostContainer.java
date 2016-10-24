package com.example;

import java.util.List;

/**
 * Created by DTG2 on 10/11/16.
 */
public class RetrievalPostContainer {

    Post retPost;
    Organization thisOrganization;


    public RetrievalPostContainer() {
    }


    public Organization getThisOrganization() {
        return thisOrganization;
    }

    public void setThisOrganization(Organization thisOrganization) {
        this.thisOrganization = thisOrganization;
    }
}
