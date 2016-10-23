package com.example;

import java.util.ArrayList;

/**
 * Created by DTG2 on 10/11/16.
 */
public class AnotherOrganizationContainer {

    ArrayList<Organization> responseOrganization = new ArrayList<>();

    String errorMessage;

    public ArrayList<Organization> getResponseOrganization() {
        return responseOrganization;
    }

    public void setResponseOrganization(ArrayList<Organization> responseOrganization) {
        this.responseOrganization = responseOrganization;
    }

    public AnotherOrganizationContainer() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
