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
            session.setAttribute("member", newMember);
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

    @RequestMapping(path = "/createPost.json", method = RequestMethod.POST)
    public PostContainer createPost(HttpSession session, @RequestBody Post post) {
        Member member = (Member) session.getAttribute("member");
        PostContainer postContainer = new PostContainer();
        post = new Post(post.date, post.title, post.body, post.author);

        if (post == null) {
            postContainer.errorMessage = "Post was empty and therefore cannot be saved";

        } else {
            post = new Post(post.date, post.title, post.body, post.author);
            posts.save(post);
            postContainer.postList = getAllPostsByMember(member);
            System.out.println("post id = " + post.id);
        }
        return postContainer;
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

    @RequestMapping(path = "/postsListByMember.json", method = RequestMethod.GET)
    public List<Post> getAllPostsByMember(Member member) {
        Iterable<Post> allPosts = posts.findByAuthor(member);
        List<Post> postList = new ArrayList<>();
        for (Post currentPost : allPosts) {
            postList.add(currentPost);
        }
        System.out.println("after iterable");
        return postList;
    }

    @RequestMapping(path = "/postsList.json", method = RequestMethod.GET)
    public List<Post> getAllPosts() {
        Iterable<Post> allPosts = posts.findAll();
        List<Post> postList = new ArrayList<>();
        for (Post currentPost : allPosts) {
            postList.add(currentPost);
        }
        return postList;
    }

}
