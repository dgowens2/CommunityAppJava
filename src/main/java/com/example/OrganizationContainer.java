package com.example;

import com.sun.tools.corba.se.idl.constExpr.Or;

import java.util.List;

/**
 * Created by DTG2 on 10/11/16.
 */
public class OrganizationContainer {

    Organization responseOrganization;

    String errorMessage;

    public Organization getResponseOrganization() {
        return responseOrganization;
    }

    public void setResponseOrganization(Organization responseOrganization) {
        this.responseOrganization = responseOrganization;
    }

    public OrganizationContainer() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
