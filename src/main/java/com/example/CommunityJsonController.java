package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    OrganizationRepository organizations;

    @RequestMapping(path = "/login.json", method = RequestMethod.POST)
    public MemberResponseContainer login(HttpSession session, @RequestBody Member member) throws Exception {
        MemberResponseContainer myResponse = new MemberResponseContainer();
        Member newMember = members.findFirstByEmail(member.email);
        try {
            if (newMember == null) {
                myResponse.errorMessage = "User does not exist or was input incorrectly";
            } else if (!member.password.equals(newMember.getPassword())) {
                myResponse.errorMessage = "Incorrect password";
            } else if (newMember != null && newMember.password.equals(newMember.getPassword())) {
                System.out.println(newMember.email + " is logging in");
                session.setAttribute("member", newMember);
                myResponse.responseMember = newMember;
            }
        }catch (Exception ex) {
            myResponse.setErrorMessage("An exception occurred while logging in");
            ex.printStackTrace();
        }
        return myResponse;
    }

    @RequestMapping(path = "/register.json", method = RequestMethod.POST)
    public MemberResponseContainer newMemberInfo(HttpSession session, @RequestBody Member member) throws Exception {
        MemberResponseContainer myResponse = new MemberResponseContainer();
        Member newMember = members.findFirstByEmail(member.email);
        System.out.println(member.email + " is about to be created");
        try {
            if (newMember == null) {
                member = new Member(member.email, member.firstName, member.lastName, member.password, member.streetAddress);
                members.save(member);
                myResponse.responseMember = member;
                session.setAttribute("member", member);
            } else {
                myResponse.setErrorMessage("User already exists");
            }
        } catch (Exception ex) {
            myResponse.setErrorMessage("An exception occurred while registering");
            ex.printStackTrace();
        }
        return myResponse;
    }

    @RequestMapping(path = "/createPost.json", method = RequestMethod.POST)
    public PostContainer createPost(HttpSession session, @RequestBody Post post) {
//        Member member = (Member) session.getAttribute("member");
        Member author = (Member) session.getAttribute("member");  //changed member to author
        PostContainer postContainer = new PostContainer();
        post = new Post(post.date, post.title, post.body);
        try {
            if (post == null) {
                postContainer.setErrorMessage("Post was empty and therefore cannot be saved");

            } else {
                post = new Post(post.date, post.title, post.body, post.author);
                post.setMember(author);
                posts.save(post);
                postContainer.setPostList(getAllPostsByAuthor(author));
                System.out.println("post id = " + post.id);
            }
        } catch (Exception ex){
            postContainer.setErrorMessage("An exception occurred creating a post");
            ex.printStackTrace();
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
    public List<Post> getAllPostsByAuthor(Member author) {
        Iterable<Post> allPosts = posts.findByAuthor(author);
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

    @RequestMapping(path = "/editPost.json", method = RequestMethod.POST)
    public PostContainer editPost(HttpSession session, @RequestBody Post thispost) {
        Member author = (Member) session.getAttribute("author");
        PostContainer myResponse = new PostContainer();
        try {
            if (author == (thispost.author)) {

                posts.save(thispost);

                System.out.println("Saving edited post");

                myResponse.postList = posts.findByAuthor(author);
                System.out.println("Returning list of posts by  author");
            } else {
                myResponse.errorMessage = "Member did not create post and thus cannot edit it.";
            }
        } catch (Exception ex){
            myResponse.errorMessage = "An Error occurred while editing a post";
            ex.printStackTrace();
        }
        return myResponse;
    }

    @RequestMapping(path = "/singlePost.json", method = RequestMethod.GET)
    public PostContainer getSpecificPost(Integer postID) {
        System.out.println("finding post with post id " + postID);
        PostContainer myResponse = new PostContainer();

        Post myPost = posts.findById(postID);
        try {
            if (myPost == null) {
                myResponse.setErrorMessage("No post found");
            } else {
                System.out.println("Found post with title:" + myPost.title);
                myResponse.setResponsePost(myPost);
            }
        } catch (Exception ex){
            myResponse.setErrorMessage("Exception while getting single post");
            ex.printStackTrace();
        }
        return myResponse;
    }


    @RequestMapping(path = "/createEvent.json", method = RequestMethod.POST)
    public EventContainer createEvent(HttpSession session, @RequestBody Event thisEvent) {
        Member member = (Member) session.getAttribute("member");
        EventContainer myResponse = new EventContainer();
        thisEvent = new Event(thisEvent.name, thisEvent.location, thisEvent.date, thisEvent.name);

        try{
            if(thisEvent == null) {
               myResponse.setErrorMessage("Retrieved a null event");

            } else {
                thisEvent = new Event(thisEvent.name,thisEvent.date, thisEvent.location, thisEvent.information, thisEvent.organizer);
                thisEvent.setOrganizer(member);
                events.save(thisEvent);

                System.out.println("Creating event");
                myResponse.setEventList(getAllEvents());
                System.out.println("Returning list of events");
            }
        } catch (Exception ex){
            myResponse.setErrorMessage("An Error occurred while creating an event");
            ex.printStackTrace();
        }
        return myResponse;
    }


    @RequestMapping(path = "/editEvent.json", method = RequestMethod.POST)
    public EventContainer editEvent(HttpSession session, @RequestBody Event thisEvent) {
        Member member = (Member) session.getAttribute("member");
        EventContainer myResponse = new EventContainer();
        try {
            if (member == (thisEvent.organizer)) {

                events.save(thisEvent);

                System.out.println("Saving edited event");

                myResponse.setEventList(getAllEvents());
                System.out.println("Returning list of events");
            } else {
                myResponse.setErrorMessage("Member did not create event and thus cannot edit it.");
            }
        } catch (Exception ex){
            myResponse.setErrorMessage("An Error occurred while editing an event");
            ex.printStackTrace();
        }
        return myResponse;
    }


    @RequestMapping(path = "/eventsList.json", method = RequestMethod.GET)
    public EventContainer eventThings(HttpSession session) {
        EventContainer myResponse = new EventContainer();
        ArrayList<Event> myEvents = getAllEvents();
        int myEventListSize = myEvents.size();

        if (myEventListSize == 0) {
            myResponse.setErrorMessage("No events to display");

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
        try {
            if (myEvent == null) {
                myResponse.setErrorMessage("No event found");
            } else {
                System.out.println("Found event " + myEvent.name);
                myResponse.setResponseEvent(myEvent);
            }
        } catch (Exception ex){
            myResponse.setErrorMessage("An exception occurred while retrieving event. ");
            ex.printStackTrace();
        }
        return myResponse;
    }

    @RequestMapping(path = "/attendEvent.json", method = RequestMethod.POST)
    public MemberEventContainer checkInAtEvent(HttpSession session, @RequestBody Event event) throws Exception{
        MemberEventContainer myResponse = new MemberEventContainer();
        Member member = (Member) session.getAttribute("member");

        try {
            MemberEvent attendingEvent = new MemberEvent(member, event);

            memberevents.save(attendingEvent);

            myResponse.setEventList(memberevents.findMembersByEvent(event));

        } catch (Exception ex){
            myResponse.setErrorMessage("A problem occurred while trying to attend an event");
            ex.printStackTrace();
        }
        return myResponse;
    }


    @RequestMapping(path = "/sendInvitation.json", method = RequestMethod.POST)
    public InvitationContainer evite(HttpSession session, @RequestBody String invitedEmail) throws Exception {
        InvitationContainer myResponse = new InvitationContainer();
        Member member = (Member) session.getAttribute("member");
        try{
            if (invitedEmail == null){
                myResponse.setErrorMessage("Invited email was null");
            } else {
            myResponse.setSuccessMessage("Invitation sent successfully");
            }
        } catch (Exception ex) {
            myResponse.setErrorMessage("An error occurred while trying to send an invite");
            ex.printStackTrace();
        }
        return myResponse;
    }

    @RequestMapping (path= "/organizationProfile.json", method = RequestMethod.GET)
    public OrganizationContainer thisOrg(HttpSession session, @RequestBody Integer organizationId) throws Exception {
        OrganizationContainer myResponse = new OrganizationContainer();
        Organization myOrg = organizations.findOne(organizationId);
        try{
            if (myOrg == null){
                myResponse.setErrorMessage("Organization was null");
            } else {
                myResponse.setResponseOrganization(myOrg);
            }

        } catch (Exception ex){
            myResponse.setErrorMessage("Exception while accessing org profile");
            ex.printStackTrace();
        }
        return myResponse;
    }

    @RequestMapping (path= "/memberProfile.json", method = RequestMethod.GET)
    public MemberResponseContainer thisMember(HttpSession session, @RequestBody Integer memberId) throws Exception {
        MemberResponseContainer myResponse = new MemberResponseContainer();
        Member myMember = members.findOne(memberId);
        try{
            if (myMember == null){
                myResponse.setErrorMessage("Member was null");
            } else {
                myResponse.setResponseMember(myMember);
            }

        } catch (Exception ex){
            myResponse.setErrorMessage("Exception while accessing member profile");
            ex.printStackTrace();
        }
        return myResponse;
    }

}
