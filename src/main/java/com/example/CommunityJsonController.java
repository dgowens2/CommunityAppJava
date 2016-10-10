package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by bearden-tellez on 10/10/16.
 */

@RestController
public class CommunityJsonController {

    @Autowired
    MemberRepository members;


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
        System.out.println(member.email + "is about to be created");
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



}
