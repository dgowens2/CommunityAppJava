package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bearden-tellez on 10/10/16.
 */

@RestController
public class CommunityJsonController {

    @Autowired
    MemberRepository members;

    @Autowired
    PostRepository posts;


    @RequestMapping(path = "/login.json", method = RequestMethod.POST)
    public MemberResponseContainer login(HttpSession session, @RequestBody Member member) throws Exception {
        MemberResponseContainer myResponse = new MemberResponseContainer();
        Member newMember = members.findFirstByEmail(member.email);
        if (newMember == null) {
            myResponse.errorMessage = "User does not exist or was input incorrectly";
        } else if (!member.password.equals(newMember.getPassword())) {
            myResponse.errorMessage = "Incorrect password";
        } else if(newMember != null && newMember.password.equals(newMember.getPassword())) {
            System.out.println(newMember.email + " is logging in");
            session.setAttribute("user", newMember);
            myResponse.responseMember = newMember;
        }
        return myResponse;
    }

    @RequestMapping(path = "/register.json", method = RequestMethod.POST)
    public MemberResponseContainer newMemberInfo(HttpSession session, @RequestBody Member member) throws Exception {
        MemberResponseContainer myResponse = new MemberResponseContainer();
        Member newMember = members.findFirstByEmail(member.email);
        System.out.println(member.email + " is about to be created");
        if (newMember == null) {
            member = new Member(member.email, member.firstName, member.lastName, member.password, member.streetAddress);
            members.save(member);
            myResponse.responseMember = member;
            session.setAttribute("member", member);
        } else {
            myResponse.errorMessage = "User already exists";
        }
        return myResponse;
    }

    @RequestMapping(path = "/viewMembers.json", method = RequestMethod.GET)
    public List<Member> getMembers() {

        List<Member> memberList = new ArrayList<>();
        Iterable <Member> allMembers = members.findAll();
        for (Member member : allMembers) {
            memberList.add(member);
        }
        return memberList;
    }

    @RequestMapping(path = "/createPost.json", method = RequestMethod.POST)
    public PostContainer createPost(@RequestBody Post post, Member member) {
//        posts.save(post);
//
//        System.out.println(" in create post method");
//
//        List<Post> postList = new ArrayList<>();
//        Iterable<Post> allPosts = posts.findAll();
//        for (Post currentPost : allPosts) {
//            postList.add(currentPost);
//        }
//
//        System.out.println("after iterable");
//        return postList;

//        System.out.println("just above the method");
//
        PostContainer postList = new PostContainer();
//        Post newPost = posts.findByMember(member);
//        System.out.println("Post #" + post.id + " is about to be created");
//        if (newPost == null) {
//            post = new Post(post.date, post.title, post.body, post.member);
//            posts.save(post);
//            postList.responsePost = post;
////            session.setAttribute("member", member);
//        } else {
//            postList.errorMessage = "Post cannot be created";
//        }
        return postList;
    }

    @RequestMapping(path = "/memberList.json", method = RequestMethod.GET)
    public List<Member> getMemberList() {
        List<Member> memberList = new ArrayList<>();
        Iterable <Member> allMembers = members.findAll();
        for (Member member : allMembers) {
            memberList.add(member);
        }
        return memberList;
    }


}
