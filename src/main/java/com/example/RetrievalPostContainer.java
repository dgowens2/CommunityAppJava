package com.example;

import java.util.List;

/**
 * Created by DTG2 on 10/11/16.
 */
public class RetrievalPostContainer {

    Post retPost;
    Organization thisOrganization;
    Member member;

    public Post getRetPost() {
        return retPost;
    }

    public void setRetPost(Post retPost) {
        this.retPost = retPost;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public RetrievalPostContainer() {
    }


    public Organization getThisOrganization() {
        return thisOrganization;
    }

    public void setThisOrganization(Organization thisOrganization) {
        this.thisOrganization = thisOrganization;
    }
}
