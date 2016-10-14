package com.example;

import java.util.List;

/**
 * Created by DTG2 on 10/11/16.
 */
public class OrganizationMemberContainer {
    List<OrganizationMember> organizationMemberList;
    String errorMessage;



    public OrganizationMemberContainer() {
    }

    public List<OrganizationMember> getOrganizationMemberList() {
        return organizationMemberList;
    }

    public void setOrganizationMemberList(List<OrganizationMember> organizationMemberList) {
        this.organizationMemberList = organizationMemberList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
