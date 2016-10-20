package com.example;

import org.aspectj.weaver.ast.Or;
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

    @Autowired
    OrganizationRepository organizations;

    @Autowired
    OrganizationMemberRepository organizationMembers;

    @Autowired
    InvitationRepository invitations;

    @RequestMapping(path = "/createDemoData.json", method = RequestMethod.GET)
    public void demoData(HttpSession session) throws Exception {

//  main demo orgs
        Organization techOrg = new Organization();
        techOrg.name= "All Things Tech";
        organizations.save(techOrg);

        Organization quakersOrg = new Organization();
        quakersOrg.name ="Atlanta Friends Meeting";
        organizations.save(quakersOrg);
//  additional orgs for demo
        Organization swimmerOrg = new Organization();
        swimmerOrg.name= "Atlanta Swim Team";
        organizations.save(swimmerOrg);

        Organization bookOrg = new Organization();
        bookOrg.name= "Decatur Book Club";
        organizations.save(bookOrg);

        Organization chessOrg = new Organization();
        chessOrg.name= "Chess Club";
        organizations.save(chessOrg);
//  core members aka us
        Member demoMemberRBT = new Member();
        demoMemberRBT.firstName = "Rebecca";
        demoMemberRBT.lastName = "Bearden-Tellez";
        demoMemberRBT.email = "rebecca.m.bearden@gmail.com";
        demoMemberRBT.password = "password";
        demoMemberRBT.streetAddress = "1600 Penn Ave";
        demoMemberRBT.photoURL = "";
        members.save(demoMemberRBT);

        OrganizationMember newOrgMember = new OrganizationMember(techOrg, demoMemberRBT);
        organizationMembers.save(newOrgMember);

        OrganizationMember qOneMember = new OrganizationMember(quakersOrg, demoMemberRBT);
        organizationMembers.save(qOneMember);

        Member demoMemberDG = new Member();
        demoMemberDG.firstName = "Donald";
        demoMemberDG.lastName = "Gowens";
        demoMemberDG.streetAddress = "382 Penn Ave";
        demoMemberDG.email= "dgowens@gmail.com";
        demoMemberDG.password= "candycorn";
        demoMemberDG.photoURL= " ";
        members.save(demoMemberDG);

        OrganizationMember secondOrgMember = new OrganizationMember(techOrg, demoMemberDG);
        organizationMembers.save(secondOrgMember);

        OrganizationMember qThreeMember = new OrganizationMember(quakersOrg, demoMemberDG);
        organizationMembers.save(qThreeMember);

        Member demoMemberDE = new Member();
        demoMemberDE.firstName = "Dan";
        demoMemberDE.lastName = "Esrey";
        demoMemberDE.streetAddress = "485 Penn Ave";
        demoMemberDE.email= "desrey@gmail.com";
        demoMemberDE.password= "97thpercentile";
        demoMemberDE.photoURL= " ";
        members.save(demoMemberDE);

        OrganizationMember thirdOrgMember = new OrganizationMember(techOrg, demoMemberDE);
        organizationMembers.save(thirdOrgMember);

        OrganizationMember qTwoMember = new OrganizationMember(quakersOrg, demoMemberDE);
        organizationMembers.save(qTwoMember);

// additional demo members

        Member demoMemberHP = new Member();
        demoMemberHP.firstName = "Harry";
        demoMemberHP.lastName = "Potter";
        demoMemberHP.streetAddress = "485 Hwy 12";
        demoMemberHP.email= "hp@gmail.com";
        demoMemberHP.password= "mischiefManaged";
        demoMemberHP.photoURL= "http://vignette1.wikia.nocookie.net/harrypotter/images/b/b2/2001-Harry-Potter-and-the-Sorcerer-s-Stone-Promotional-Shoot-HQ-harry-potter-11097228-1600-1960.jpg/revision/latest/scale-to-width-down/163?cb=20141122213655";
        members.save(demoMemberHP);

        OrganizationMember hpTechMember = new OrganizationMember(techOrg, demoMemberHP);
        organizationMembers.save(hpTechMember);

        OrganizationMember qHpMember = new OrganizationMember(quakersOrg, demoMemberHP);
        organizationMembers.save(qHpMember);

        OrganizationMember swimmerHpMember = new OrganizationMember(swimmerOrg, demoMemberHP);
        organizationMembers.save(swimmerHpMember);

        Member demoMemberWS = new Member();
        demoMemberWS.firstName = "Will";
        demoMemberWS.lastName = "Smith";
        demoMemberWS.streetAddress = "900 West Philborn Lane";
        demoMemberWS.email= "wildwildwest@gmail.com";
        demoMemberWS.password= "freshprince";
        demoMemberWS.photoURL= "https://s-media-cache-ak0.pinimg.com/originals/c6/e8/f1/c6e8f16711706e5506e1a39c121e61ed.jpg";
        members.save(demoMemberWS);

        OrganizationMember wsTechMember = new OrganizationMember(techOrg, demoMemberWS);
        organizationMembers.save(wsTechMember);

        OrganizationMember wsBookMember = new OrganizationMember(bookOrg, demoMemberWS);
        organizationMembers.save(wsBookMember);

        Member demoMemberTH = new Member();
        demoMemberTH.firstName = "Taraji P";
        demoMemberTH.lastName = "Henson";
        demoMemberTH.streetAddress = "900 West Philborn Lane";
        demoMemberTH.email= "tph@gmail.com";
        demoMemberTH.password= "cookie";
        demoMemberTH.photoURL= "http://www.indiewire.com/wp-content/uploads/2015/06/taraji-p-henson-as-cookie-in-foxs-empire.-Henson-as-Cookie-Lyon-1.jpg";
        members.save(demoMemberTH);

        OrganizationMember tpTechMember = new OrganizationMember(techOrg, demoMemberTH);
        organizationMembers.save(tpTechMember);

        OrganizationMember tpChessMember = new OrganizationMember(chessOrg, demoMemberTH);
        organizationMembers.save(tpChessMember);

        Event liveToLead = new Event();
        liveToLead.name = "Live2Lead";
        liveToLead.location = "Atlanta Tech Village 3423 Piedmont Rd. NE Atlanta, Georgia 30305";
        liveToLead.date= "11/5/2016 ~ 9:00";
        liveToLead.information = "Live2Lead is a half-day leader development experience designed to equip you with new perspectives, practical tools and key takeaways. ";
        liveToLead.organizer = demoMemberTH;
        liveToLead.organization = techOrg;
        events.save(liveToLead);


        Event ironPints = new Event();
        ironPints.name= "The Iron Yard Atlanta";
        ironPints.location = "115 M.L.K. Jr Dr SW #400, Atlanta, GA 30303";
        ironPints.date= "11/18/2016 ~ 16:00";
        ironPints.information= "Community Iron Pints is the 3rd Friday of every month when we open up our campus to the public for a relaxed social gathering.";
        ironPints.organization = techOrg;
        ironPints.organizer = demoMemberDE;
        events.save(ironPints);

        Event tagEvent = new Event();
        tagEvent.name= "Technology Association of Georgia: Moving and Modernizing Legacy Applications and Data to the Cloud";
        tagEvent.date = "11/3/2016 ~ 18:00";
        tagEvent.location = "1175 Peachtree Street NE, Suite 1400, Atlanta, GA 30309";
        tagEvent.information= "Join TAG Cloud as we Dive into ways of Modernizing and Moving Legacy Applications to the Cloud.";
        tagEvent.organization = techOrg;
        tagEvent.organizer = demoMemberHP;
        events.save(tagEvent);

        Event garnishEvent = new Event();
        garnishEvent.name = "HBS: Women’s SIG Wine and Food Tasting";
        garnishEvent.date = "11/6/2016 ~ 16:00";
        garnishEvent.location = "925 Garrett Street, Atlanta, GA 30316 United States";
        garnishEvent.information = "Emily Golub, the founder of Garnish & Gather, will be hosting us for a food and wine tasting at Joseph & Co.  ";
        garnishEvent.organizer = demoMemberRBT;
        garnishEvent.organization = techOrg;
        events.save(garnishEvent);

        Event devFest = new Event();
        devFest.name = "Google Developer DevFest";
        devFest.date = "11/12/2016 ~ 10:00";
        devFest.location = "675 Ponce de Leon Avenue NE, 2nd Floor, Atlanta, GA 30308 United States";
        devFest.information= "Programming DevFest offers talks and code labs that are accessible to both new and seasoned developers.";
        devFest.organizer= demoMemberWS;
        devFest.organization= techOrg;
        events.save(devFest);

        Post networkingPost = new Post();
        networkingPost.title = "Networking Tips";
        networkingPost.date = "10/27/2016 ~ 13:00";
        networkingPost.body= "1. Have an Intro\n 2. Research attendees\n 3. Strategically Place Yourself\n 4. Follow Up";
        networkingPost.author = demoMemberDG;
        networkingPost.organization= techOrg;
        posts.save(networkingPost);

        Post hackathonIdeas = new Post();
        hackathonIdeas.title = "Hackathon Ideas?";
        hackathonIdeas.date= "10/28/2016 ~ 15:45";
        hackathonIdeas.body= "I am currently planning for a Hackathon, but am out of ideas for the event. Please email me at dev@gmail.com if you would like to throw some ideas out. Any input is appreciated!";
        hackathonIdeas.author = demoMemberDE;
        hackathonIdeas.organization = techOrg;
        posts.save(hackathonIdeas);

        Post slackChannel = new Post();
        slackChannel.title = "New Slack Channel";
        slackChannel.date= "10/28/2016 ~ 10:00";
        slackChannel.body= "TechOrg is our new slack channel!";
        slackChannel.author= demoMemberRBT;
        slackChannel.organization = techOrg;
        posts.save(slackChannel);
//  posts/events for tech above

        Event qOneEvent = new Event();
        qOneEvent.name= "Free Breakfast";
        qOneEvent.location= "701 W Howard Ave, Decatur, GA 30030";
        qOneEvent.information= "Free breakfast to the public";
        qOneEvent.date = "11/6/2016 ~ 9:00";
        qOneEvent.organization= quakersOrg;
        qOneEvent.organizer= demoMemberHP;
        events.save(qOneEvent);

        Event qTwoEvent = new Event();
        qTwoEvent.name= "Morning Worship";
        qTwoEvent.date= "11/2/2016 ~ 8:30";
        qTwoEvent.location= "701 W Howard Ave, Decatur, GA 30030";
        qTwoEvent.information= "Silent worship";
        qTwoEvent.organizer= demoMemberHP;
        qTwoEvent.organization= quakersOrg;
        events.save(qTwoEvent);

        Event qThreeEvent = new Event();
        qThreeEvent.name= "Potluck";
        qThreeEvent.date= "11/6/2016 ~ 11:30";
        qThreeEvent.information= "Bring your favorite dish to our monthly potluck!";
        qThreeEvent.location= "701 W Howard Ave, Decatur, GA 30030";
        qThreeEvent.organizer= demoMemberHP;
        events.save(qThreeEvent);

        Post qOnePost = new Post();
        Post qTwoPost = new Post();
        Post qThreePost = new Post();
        Post qFourPost = new Post();


    }


    @RequestMapping(path = "/login.json", method = RequestMethod.POST)
    public MemberResponseContainer login(HttpSession session, @RequestBody Member member) throws Exception {
        MemberResponseContainer myResponse = new MemberResponseContainer();
        Member newMember = members.findFirstByEmail(member.email);
        try {
            if (newMember == null) {
                myResponse.errorMessage = "User does not exist or was input incorrectly";
                System.out.println(" Username was null");
            } else if (!member.password.equals(newMember.getPassword())) {
                myResponse.errorMessage = "Incorrect password";
                System.out.println("Password attempt failed. Incorrect password");
            } else if (newMember != null && newMember.password.equals(newMember.getPassword())) {
                System.out.println(newMember.firstName + " " + newMember.lastName + " is logging in");
                if (newMember.photoURL == null) {
                    newMember.setPhotoURL("dummy photo URL");
                }
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
                ArrayList<Invitation> listInvites = invitations.findByInvitedEmail(member.getEmail());
                int size = listInvites.size();
                if (size>=1) {
                    ArrayList<Invitation> allInvites = invitations.findByInvitedEmail(member.getEmail());
                    for (Invitation currentInvite : allInvites) {
                        Organization organization = currentInvite.getOrganization();
                        member = new Member(member.firstName, member.lastName, member.email, member.password, member.streetAddress, member.photoURL);
                        if (member.photoURL == null) {
                            member.setPhotoURL("dummy photo URL");
                        }
                        members.save(member);
                        OrganizationMember organizationMemberAssociation = new OrganizationMember(organization, member);
                        organizationMemberAssociation.setOrganization(organization);
                        organizationMembers.save(organizationMemberAssociation);
                        myResponse.responseMember = member;
                    }
                } else {
                    member = new Member(member.firstName, member.lastName, member.email, member.password, member.streetAddress, member.photoURL);
                    if (member.photoURL == null) {
                        member.setPhotoURL("dummy photo URL");
                    }
                    members.save(member);
                    myResponse.responseMember = member;
                    session.setAttribute("member", member);
                    //later they would create an org
                }
            } else {
                    myResponse.setErrorMessage("User already exists");
            }
        }catch (Exception ex) {
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
                post = new Post(post.date, post.title, post.body, post.author, post.organization);
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

    public List<Post> getAllPostsByAuthor(Member author) {
        Iterable<Post> allPosts = posts.findByAuthor(author);
        List<Post> postList = new ArrayList<>();
        for (Post currentPost : allPosts) {
            postList.add(currentPost);

        }
        System.out.println("after iterable");
        return postList;
    }

    @RequestMapping(path = "/postsListByMember.json", method = RequestMethod.POST)
    public PostContainer getAllPostsByAuthorWithEndpoint(@RequestBody Member author) {
        PostContainer postContainer = new PostContainer();
        try {
            Iterable<Member> allMembers = members.findAll();
            for (Member currentMember : allMembers) {
                if (currentMember.getEmail().equals(author.email)) {
                    Iterable<Post> allPosts = posts.findByAuthor(currentMember);
                    List<Post> postList = new ArrayList<>();
                    for (Post currentPost : allPosts) {
                        postList.add(currentPost);
                        try {
                            if (postList == null) {
                                postContainer.setErrorMessage("Post list was empty and therefore cannot be saved");
                            } else {
                                postContainer.setPostList(postList);
                                System.out.println("post id = " + postList.indexOf(currentPost));
                            }
                        } catch (Exception ex) {
                            postContainer.setErrorMessage("An exception occurred creating a post list");
                            ex.printStackTrace();
                        }
                    }
                } else {
                    postContainer.setErrorMessage("No members posts to display");
                }
            }
            System.out.println("after iterable");
        } catch (Exception ex) {
            postContainer.setErrorMessage("An exception occurred creating a post list");
            ex.printStackTrace();
        }
        return postContainer;
    }

//    @RequestMapping(path = "/postsListByMember.json", method = RequestMethod.GET)
//    public PostContainer getAllPostsByAuthorWithEndpointGet(HttpSession session, Member author) {
//        author = (Member) session.getAttribute("member");
//        PostContainer postContainer = new PostContainer();
//        Iterable<Post> allPosts = posts.findByAuthor(author);
//        List<Post> postList = new ArrayList<>();
//        for (Post currentPost : allPosts) {
//            postList.add(currentPost);
//            try {
//                if (postList == null) {
//                    postContainer.setErrorMessage("Post list was empty and therefore cannot be saved");
//
//                } else {
//                    postContainer.setPostList(postList);
//                    System.out.println("post id = " + postList.indexOf(currentPost));
//                }
//            } catch (Exception ex){
//                postContainer.setErrorMessage("An exception occurred creating a post list");
//                ex.printStackTrace();
//            }
//        }
//        System.out.println("after iterable");
//        return postContainer;
//    }

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
        thisEvent = new Event(thisEvent.name, thisEvent.date, thisEvent.location, thisEvent.information);

        try{
            if(thisEvent == null) {
               myResponse.setErrorMessage("Retrieved a null event");

            } else {
                thisEvent = new Event(thisEvent.name,thisEvent.date, thisEvent.location, thisEvent.information, thisEvent.organizer, thisEvent.organization);
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
            myResponse.setEventList(myEvents);
//            for (Event myEvent : myEvents) {
//                myResponse.eventList.add(myEvent);
//                System.out.println("returning list of events");
//            }
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

    @RequestMapping(path = "/eventsListByMember.json", method = RequestMethod.POST)
    public EventContainer getAllEventsByOrganizerWithEndpoint(@RequestBody Member organizer) {
        EventContainer eventContainer = new EventContainer();
        try {
            Iterable<Member> allMembers = members.findAll();
            for (Member currentMember: allMembers) {
                if (currentMember.getEmail().equals(organizer.email)) {
                    Iterable<Event> allEvents = events.findByOrganizer(currentMember);
                    List<Event> eventList = new ArrayList<>();
                    for (Event currentEvent : allEvents) {
                        eventList.add(currentEvent);
                            if (eventList != null) {
                                eventContainer.setEventList(eventList);
                                System.out.println("event id = " + eventList.indexOf(currentEvent));
                            } else {
                                eventContainer.setErrorMessage("Event list was empty and therefore cannot be saved");
                            }
                        }
                } else {
                    eventContainer.setErrorMessage("No members events to display");
                }
            }
        } catch (Exception ex) {
            eventContainer.setErrorMessage("An exception occurred creating an event list");
            ex.printStackTrace();
        }
        System.out.println("after iterable");
        return eventContainer;
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

    @RequestMapping (path= "/createOrganization.json", method = RequestMethod.POST)
    public OrganizationContainer createOrganization(HttpSession session, @RequestBody Organization organization) throws  Exception {
        Member member = (Member) session.getAttribute("member");
        OrganizationContainer organizationContainer = new OrganizationContainer();
        organization = new Organization(organization.name);
        try {
            if (organization == null) {
                organizationContainer.setErrorMessage("Organization name was empty and therefore cannot be saved");

            } else {
                organization = new Organization(organization.name);
                organizations.save(organization);
                organizationContainer.setResponseOrganization(organization);
                OrganizationMember newOrgMember = new OrganizationMember(organization, member);
                System.out.println("Organization id = " + organization.id);
            }
        } catch (Exception ex){
            organizationContainer.setErrorMessage("An exception occurred creating an organization");
            ex.printStackTrace();
        }
        return organizationContainer;
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

//    @RequestMapping (path= "/joinOrganization.json", method = RequestMethod.POST)
//    public OrganizationMemberContainer joinOrganization(HttpSession session, @RequestBody Integer organizationId) throws Exception {
//        OrganizationMemberContainer myResponse = new OrganizationMemberContainer();
//        Member member = (Member) session.getAttribute("member");
//        Organization organization = organizations.findOne(organizationId);
//
//        try {
//            if(member.email.equals(invitations.findByInvitedEmail(member.getEmail()))) {
//                OrganizationMember organizationMemberAssociation = new OrganizationMember(organization, member);
//                organizationMemberAssociation.setOrganization(organization);
//                organizationMembers.save(organizationMemberAssociation);
//                myResponse.setOrganizationMemberList(organizationMembers.findMembersByOrganization(organization));
//                System.out.println("organization set");
//            } else {
//                myResponse.setErrorMessage("User was not invited to join this organization");
//            }
//        } catch (Exception ex) {
//            myResponse.setErrorMessage("A problem occurred while trying to join an organization");
//            ex.printStackTrace();
//        }
//        return myResponse;
//    }

    @RequestMapping (path= "/joinOrganization.json", method = RequestMethod.POST)
    public OrganizationMemberContainer joinOrganization(HttpSession session) throws Exception {
        OrganizationMemberContainer myResponse = new OrganizationMemberContainer();
        Member member = (Member) session.getAttribute("member");
        ArrayList<Invitation> allInvites =  invitations.findByInvitedEmail(member.getEmail());

        try {
            if(allInvites != null) {
                for (Invitation currentInvite: allInvites) {
                    Organization organization = currentInvite.getOrganization();
                    OrganizationMember organizationMemberAssociation = new OrganizationMember(organization, member);
                    organizationMemberAssociation.setOrganization(organization);
                    organizationMembers.save(organizationMemberAssociation);
                    myResponse.setOrganizationMemberList(organizationMembers.findMembersByOrganization(organization));
                    System.out.println("organization set");
                }
            } else {
                myResponse.setErrorMessage("User was not invited to join this organization");
            }
        } catch (Exception ex) {
            myResponse.setErrorMessage("A problem occurred while trying to join an organization");
            ex.printStackTrace();
        }
        return myResponse;
    }


    public ArrayList<OrganizationMember> refreshOrganizationMemberList() {
        ArrayList<OrganizationMember> organizationMembersArrayList = new ArrayList<>();
        Iterable<OrganizationMember> allOrganizationMembers = organizationMembers.findAll();

        for (OrganizationMember orgMem : allOrganizationMembers) {
            organizationMembersArrayList.add(orgMem);

        }
        return organizationMembersArrayList;
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

    @RequestMapping(path = "/organizationsList.json", method = RequestMethod.GET)
    public List<Organization> getAllOrganizations() {
        Iterable<Organization> allOrganizations = organizations.findAll();
        List<Organization> organizationsList = new ArrayList<>();
        for (Organization thisOrganization : allOrganizations) {
            organizationsList.add(thisOrganization);
        }
        return organizationsList;
    }


    @RequestMapping (path= "/postsByOrg.json", method = RequestMethod.POST)
    public PostContainer getAllPosts(HttpSession session, @RequestBody Organization organization){
        PostContainer myResponse = new PostContainer();
        try {
            ArrayList<Post> postsByOrg = new ArrayList<>();
            postsByOrg= posts.findByOrganization(organization);
            if (postsByOrg == null){
                myResponse.setErrorMessage("This organization has no posts");
            } else {
                myResponse.setPostList(postsByOrg);
            }
        }catch (Exception ex){
            myResponse.setErrorMessage("An exception occurred in getting posts by organization");
            ex.printStackTrace();
        }
        return myResponse;
    }

    @RequestMapping (path= "/eventsByOrg.json", method = RequestMethod.POST)
    public EventContainer getAllEvents(HttpSession session, @RequestBody Organization organization){
        EventContainer myResponse = new EventContainer();
        try {
            ArrayList<Event> eventsByOrg = new ArrayList<>();
            eventsByOrg = events.findByOrganization(organization);
            if (eventsByOrg == null){
                myResponse.setErrorMessage("This organization has no events");
            } else {
                myResponse.setEventList(eventsByOrg);
            }
        } catch (Exception ex){
            myResponse.setErrorMessage("An exception occurred in getting events by organization");
            ex.printStackTrace();
        }
        return myResponse;
    }
}
