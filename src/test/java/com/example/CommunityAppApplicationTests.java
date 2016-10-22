package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityAppApplicationTests {

	@Autowired
	MemberRepository members;

	@Autowired
	EventRepository events;

	@Autowired
	PostRepository posts;

	@Autowired
	OrganizationRepository organizations;

	@Autowired
	OrganizationMemberRepository organizationmembers;

	@Autowired
	MemberEventRepository memberevents;

	@Autowired
	InvitationRepository invitations;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testCreateMember() {
		Member newMember = new Member();
		Member dbMember = new Member();

		try {
			newMember.firstName = "Testing";
			newMember.lastName = "McTesterson";
			newMember.email = "Testingemail@gmail.com";
			newMember.password = "notsecure";
			newMember.streetAddress = "123 TIY Drive, Atlanta, GA 30102";
			members.save(newMember);

			dbMember = members.findFirstByEmail("Testingemail@gmail.com");
			assertNotNull(dbMember);

		} finally {
			members.delete(newMember);
		}
	}

	@Test
	public void testCreateMemberWithInvitation(){
		Member invitingMember = new Member();
		Member invitedMember = new Member();
		OrganizationMember memberInvitingMember = new OrganizationMember();
		OrganizationMember memberInvitedMember = new OrganizationMember();
		Organization theOrg = new Organization();
		Invitation theInvite = new Invitation();


		Member dbMember = new Member();

		try {
			invitingMember.firstName = "Tess";
			invitingMember.lastName = "Welch";
			invitingMember.email = "Tess@gmail.com";
			invitingMember.password = "notsecure";
			invitingMember.streetAddress = "123 TIY Drive, Atlanta, GA 30102";
			members.save(invitingMember);


			theOrg.name = "Whataburger";
			organizations.save(theOrg);

			memberInvitingMember = new OrganizationMember(theOrg, invitingMember);
			organizationmembers.save(memberInvitingMember);

			theInvite = new Invitation(invitingMember, "monsters@gmail.com", theOrg);
			invitations.save(theInvite);

			ArrayList<Invitation> invitesAL = invitations.findByInvitedEmail("monsters@gmail.com");
			int alSize = invitesAL.size();

			if (alSize>=1) {
				ArrayList<Invitation> allInvites = invitations.findByInvitedEmail("monsters@gmail.com");
				for (Invitation currentInvite : allInvites) {
					Organization organization = currentInvite.getOrganization();
					invitedMember.firstName = "Sam";
					invitedMember.lastName = "Gut";
					invitedMember.email = "monsters@gmail.com";
					invitedMember.password = "lola";
					invitedMember.streetAddress = "30 Hwy 42";
					members.save(invitedMember);
					memberInvitedMember = new OrganizationMember(organization, invitedMember);
					memberInvitedMember.setOrganization(organization);
					organizationmembers.save(memberInvitedMember);
				}
			}else {
				System.out.println("Didn't work");
			}

			assertNotNull(organizationmembers.findByMemberEmail("monster@gmail.com"));


		} finally{
			invitations.delete(theInvite);
			organizationmembers.delete(memberInvitingMember);
			organizationmembers.delete(memberInvitedMember);
			organizations.delete(theOrg);
			members.delete(invitingMember);
			members.delete(invitedMember);
		}
	}


	@Test
	public void testCreateUserThatExists() throws Exception {
		boolean thrown = false;
		Member tester = new Member();
		Member testerTwo = new Member();

		try {
			tester.firstName = "Tu Wong";
			tester.lastName = "Foo";
			tester.email = "reck@gmail.com";
			tester.password = "JavaBeanForLife";
			tester.streetAddress = "543 TIY Drive, Atlanta, GA 30102";
			members.save(tester);

			testerTwo.firstName = "Rebecca";
			testerTwo.lastName = "Bearden-Tellez";
			testerTwo.email = "reck@gmail.com";
			testerTwo.password = "JavaBeanForLife";
			testerTwo.streetAddress = "543 TIY Drive, Atlanta, GA 30102";
			members.save(testerTwo);
		} catch (DataIntegrityViolationException exception) {
			thrown = true;
		} finally {
			assertTrue(thrown);
			members.delete(tester);
		}

//		members.delete(testerTwo);
	}

	@Test
	public void testInsertingAnEmptyValueIntoUser() throws PSQLException {
		boolean thrown = false;

		try {
			Member newMember = new Member();
			newMember.lastName = "McTesterson";
			newMember.email = "Testingemail@gmail.com";
			newMember.password = "notsecure";
			newMember.streetAddress = "123 TIY Drive, Atlanta, GA 30102";
			members.save(newMember);

		} catch (Exception ex) {
			thrown = true;

		}
		assertTrue(thrown);
	}

	@Test
	public void testFindEventByName() {
		Event newEvent = new Event();
		Event dbEvent = new Event();

		Member newMember = new Member();

		try {
			newMember.firstName = "Tommy";
			newMember.lastName = "Hitch";
			newMember.email = "useremail@gmail.com";
			newMember.password = "surprise";
			newMember.streetAddress = "123 TIY Drive, Atlanta, GA 30102";
			members.save(newMember);


			newEvent.name = "Party Hardy";
			newEvent.location = "West End";
			newEvent.information = "Dinner fo thieves";
			newEvent.date = "5/30/2017 ~ 1:30 PM";
			newEvent.organizer = newMember;
			events.save(newEvent);
			dbEvent = events.findByName("Party Hardy");

			assertNotNull(dbEvent);

		} finally {
			events.delete(newEvent);
			members.delete(newMember);
		}
	}

	@Test
	public void testCreateEvent() {
		Event newEvent = new Event();
		Event dbEvent = new Event();
		Member testOrganizer = new Member();
		Organization testOrganization = new Organization();
		try {
			testOrganizer.firstName = "Q";
			testOrganizer.lastName = "Wes";
			testOrganizer.email = "quess@yahoo.com";
			testOrganizer.streetAddress = "222 Lucky Lane";
			testOrganizer.password = "eastside";
			members.save(testOrganizer);

			testOrganization.name = "Health Org";
			organizations.save(testOrganization);

			newEvent.name = "Peoply";
			newEvent.location = "West End";
			newEvent.date = "4/5/2017";
			newEvent.information = "Dinner fo thieves";
			newEvent.organizer = testOrganizer;
			newEvent.organization = testOrganization;


			events.save(newEvent);
			dbEvent = events.findOne(newEvent.getId());
			assertNotNull(dbEvent);

		} finally {
			events.delete(newEvent);
			organizations.delete(testOrganization);
			members.delete(testOrganizer);


		}
	}

	@Test
	public void testEditEvent() {
		Event newEvent = new Event();
		Event dbEvent = new Event();
		Member tMember = new Member();
		try {

			tMember.firstName = "Wally";
			tMember.lastName = "Willy";
			tMember.email = "theo@yahoo.com";
			tMember.streetAddress = "900 Frogs Drive, Kennesaw GA 30152";
			tMember.password = "tinktink";
			members.save(tMember);


			newEvent.name = "Party Hardy";
			newEvent.location = "West End";
			newEvent.information = "Dinner fo thieves";
			newEvent.date = "5/30/2017 ~ 1:30 PM";
			newEvent.organizer = tMember;
			events.save(newEvent);

			dbEvent = events.findOne(newEvent.getId());
			dbEvent.name = "My new party";
			events.save(dbEvent);

			assertEquals(dbEvent.getId(), newEvent.getId());
		} finally {
			events.delete(newEvent);
			members.delete(tMember);
		}
	}

	@Test
	public void testEmptyFieldForEvent() throws Exception {
		boolean thrown = false;
		Event testEvent = new Event();
		try {
			testEvent.date = "3/3/2013 ~ 3:30 PM";
			testEvent.information = "The new new";
			events.save(testEvent);

		} catch (Exception ex) {
			thrown = true;
		}
		assertTrue(thrown);

	}

	@Test
	public void testFindEventById() {
		Event newEvent = new Event();
		Event dbEvent = new Event();
		Member testMember = new Member();
		try {
			testMember.firstName = "Tam Tam";
			testMember.lastName = "Bam";
			testMember.streetAddress = "3824 Winder Rd";
			testMember.email = "redman@gmail.com";
			testMember.password = "lucky";
			members.save(testMember);

			newEvent.name = "Party Hardy";
			newEvent.location = "West End";
			newEvent.information = "Dinner fo thieves";
			newEvent.date = "5/30/2017 ~ 1:30 PM";
			newEvent.organizer = testMember;
			events.save(newEvent);

			dbEvent = events.findById(newEvent.getId());
			dbEvent.name = "My new party";
			events.save(dbEvent);

			assertEquals(dbEvent.getId(), newEvent.getId());
		} finally {
			events.delete(newEvent);
			members.delete(testMember);
		}
	}

	@Test
	public void testCreatePost() {
		Post testPost = new Post();
		ArrayList<Post> dbPosts = new ArrayList<Post>();
		Member tester = new Member();

		try {
			tester.firstName = "Tupelo";
			tester.lastName = "MS";
			tester.email = "twf@gmail.com";
			tester.password = "JavaBeanForLife";
			tester.streetAddress = "543 TIY Drive, Atlanta, GA 30102";
			members.save(tester);

			testPost.date = "2/3/14 ~ 2:10 AM";
			testPost.title = "Goodies";
			testPost.body = "Not my goodies";
			testPost.author = tester;
			posts.save(testPost);
			dbPosts = posts.findByAuthor(tester);

			assertNotNull(dbPosts);

		} finally {
			posts.delete(testPost);
			members.delete(tester);

		}

	}

	@Test
	public void testEditPost() {
		Post testPost = new Post();
		Post dbPost = new Post();
		Member tester = new Member();

		try {
			tester.firstName = "Tupelo";
			tester.lastName = "MS";
			tester.email = "twf@gmail.com";
			tester.password = "JavaBeanForLife";
			tester.streetAddress = "543 TIY Drive, Atlanta, GA 30102";
			members.save(tester);

			testPost.date = "2/3/14 ~ 2:10 AM";
			testPost.title = "Goodies";
			testPost.body = "Not my goodies";
			testPost.author = tester;
			posts.save(testPost);
			dbPost = posts.findById(testPost.getId());

			dbPost.body = "Ciara";
			posts.save(dbPost);

			assertEquals(testPost.getId(), dbPost.getId());

		} finally {
			posts.delete(testPost);
			posts.delete(dbPost);
			members.delete(tester);
		}
	}

	@Test
	public void testFindPostById() {
		Post newPost = new Post();
		Post dbPost = new Post();
		Member testMember = new Member();
		try {
			testMember.firstName = "Tam Tam";
			testMember.lastName = "Bam";
			testMember.streetAddress = "3824 Winder Rd";
			testMember.email = "rum@gmail.com";
			testMember.password = "lucky";
			members.save(testMember);

			newPost.title = "Hello";
			newPost.body = "Unit Testing for life";
			newPost.date = "5/30/2017 ~ 1:30 PM";
			newPost.author = testMember;
			posts.save(newPost);

			dbPost = posts.findById(newPost.getId());
			dbPost.title = "My new party";
			posts.save(dbPost);

			assertEquals(dbPost.getId(), newPost.getId());

		} finally {
			posts.delete(newPost);
			members.delete(testMember);
		}
	}

	@Test
	public void testFindPostsByUser() {
		Post testPost = new Post();
		Post secondPost = new Post();
		Member tester = new Member();

		try {
			tester.firstName = "Lakeland";
			tester.lastName = "Fordays";
			tester.email = "wut@gmail.com";
			tester.password = "JavaBeanForLife";
			tester.streetAddress = "334 Sand Drive, Atlanta, GA 30102";
			members.save(tester);

			testPost.date = "2/3/14 ~ 2:10 AM";
			testPost.title = "Goodies";
			testPost.body = "Not my goodies";
			testPost.author = tester;
			posts.save(testPost);

			secondPost.title = "Waterfalls";
			secondPost.body = "Don't go chasing";
			secondPost.date = "6/4/2020 ~ 7:30 PM";
			secondPost.author = tester;
			posts.save(secondPost);

			ArrayList dbPosts = posts.findByAuthor(tester);


			assertEquals(2, dbPosts.size());

		} finally {
			posts.delete(testPost);
			posts.delete(secondPost);
			members.delete(tester);
		}
	}


	@Test
	public void testCreateOrg() {
		Organization testOrganization = new Organization();
		Organization dbOrg = new Organization();
		try {
			testOrganization.name = "New Wave";
			organizations.save(testOrganization);
			dbOrg = organizations.findByName("New Wave");
			assertNotNull(dbOrg);
		} finally {
			organizations.delete(testOrganization);
		}
	}

	@Test
	public void testOrgMembers() {
		Organization testOrganization = new Organization();
		Organization dbOrg = new Organization();
		Member testMember = new Member();
		OrganizationMember orgMember = new OrganizationMember();
		ArrayList<OrganizationMember> dbOrganizations = new ArrayList<OrganizationMember>();
		ArrayList<OrganizationMember> dbMembers = new ArrayList<OrganizationMember>();


		try{
			testOrganization.name = "Sally Beauty";
			organizations.save(testOrganization);

			testMember.firstName = "Creep";
			testMember.lastName = "Promised";
			testMember.streetAddress = "333 Promise Ave, Utoy, UT 23094";
			testMember.password = "scratchedouryourname";
			testMember.email = "canterae@gmail.com";
			members.save(testMember);

			Organization orgId = organizations.findById(testOrganization.getId());

			orgMember = new OrganizationMember(orgId, testMember);
			organizationmembers.save(orgMember);

			dbOrganizations = organizationmembers.findByMemberId(testMember.getId());
			assertNotNull(dbOrganizations);

			dbMembers = organizationmembers.findByOrganizationId(testOrganization.getId());
			assertNotNull(dbMembers);


		} finally {
			organizationmembers.delete(orgMember);
			organizations.delete(testOrganization);
			members.delete(testMember);
		}
	}

	@Test
	public void testMultipleOrgMembers() {
		Organization testOrganization = new Organization();
		Organization dbOrg = new Organization();
		Member testMember = new Member();
		Member testMemberTwo = new Member();
		OrganizationMember orgMember = new OrganizationMember();
		OrganizationMember orgMemberTwo = new OrganizationMember();
		ArrayList<OrganizationMember> dbMembers = new ArrayList<OrganizationMember>();

		try{
			testOrganization.name = "Seasick Sailors";
			organizations.save(testOrganization);

			testMember.firstName = "Unit";
			testMember.lastName = "Testing";
			testMember.streetAddress = "333 Like a Bosss Canton, GA 23094";
			testMember.password = "lehggo";
			testMember.email = "somanytests@gmail.com";
			members.save(testMember);

			testMemberTwo.firstName = "Love";
			testMemberTwo.lastName = "Starland";
			testMemberTwo.streetAddress = "237 Helpme Rd, Utoy, UT 23094";
			testMemberTwo.password = "gottawin";
			testMemberTwo.email = "lstarland@gmail.com";
			members.save(testMemberTwo);

			orgMember = new OrganizationMember(testOrganization, testMember);
			organizationmembers.save(orgMember);

			orgMemberTwo = new OrganizationMember(testOrganization, testMemberTwo);
			organizationmembers.save(orgMemberTwo);

			dbMembers = organizationmembers.findByOrganizationId(testOrganization.getId());
			assertEquals(2, dbMembers.size());

		} finally {
			organizationmembers.delete(orgMember);
			organizationmembers.delete(orgMemberTwo);
			organizations.delete(testOrganization);
			members.delete(testMember);
			members.delete(testMemberTwo);
		}
	}

	@Test
	public void testOrgMembersInvited() {
		Organization testOrganization = new Organization();
		Organization dbOrg = new Organization();
		Member testMember = new Member();
		Member anotherMember = new Member();
		OrganizationMember organizationMemberAssociation = new OrganizationMember();
		Invitation newInvite = null;
		ArrayList<OrganizationMember> dbOrganizations = new ArrayList<OrganizationMember>();
		ArrayList<OrganizationMember> dbMembers = new ArrayList<OrganizationMember>();


		try{
			testOrganization.name = "A Night to Remember - Prom";
			organizations.save(testOrganization);

			testMember.firstName = "Creep";
			testMember.lastName = "Promised";
			testMember.streetAddress= "333 Promise Ave, Utoy, UT 23094";
			testMember.password = "scratchedouryourname";
			testMember.email = "icantstos@gmail.com";
			members.save(testMember);

			newInvite = new Invitation(testMember, "bestbuy@gmail.com", testOrganization);
			invitations.save(newInvite);

			anotherMember.firstName = "Billy";
			anotherMember.lastName= "Bob";
			anotherMember.streetAddress = "488 Random Isle ";
			anotherMember.email = "bestbuy@gmail.com";
			anotherMember.password = "hi";
			members.save(anotherMember);

			try {
				if(anotherMember.email.equals(invitations.findByInvitedEmail(anotherMember.getEmail()))) {
					organizationMemberAssociation = new OrganizationMember(testOrganization, anotherMember);
					organizationmembers.save(organizationMemberAssociation);
					System.out.println("organization set");

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			assertNotNull(organizationmembers.findByOrganizationName("A Night to Remember - Prom"));

		} finally {
			invitations.delete(newInvite);
			organizationmembers.delete(organizationMemberAssociation);
			organizations.delete(testOrganization);
			members.delete(testMember);
			members.delete(anotherMember);
		}
	}


	@Test
	public void testAttendingEvent() {
		Member aEventMember = new Member();
		Event attendEvent = new Event();
		MemberEvent theMemberEvent = new MemberEvent();
		MemberEvent dbMemberEvent = new MemberEvent();

		try{
			aEventMember.firstName = "Rod";
			aEventMember.lastName = "Johnson";
			aEventMember.email = "tats2@yahoo.com";
			aEventMember.streetAddress = "679 Lemonade Way......";
			aEventMember.password = "doitall";
			members.save(aEventMember);

			attendEvent.date = "5/25/2018 ~ 2:00 PM";
			attendEvent.organizer = aEventMember;
			attendEvent.information = "Beach madness";
			attendEvent.location = "Pensacola";
			attendEvent.name = "Slip and Slides";
			events.save(attendEvent);

			theMemberEvent = new MemberEvent(aEventMember, attendEvent);
			System.out.println(theMemberEvent.event.getName() + " " + theMemberEvent.member.getFirstName());
			memberevents.save(theMemberEvent);

			dbMemberEvent = memberevents.findByEventId(attendEvent.getId());
			assertNotNull(dbMemberEvent);

		} finally {
			memberevents.delete(theMemberEvent);
			events.delete(attendEvent);
			members.delete(aEventMember);
		}
	}

	@Test
	public void testMembersAttending() {
		Member aEventMember = new Member();
		Member aEventMemberOne = new Member();
		Member aEventMemberTwo = new Member();
		Member aEventMemberThree = new Member();

		Event attendEvent = new Event();

		MemberEvent theMemberEvent = new MemberEvent();
		MemberEvent theMemberEventOne = new MemberEvent();
		MemberEvent theMemberEventTwo = new MemberEvent();
		MemberEvent theMemberEventThree = new MemberEvent();

		try {
			aEventMember.firstName = "Rod";
			aEventMember.lastName = "Johnson";
			aEventMember.email = "tatskolber@yahoo.com";
			aEventMember.streetAddress = "679 Lemonade Way......";
			aEventMember.password = "doitall";
			members.save(aEventMember);

			aEventMemberOne.firstName = "Joe";
			aEventMemberOne.lastName = "Bear";
			aEventMemberOne.email = "mechjoes@yahoo.com";
			aEventMemberOne.streetAddress = "344 Riverwalk Place Savannah, GA";
			aEventMemberOne.password = "whynot";
			members.save(aEventMemberOne);

			aEventMemberTwo.firstName = "Tasty";
			aEventMemberTwo.lastName = "Cakes";
			aEventMemberTwo.email = "tastys@yahoo.com";
			aEventMemberTwo.streetAddress = "155 Locked up Lane";
			aEventMemberTwo.password = "jailtime";
			members.save(aEventMemberTwo);

			aEventMemberThree.firstName = "Sherry";
			aEventMemberThree.lastName = "Berry";
			aEventMemberThree.email = "sbs@yahoo.com";
			aEventMemberThree.streetAddress = "766 Berry Ave";
			aEventMemberThree.password = "gimmetheberries";
			members.save(aEventMemberThree);


			attendEvent.date = "3/2/16 ~ 4:45 PM";
			attendEvent.organizer = aEventMember;
			attendEvent.information = "Live a day as a your favorite person";
			attendEvent.location = "Underground";
			attendEvent.name = "Pixar day";
			events.save(attendEvent);

			theMemberEvent = new MemberEvent(aEventMember, attendEvent);
			theMemberEventOne = new MemberEvent(aEventMemberOne, attendEvent);
			theMemberEventTwo = new MemberEvent(aEventMemberTwo, attendEvent);
			theMemberEventThree = new MemberEvent(aEventMemberThree, attendEvent);
			memberevents.save(theMemberEvent);
			memberevents.save(theMemberEventOne);
			memberevents.save(theMemberEventTwo);
			memberevents.save(theMemberEventThree);

			Iterable<MemberEvent> memberEventsFound = memberevents.findMembersByEvent(attendEvent);
			ArrayList<MemberEvent> memberList = new ArrayList<MemberEvent>();
			for (MemberEvent currentMemberEvent : memberEventsFound) {
				memberList.add(currentMemberEvent);
			}

			int alSize = memberList.size();

			assertEquals(4, alSize);

		} finally {
			memberevents.delete(theMemberEvent);
			memberevents.delete(theMemberEventOne);
			memberevents.delete(theMemberEventTwo);
			memberevents.delete(theMemberEventThree);

			events.delete(attendEvent);

			members.delete(aEventMember);
			members.delete(aEventMemberOne);
			members.delete(aEventMemberTwo);
			members.delete(aEventMemberThree);
		}
	}

	@Test
	public void testMemberAttendingMultipleEvents() {
		Member aEventMember = new Member();
		Event attendEvent = new Event();
		Event attendEventTwo = new Event();
		MemberEvent theMemberEvent = new MemberEvent();
		MemberEvent theMemberEventTwo = new MemberEvent();

		try {
			aEventMember.firstName = "Rod";
			aEventMember.lastName = "Johnson";
			aEventMember.email = "tatskolber@yahoo.com";
			aEventMember.streetAddress = "679 Lemonade Way......";
			aEventMember.password = "doitall";
			members.save(aEventMember);

			attendEvent.date = "3/2/16 ~ 4:45 PM";
			attendEvent.organizer = aEventMember;
			attendEvent.information = "Live a day as a your favorite person";
			attendEvent.location = "Underground";
			attendEvent.name = "Pixar day";
			events.save(attendEvent);

			attendEventTwo.date = "3/4/16 ~ 6:45 PM";
			attendEventTwo.organizer = aEventMember;
			attendEventTwo.information = "Tacos & Tequila";
			attendEventTwo.location = "Nacho Mamas";
			attendEventTwo.name = "Demo Day";
			events.save(attendEventTwo);

			theMemberEvent = new MemberEvent(aEventMember, attendEvent);
			theMemberEventTwo = new MemberEvent(aEventMember, attendEventTwo);

			memberevents.save(theMemberEvent);
			memberevents.save(theMemberEventTwo);

			Iterable<MemberEvent> eventsFoundForMember = memberevents.findEventsByMember(aEventMember);
			ArrayList<MemberEvent> eventList = new ArrayList<MemberEvent>();
			for (MemberEvent currentMemberEvent : eventsFoundForMember) {
				eventList.add(currentMemberEvent);
			}

			int alSize = eventList.size();

			assertEquals(2, alSize);

		} finally {
			memberevents.delete(theMemberEvent);
			memberevents.delete(theMemberEventTwo);
			events.delete(attendEvent);
			events.delete(attendEventTwo);
			members.delete(aEventMember);

		}
	}

	@Test
	public void testInvitation() {
		Member aMember = new Member();
		Organization testOrganization = new Organization();
		OrganizationMember newOrgMember = null;
		Invitation theInvite = null;
		ArrayList<Invitation> dbInvite = null;

		try {
			aMember.firstName = "Charlie";
			aMember.lastName = "Coach";
			aMember.email = "Jeepers@gmail.com";
			aMember.password = "creepers";
			aMember.streetAddress = "2 Peepers Lane Dallas, TX";
			members.save(aMember);

			testOrganization.name = "Horror Fandom";
			organizations.save(testOrganization);

			newOrgMember = new OrganizationMember(testOrganization, aMember);
			organizationmembers.save(newOrgMember);

			theInvite = new Invitation(aMember, "testemail@yahoo.com", testOrganization);
			invitations.save(theInvite);

			dbInvite = invitations.findByInvitedEmail("testemail@yahoo.com");
			assertNotNull(dbInvite);

		} finally {
			invitations.delete(dbInvite);
			organizationmembers.delete(newOrgMember);
			organizations.delete(testOrganization);
			members.delete(aMember);
		}

	}

	@Test
	public void testMultipleInvitations() {
		Member aMember = new Member();
		Member twoMember = new Member();
		Organization testOrganization = new Organization();
		OrganizationMember newOrgMember = null;
		OrganizationMember secondOrgMember = null;
		Invitation theInvite = null;
		ArrayList<Invitation> dbInvite = null;
		ArrayList<Invitation> dbInviteTwo = null;
		Invitation theSecondInvite = null;

		try {
			aMember.firstName = "Charlie";
			aMember.lastName = "Coach";
			aMember.email = "Jeepers@gmail.com";
			aMember.password = "creepers";
			aMember.streetAddress = "2 Peepers Lane Dallas, TX";
			members.save(aMember);

			twoMember.firstName = "Frank";
			twoMember.lastName = "Deletty";
			twoMember.email = "frankie@gmail.com";
			twoMember.password = "fright";
			twoMember.streetAddress = "4 Peepers Lane Dallas, TX";
			members.save(twoMember);


			testOrganization.name = "Horror Fandom";
			organizations.save(testOrganization);

			newOrgMember = new OrganizationMember(testOrganization, aMember);
			organizationmembers.save(newOrgMember);

			secondOrgMember = new OrganizationMember(testOrganization, aMember);
			organizationmembers.save(secondOrgMember);

			theInvite = new Invitation(aMember, "testemail@yahoo.com", testOrganization);
			invitations.save(theInvite);

			theSecondInvite = new Invitation(twoMember, "hello@gmail.com", testOrganization);
			invitations.save(theInvite);

			dbInvite = invitations.findByInvitedEmail("testemail@yahoo.com");
			assertNotNull(dbInvite);

			dbInviteTwo = invitations.findByInvitedEmail("hello@gmail.com");


		} finally {
			invitations.delete(dbInvite);
			invitations.delete(theSecondInvite);
			organizationmembers.delete(newOrgMember);
			organizationmembers.delete(secondOrgMember);
			organizations.delete(testOrganization);
			members.delete(aMember);
			members.delete(twoMember);
		}
	}

	@Test
	public void testOrgInfo() {
		Organization testOrganization = new Organization();
		Organization dbOrg = new Organization();
		try {
			testOrganization.name = "Ahimsa";
			organizations.save(testOrganization);

			dbOrg = organizations.findByName("Ahimsa");
			assertEquals("Ahimsa", dbOrg.getName());

		} finally {
			organizations.delete(testOrganization);
		}
	}

	@Test
	public void testMemberInfo() {
		Member testMember = new Member();
		Member dbMember = new Member();
		try {
			testMember.firstName = "Hirum";
			testMember.lastName = "Wilcox";
			testMember.streetAddress = "539 Fells Creek Rd ..";
			testMember.email= "loster@ymail.com";
			testMember.password = "newroad";
			members.save(testMember);

			dbMember = members.findOne(testMember.getId());
			assertEquals("Hirum", dbMember.getFirstName());

		} finally {
			members.delete(testMember);
		}
	}

	@Test
	public void testFindEventsByOrganizer() {
		Member testMember = new Member();
		Member secondTestMember = new Member();
		Event testEvent = new Event();
		Event secondTestEvent = new Event();
		ArrayList<Event> dbEvents = new ArrayList<>();
		ArrayList<Event> dbEventsNone = new ArrayList<>();

		try {
			testMember.firstName = "Hirum";
			testMember.lastName = "Wilcox";
			testMember.streetAddress = "539 Fells Creek Rd ..";

			testMember.email= "lostrday@ymail.com";
			testMember.password = "newroad";
			members.save(testMember);

			secondTestMember.firstName = "Gemma";
			secondTestMember.lastName = "Teller";
			secondTestMember.streetAddress = "877 Fells Creek Rd ..";
			secondTestMember.email= "gtellersad@soa.com";
			secondTestMember.password = "redwoodorginal";
			members.save(secondTestMember);

			testEvent.organizer = secondTestMember;

			testEvent.date ="9/9/2017 ~ 15:30";
			testEvent.name = "Charity Rides for children";
			testEvent.information= "Two day for children";
			testEvent.location ="Dragon Tail";
			events.save(testEvent);

			secondTestEvent.organizer = secondTestMember;
			secondTestEvent.date ="5/24/2017 ~ 13:00";
			secondTestEvent.name = "County Fair at the Square";
			secondTestEvent.information= "Fun for the whole family";
			secondTestEvent.location ="Charming Square";
			events.save(secondTestEvent);

			dbEventsNone = events.findByOrganizer(testMember);
			assertEquals(0, dbEventsNone.size());

			dbEvents = events.findByOrganizer(secondTestMember);
			assertEquals(2, dbEvents.size());


		} finally {

			events.delete(testEvent);
			events.delete(secondTestEvent);
			members.delete(testMember);
			members.delete(secondTestMember);
		}
	}

	@Test
	public void testFindEventsByOrganization() {
		Organization testOrg = new Organization();
		Member testMember = new Member();
		Member secondTestMember = new Member();
		OrganizationMember orgMember = new OrganizationMember();
		Event testEvent = new Event();
		Event secondTestEvent = new Event();
		Event notOrgEvent = new Event();

		try{
			testOrg.name="Danver Dance Group";
			organizations.save(testOrg);

			testMember.firstName = "Hirum";
			testMember.lastName = "Wilcox";
			testMember.streetAddress = "539 Fells Creek Rd ..";
			testMember.email= "lostredrd@ymail.com";
			testMember.password = "newroad";
			members.save(testMember);

			secondTestMember.firstName = "Gemma";
			secondTestMember.lastName = "Teller";
			secondTestMember.streetAddress = "877 Fells Creek Rd ..";
			secondTestMember.email= "gemmateller@soa.com";
			secondTestMember.password = "redwoodorginal";
			members.save(secondTestMember);


			orgMember = new OrganizationMember(testOrg, secondTestMember);
			organizationmembers.save(orgMember);

			testEvent.organizer = secondTestMember;
			testEvent.date = "9/9/2017 ~ 15:30";
			testEvent.name = "Charity Ride";
			testEvent.information = "Two day for children";
			testEvent.location = "Dragon Tail";
			events.save(testEvent);

			secondTestEvent.organizer = secondTestMember;
			secondTestEvent.date = "5/24/2017 ~ 13:00";
			secondTestEvent.name = "County Fair";
			secondTestEvent.information = "Fun for the whole family";
			secondTestEvent.location = "Charming Square";
			events.save(secondTestEvent);

			notOrgEvent.organizer = testMember;
			notOrgEvent.date = "6/3/2017 ~ 10:00";
			notOrgEvent.name = "BBQ";
			notOrgEvent.information = "Come and get it! ";
			notOrgEvent.location = "Cookout on Moreland";
			events.save(notOrgEvent);

			Iterable<OrganizationMember> allOrgMembers = organizationmembers.findMembersByOrganization(testOrg);
			List<Event> orgMemberEventList = new ArrayList<>();
			for (OrganizationMember thisOrgMember : allOrgMembers) {
				orgMemberEventList.addAll(events.findByOrganizer(thisOrgMember.getMember()));
			}
			assertEquals(2, orgMemberEventList.size());


		} finally {
			organizationmembers.delete(orgMember);
			organizations.delete(testOrg);
			events.delete(testEvent);
			events.delete(secondTestEvent);
			events.delete(notOrgEvent);
			members.delete(testMember);
			members.delete(secondTestMember);
		}
	}

	@Test
	public void testFindPostsByOrganization() {
		Organization testOrg = new Organization();
		Member testMember = new Member();
		Member secondTestMember = new Member();
		OrganizationMember orgMember = new OrganizationMember();

		Post onePost = new Post();
		Post twoPost = new Post();
		Post threePost = new Post();

		try{
			testOrg.name="Wah wah";
			organizations.save(testOrg);

			testMember.firstName = "Hirum";
			testMember.lastName = "Wilcox";
			testMember.streetAddress = "539 Fells Creek Rd ..";
			testMember.email= "roadd@smail.com";
			testMember.password = "newroad";
			members.save(testMember);

			onePost.title = "Famous Songs";
			onePost.date = "3/4/16 12:00";
			onePost.body = "Come sail away with me";
			onePost.author = testMember;
			onePost.organization = testOrg;

			posts.save(onePost);

			secondTestMember.firstName = "Gemma";
			secondTestMember.lastName = "Teller";
			secondTestMember.streetAddress = "877 Fells Creek Rd ..";
			secondTestMember.email= "gellerrosa@toasty.com";
			secondTestMember.password = "redwoodorginal";
			members.save(secondTestMember);

			twoPost.author = secondTestMember;
			twoPost.date = "2/20/2018 16:00";
			twoPost.title = "Defensive Drivin for AAA";
			twoPost.body="Car accidents hurt";
			twoPost.organization = testOrg;
			posts.save(twoPost);

			threePost.author = secondTestMember;
			threePost.date = "4/5/2018 16:00";
			threePost.title = "SOS";
			threePost.body="Naming conventions are sometimes hard";
			threePost.organization = testOrg;
			posts.save(threePost);

			orgMember = new OrganizationMember(testOrg, secondTestMember);
			organizationmembers.save(orgMember);

//			Iterable<OrganizationMember> allOrgMembers = organizationmembers.findMembersByOrganization(testOrg);
//			List<Post> orgMemberPostList = new ArrayList<>();
//			for (OrganizationMember thisOrgMember: allOrgMembers){
//				orgMemberPostList.addAll(posts.findByAuthor(thisOrgMember.getMember()));
//			}

			List<Post> orgMemberPostListTwo = posts.findByOrganization(testOrg);
			assertEquals(3, orgMemberPostListTwo.size());

		} finally {
			organizationmembers.delete(orgMember);
			posts.delete(onePost);
			posts.delete(twoPost);
			posts.delete(threePost);
			organizations.delete(testOrg);
			members.delete(testMember);
			members.delete(secondTestMember);
		}
	}

	@Test
	public void testPostsByAllMembersOrgs() {
		Organization testOrg = new Organization();
		Organization secondOrg = new Organization();
		Member testMember = new Member();
		Member secondTestMember = new Member();
		OrganizationMember orgMember = new OrganizationMember();
		OrganizationMember secondOrgMember = new OrganizationMember();
		OrganizationMember secondOrgMemberTest = new OrganizationMember();
		Post onePost = new Post();
		Post twoPost = new Post();
		Post threePost = new Post();
		Post fourPost = new Post();

		try{
			testOrg.name="Rider";
			organizations.save(testOrg);

			secondOrg.name= "Under the Sea";
			organizations.save(secondOrg);

			testMember.firstName = "Hirums";
			testMember.lastName = "Wilcox";
			testMember.streetAddress = "539 Fells Creek Rd ..";
			testMember.email= "roads@yahoo.com";
			testMember.password = "newroad";
			members.save(testMember);

			onePost.title = "Famous Songs by Miya";
			onePost.date = "3/4/16 12:00";
			onePost.body ="Come sail away with me";
			onePost.author = testMember;
			onePost.organization = testOrg;
			posts.save(onePost);

			secondTestMember.firstName = "Jax";
			secondTestMember.lastName = "Teller";
			secondTestMember.streetAddress = "877 Fells Creek Rd ..";
			secondTestMember.email= "jaxa@yahoo.com";
			secondTestMember.password = "redwoodorginal";
			members.save(secondTestMember);

			twoPost.author = testMember;
			twoPost.date = "2/20/2018 16:00";
			twoPost.title = "Defensive Drive You";
			twoPost.body="Car accidents hurt";
			twoPost.organization = testOrg;
			posts.save(twoPost);

			threePost.author = secondTestMember;
			threePost.date = "4/5/2018 16:00";
			threePost.title = "Guess the code";
			threePost.body="Naming conventions are sometimes hard";
			threePost.organization = secondOrg;
			posts.save(threePost);

			fourPost.author = secondTestMember;
			fourPost.date = "4/5/2018 16:00";
			fourPost.title = "Why not cleaning with dawn";
			fourPost.body="it's clean";
			fourPost.organization = secondOrg;
			posts.save(fourPost);

			orgMember = new OrganizationMember(testOrg, secondTestMember);
			organizationmembers.save(orgMember);

			secondOrgMember = new OrganizationMember(secondOrg, secondTestMember);
			organizationmembers.save(secondOrgMember);

			secondOrgMemberTest = new OrganizationMember(secondOrg, testMember);
			organizationmembers.save(secondOrgMemberTest);


			List <Post> orgMemberPostList = new ArrayList<>();
			ArrayList<OrganizationMember> memberOrgs = organizationmembers.findByMemberId(secondTestMember.getId());
			int sizeOfAL = memberOrgs.size();
			if (sizeOfAL == 1){
				orgMemberPostList = posts.findByOrganization(testOrg);
			} else {
				for (OrganizationMember currentOrgMember: memberOrgs){
					Organization currentOrg =  currentOrgMember.getOrganization();
					orgMemberPostList.addAll(posts.findByOrganization(currentOrg));
				}
			}

			assertEquals(4, orgMemberPostList.size());

		}finally {
			posts.delete(onePost);
			posts.delete(twoPost);
			posts.delete(threePost);
			posts.delete(fourPost);
			organizationmembers.delete(orgMember);
			organizationmembers.delete(secondOrgMember);
			organizationmembers.delete(secondOrgMemberTest);
			organizations.delete(testOrg);
			organizations.delete(secondOrg);
			members.delete(testMember);
			members.delete(secondTestMember);
		}
	}

	@Test
	public void testEventsByAllMembersOrgs() {
		Organization testOrg = new Organization();
		Organization secondOrg = new Organization();
		Member testMember = new Member();
		Member secondTestMember = new Member();
		OrganizationMember orgMember = new OrganizationMember();
		OrganizationMember secondOrgMember = new OrganizationMember();
		OrganizationMember secondOrgMemberTest = new OrganizationMember();
		Event oneEvent = new Event();
		Event twoEvent = new Event();
		Event threeEvent = new Event();
		Event fourEvent = new Event();

		try{
			testOrg.name="Dog Talking with Rebecca";
			organizations.save(testOrg);

			secondOrg.name= "Herp Fight Club";
			organizations.save(secondOrg);

			testMember.firstName = "Hirums";
			testMember.lastName = "Wilcox";
			testMember.streetAddress = "539 Fells Creek Rd ..";
			testMember.email= "this@ymail.com";
			testMember.password = "newroad";
			members.save(testMember);

			oneEvent.date = "3/14/16 12:00";
			oneEvent.name = "Pie Day Forevers";
			oneEvent.location = "TIY";
			oneEvent.information = "Bring a pie";
			oneEvent.organizer= testMember;
			oneEvent.organization = testOrg;
			events.save(oneEvent);

			secondTestMember.firstName = "Jax";
			secondTestMember.lastName = "Teller";
			secondTestMember.streetAddress = "877 Fells Creek Rd ..";
			secondTestMember.email= "thejs@soas.com";
			secondTestMember.password = "redwoodorginal";
			members.save(secondTestMember);

			twoEvent.organizer = testMember;
			twoEvent.date = "2/20/2018 16:00";
			twoEvent.name = "Wands";
			twoEvent.information="Great deals on the locations of your dreams";
			twoEvent.organization = testOrg;
			twoEvent.location = "5 Points";
			events.save(twoEvent);

			threeEvent.organizer = secondTestMember;
			threeEvent.date = "4/5/2018 16:00";
			threeEvent.name = "April Heyday";
			threeEvent.information="Past fools day oh well";
			threeEvent.location= "TBDs";
			threeEvent.organization = secondOrg;
			events.save(threeEvent);

			fourEvent.organizer = secondTestMember;
			fourEvent.date = "6/9/2018 16:00";
			fourEvent.name = "Summer Run";
			fourEvent.information="Run";
			fourEvent.location = "Kennesaw Mountain";
			fourEvent.organization = secondOrg;
			events.save(fourEvent);

			orgMember = new OrganizationMember(testOrg, secondTestMember);
			organizationmembers.save(orgMember);

			secondOrgMember = new OrganizationMember(secondOrg, secondTestMember);
			organizationmembers.save(secondOrgMember);

			secondOrgMemberTest = new OrganizationMember(secondOrg, testMember);
			organizationmembers.save(secondOrgMemberTest);


			List <Event> orgMemberEventList = new ArrayList<>();
			ArrayList<OrganizationMember> memberOrgs = organizationmembers.findByMemberId(secondTestMember.getId());
			int sizeOfAL = memberOrgs.size();
			if (sizeOfAL == 1){
				orgMemberEventList = events.findByOrganization(testOrg);
			} else {
				for (OrganizationMember currentOrgMember: memberOrgs){
					Organization currentOrg =  currentOrgMember.getOrganization();
					orgMemberEventList.addAll(events.findByOrganization(currentOrg));
				}
			}

			assertEquals(4, orgMemberEventList.size());

		}finally {
			events.delete(oneEvent);
			events.delete(twoEvent);
			events.delete(threeEvent);
			events.delete(fourEvent);
			organizationmembers.delete(orgMember);
			organizationmembers.delete(secondOrgMember);
			organizationmembers.delete(secondOrgMemberTest);
			organizations.delete(testOrg);
			organizations.delete(secondOrg);
			members.delete(testMember);
			members.delete(secondTestMember);
		}
	}

	@Test
	public void testViewPostsAcrossOrgs() {
		Organization firstTestOrg = new Organization();
		Organization secondTestOrg = new Organization();
		Member firstTestMember = new Member();
		Member secondTestMember = new Member();
		OrganizationMember firstorgMember = new OrganizationMember();
		OrganizationMember secondOrgMember = new OrganizationMember();
		Post onePost = new Post();
		Post twoPost = new Post();
		Post threePost = new Post();
		Post fourPost = new Post();

		try {
			firstTestMember.firstName = "Romeo";
			firstTestMember.lastName = "Montague";
			firstTestMember.email = "rm@play.com";
			firstTestMember.password = "password";
			firstTestMember.streetAddress = "Fair Verona";
			firstTestMember.photoURL = "what is internet?";
			members.save(firstTestMember);

			secondTestMember.firstName = "Juliet";
			secondTestMember.lastName = "Capulet";
			secondTestMember.email = "jc@play.com";
			secondTestMember.password = "password";
			secondTestMember.streetAddress = "Fair Verona";
			secondTestMember.photoURL = "yes, what is internet?";
			members.save(secondTestMember);

			firstTestOrg.name = "The Montagues";
			organizations.save(firstTestOrg);

			secondTestOrg.name = "The Capulets";
			organizations.save(secondTestOrg);

			firstorgMember = new OrganizationMember(firstTestOrg, firstTestMember);
			organizationmembers.save(firstorgMember);

			secondOrgMember = new OrganizationMember(secondTestOrg, secondTestMember);
			organizationmembers.save(secondOrgMember);

			onePost.date = "today";
			onePost.title = "Title 1";
			onePost.body = "This is my body";
			onePost.author = firstTestMember;
			onePost.organization = firstTestOrg;
			posts.save(onePost);

			twoPost.date = "today";
			twoPost.title = "Title 2";
			twoPost.body = "This is my second body";
			twoPost.author = firstTestMember;
			twoPost.organization = firstTestOrg;
			posts.save(twoPost);

			threePost.date = "today";
			threePost.title = "Title 3";
			threePost.body = "This is my third body";
			threePost.author = secondTestMember;
			threePost.organization = secondTestOrg;
			posts.save(threePost);

			fourPost.date = "today";
			fourPost.title = "Title 4";
			fourPost.body = "This is my fourth body";
			fourPost.author = secondTestMember;
			fourPost.organization = secondTestOrg;
			posts.save(fourPost);

			Iterable<Post> postsAcrossOrgs = posts.findAll();

			Long something = postsAcrossOrgs.spliterator().getExactSizeIfKnown();

			assertTrue(something == 4);

		} finally {
			posts.delete(onePost);
			posts.delete(twoPost);
			posts.delete(threePost);
			posts.delete(fourPost);
			organizationmembers.delete(firstorgMember);
			organizationmembers.delete(secondOrgMember);
			organizations.delete(firstTestOrg);
			organizations.delete(secondTestOrg);
			members.delete(firstTestMember);
			members.delete(secondTestMember);
		}
	}

	@Test
	public void testPostsByAllMembersOrgsOrdered() {
		Organization secondOrg = new Organization();
		Member testMember = new Member();
		Member secondTestMember = new Member();
		OrganizationMember orgMember = new OrganizationMember();
		OrganizationMember secondOrgMember = new OrganizationMember();
		OrganizationMember secondOrgMemberTest = new OrganizationMember();
		Post onePost = new Post();
		Post twoPost = new Post();
		Post threePost = new Post();
		Post sameDateDifferentTime = new Post();
		Post sameMonthDifferentDate = new Post();

		try{
			secondOrg.name= "Jamrock";
			organizations.save(secondOrg);

			testMember.firstName = "Jessica";
			testMember.lastName = "Wilcox";
			testMember.streetAddress = "539 Fells Creek Rd ..";
			testMember.email= "roads@yahoo.com";
			testMember.password = "newroad";
			members.save(testMember);

			onePost.title = "Live Code Session";
			onePost.date = "1994-11-05T08:15:30-05:00";
			onePost.body ="Come sail away with me";
			onePost.author = testMember;
			onePost.organization = secondOrg;
			posts.save(onePost);

			secondTestMember.firstName = "Cherry";
			secondTestMember.lastName = "Teller";
			secondTestMember.streetAddress = "877 Fells Creek Rd ..";
			secondTestMember.email= "jaxa@yahoo.com";
			secondTestMember.password = "redwoodorginal";
			members.save(secondTestMember);

			twoPost.author = testMember;
			twoPost.date = "2016-11-05T09:25:30-05:00";
			twoPost.title = "Brice's Adventure";
			twoPost.body="Car accidents hurt";
			twoPost.organization = secondOrg;
			posts.save(twoPost);

			threePost.author = secondTestMember;
			threePost.date = "2018-01-03T09:25:30-05:00";
			threePost.title = "Solve the puzzle";
			threePost.body="Naming conventions are sometimes hard";
			threePost.organization = secondOrg;
			posts.save(threePost);

			sameDateDifferentTime.title = "Same date dif time";
			sameDateDifferentTime.author= testMember;
			sameDateDifferentTime.organization= secondOrg;
			sameDateDifferentTime.body= "so we meet again";
			sameDateDifferentTime.date= "2018-01-03T06:20:30-03:00";
			posts.save(sameDateDifferentTime);

			sameMonthDifferentDate.title= "Fizz";
			sameMonthDifferentDate.body= "Buzz";
			sameMonthDifferentDate.organization= secondOrg;
			sameMonthDifferentDate.author= testMember;
			sameMonthDifferentDate.date= "2016-03-03T06:20:30-07:00";
			posts.save(sameMonthDifferentDate);


			secondOrgMember = new OrganizationMember(secondOrg, secondTestMember);
			organizationmembers.save(secondOrgMember);

			secondOrgMemberTest = new OrganizationMember(secondOrg, testMember);
			organizationmembers.save(secondOrgMemberTest);


			ArrayList<Post> orgPosts= posts.findByOrganizationOrderByDateAsc(secondOrg);
//no assert here b/c i wanted to test myself first - for this to work we need to move to iso....on java side
			for (Post currentPost: orgPosts) {
				System.out.println(currentPost.getDate());
			}

		}finally {
			posts.delete(onePost);
			posts.delete(twoPost);
			posts.delete(threePost);
			posts.delete(sameDateDifferentTime);
			posts.delete(sameMonthDifferentDate);
			organizationmembers.delete(orgMember);
			organizationmembers.delete(secondOrgMember);
			organizationmembers.delete(secondOrgMemberTest);
			organizations.delete(secondOrg);
			members.delete(testMember);
			members.delete(secondTestMember);
		}
	}


	@Test
	public void testFindMembersByOrg(){
		Member memberOne = new Member();
		Member memberTwo = new Member();
		Member memberThree = new Member();

		Organization oneOrg = new Organization();
		Organization twoOrg = new Organization();

		OrganizationMember orgMemberOne = new OrganizationMember();
		OrganizationMember orgMemberTwo = new OrganizationMember();
		OrganizationMember orgMemberThree = new OrganizationMember();

		try{

			memberOne.firstName= "Frank";
			memberOne.lastName= "Nike";
			memberOne.streetAddress= "192 Ocean Way....";
			memberOne.photoURL= "google.com";
			memberOne.email= "nikes@frank.com";
			memberOne.password= "rings";
			members.save(memberOne);

			memberTwo.firstName= "Alfred";
			memberTwo.lastName= "Enoch";
			memberTwo.email= "ae@law.com";
			memberTwo.photoURL= "my photo";
			memberTwo.streetAddress=" 900 Mur way ";
			memberTwo.password= "password";
			members.save(memberTwo);

			memberThree.firstName= "Roman";
			memberThree.lastName= "Rev";
			memberThree.photoURL= "this is a photo url";
			memberThree.streetAddress= " 85 Letty Way ";
			memberThree.password= "pass";
			memberThree.email= "romanr@gmail.com";
			members.save(memberThree);

			oneOrg.name= "NaanStop";
			organizations.save(oneOrg);

			twoOrg.name= "Greene's";
			organizations.save(twoOrg);

			orgMemberOne= new OrganizationMember(oneOrg, memberOne);
			organizationmembers.save(orgMemberOne);

			orgMemberTwo= new OrganizationMember(oneOrg, memberTwo);
			organizationmembers.save(orgMemberTwo);

			orgMemberThree = new OrganizationMember(oneOrg, memberThree);
			organizationmembers.save(orgMemberThree);

			ArrayList<OrganizationMember> listOMembers = new ArrayList<>();
			listOMembers = organizationmembers.findMembersByOrganization(twoOrg);
			assertEquals (listOMembers.size(), 0);

			ArrayList<OrganizationMember> listOfRealMembers = new ArrayList<>();
			listOfRealMembers = organizationmembers.findMembersByOrganization(oneOrg);
			assertNotNull (listOfRealMembers);

		} finally {
			organizationmembers.delete(orgMemberOne);
			organizationmembers.delete(orgMemberTwo);
			organizationmembers.delete(orgMemberThree);
			organizations.delete(oneOrg);
			organizations.delete(twoOrg);
			members.delete(memberOne);
			members.delete(memberTwo);
			members.delete(memberThree);

		}

	}

	@Test
	public void testEditMemberProfile(){
		Member thisMember = new Member();
		Member dbMember = new Member();
		Member anotherDbMember = new Member();

		try{
			thisMember.firstName= "Morty";
			thisMember.lastName= "Miguel";
			thisMember.streetAddress= "";
			thisMember.email= "mort@gmail.com";
			thisMember.password= "mortality";
			thisMember.photoURL= " default";
			members.save(thisMember);


			dbMember= members.findFirstByEmail(thisMember.getEmail());
			assertEquals(dbMember.firstName, "Morty");

			dbMember.password= "ichangeditwoooo";
			members.save(dbMember);

			anotherDbMember= members.findFirstByEmail("mort@gmail.com");

			assertEquals(anotherDbMember.id, dbMember.id, thisMember.id);

		} finally {
			members.delete(thisMember);

		}



	}
}

