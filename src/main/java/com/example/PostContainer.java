package com.example;

import java.util.List;

/**
 * Created by DTG2 on 10/11/16.
 */
public class PostContainer  {

    Post responsePost;
    List<Post> postList;
    String errorMessage;

    public PostContainer() {
    }

    public Post getResponsePost() {
        return responsePost;
    }

    public void setResponsePost(Post responsePost) {
        this.responsePost = responsePost;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

//    public List<Post> getPostList() {
//        return postList;
//    }
//
//    public void setPostList(List<Post> postList) {
//        this.postList = postList;
//    }
}
