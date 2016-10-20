package com.example;

import java.util.ArrayList;

/**
 * Created by bearden-tellez on 10/10/16.
 */
public class MemberOrganizationContainer {

    ArrayList<Member> responseMemberList;
    String errorMessage;

    public ArrayList<Member> getResponseMemberList() {

        return responseMemberList;
    }

    public void setResponseMemberList(ArrayList<Member> responseMemberList) {
        this.responseMemberList = responseMemberList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public MemberOrganizationContainer() {

    }
}

