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

    @Autowired
    EventRepository events;

    @Autowired
    MemberEventRepository memberevents;

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

    @RequestMapping(path = "/createPost.json", method = RequestMethod.POST)
    public PostContainer createPost(HttpSession session, @RequestBody Post post) {
        Member member = (Member) session.getAttribute("member");
        PostContainer postContainer = new PostContainer();
        post = new Post(post.date, post.title, post.body, post.member);

        if (post == null) {
            postContainer.errorMessage = "Post was empty and therefore cannot be saved";

        } else {
            post = new Post(post.date, post.title, post.body, post.member);
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
        Iterable<Post> allPosts = posts.findByMember(member);
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

    //test with angular

    @RequestMapping(path = "/createEvent.json", method = RequestMethod.POST)
    public EventContainer createEvent(HttpSession session, @RequestBody Event thisEvent) {
        Member member = (Member) session.getAttribute("member");
        EventContainer myResponse = new EventContainer();

        try {
            thisEvent = new Event(thisEvent.name, thisEvent.location, thisEvent.date, thisEvent.name, thisEvent.organizer);
            events.save(thisEvent);

            System.out.println("Creating event");

            myResponse.eventList = getAllEvents();
            System.out.println("Returning list of events");
        } catch (Exception ex){
            myResponse.errorMessage = "An Error occurred while creating an event";
        }
        return myResponse;
    }


    @RequestMapping(path = "/editEvent.json", method = RequestMethod.POST)
    public EventContainer editEvent(HttpSession session, @RequestBody Event thisEvent) {
        Member member = (Member) session.getAttribute("member");
        EventContainer myResponse = new EventContainer();
        try {
            if (member.firstName.equalsIgnoreCase(thisEvent.organizer.firstName)) {

                events.save(thisEvent);

                System.out.println("Saving edited event");

                myResponse.eventList = getAllEvents();
                System.out.println("Returning list of events");
            } else {
                myResponse.errorMessage = "Member did not create event and thus cannot edit it.";
            }
        } catch (Exception ex){
            myResponse.errorMessage = "An Error occurred while editing an event";
        }
        return myResponse;
    }


    @RequestMapping(path = "/eventsList.json", method = RequestMethod.GET)
    public EventContainer eventThings(HttpSession session) {
        EventContainer myResponse = new EventContainer();

        ArrayList<Event> myEvents = getAllEvents();
        int myEventListSize = myEvents.size();

        if (myEventListSize == 0) {
            myResponse.errorMessage = "No events to display";

        } else {
            for (Event myEvent : myEvents) {
                myResponse.eventList.add(myEvent);
                System.out.println("returning list of events");
            }
        }
        return myResponse;
    }

    ArrayList<Event> getAllEvents() {
        ArrayList<Event> eventList = new ArrayList<Event>();
        Iterable<Event> allEvents = events.findAll();

        for (Event currentEvent : allEvents) {
            eventList.add(currentEvent);

        }
        return eventList;
    }

    @RequestMapping(path = "/event.json", method = RequestMethod.GET)
    public EventContainer getSpecificEvent(Integer eventID) {
        System.out.println("finding event with event id " + eventID);
        EventContainer myResponse = new EventContainer();

        Event myEvent = events.findById(eventID);
        if (myEvent == null) {
            myResponse.errorMessage = "No event found";
        } else {
            System.out.println("Found event " + myEvent.name);
            myResponse.responseEvent = myEvent;
        }
        return myResponse;
    }

    @RequestMapping(path = "/attendEvent.json", method = RequestMethod.POST)
    public MemberEventContainer checkInAtEvent(HttpSession session, @RequestBody Event event) throws Exception{
        MemberEventContainer myResponse = new MemberEventContainer();
        Member member = (Member) session.getAttribute("user");

        try {
            MemberEvent attendingEvent = new MemberEvent(member, event);

            memberevents.save(attendingEvent);

            myResponse.setEventList(memberevents.findMembersByEvent(event));
        } catch (Exception ex){
            myResponse.setErrorMessage("A problem occurred while trying to attend an event");

        }
        return myResponse;
    }


    @RequestMapping(path = "/sendInvitation.json", method = RequestMethod.POST)
    public InvitationContainer evite(HttpSession session) throws Exception {
        InvitationContainer myResponse = new InvitationContainer();
        try{
            if 



        } catch (Exception ex) {
            myResponse.setErrorMessage("An error occurred while trying to send an invite");
        }

        return myResponse;
    }
}
