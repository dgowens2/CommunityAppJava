package com.example;

/**
 * Created by bearden-tellez on 10/10/16.
 */
public class MemberResponseContainer {

    Member responseMember;
    String errorMessage;

    public Member getResponseMember() {
        return responseMember;
    }

    public void setResponseMember(Member responseMember) {
        this.responseMember = responseMember;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public MemberResponseContainer() {

    }
}

