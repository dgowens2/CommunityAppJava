package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

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
	public void testCreateMember(){
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

		} finally{
			members.delete(newMember);
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
		}finally {
			assertTrue(thrown);
			members.delete(tester);
		}

//		members.delete(testerTwo);
	}

	@Test
	public void testInsertingAnEmptyValueIntoUser () throws PSQLException {
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
		Event dbEvent = new Event ();

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
		try {
			testOrganizer.firstName = "Q";
			testOrganizer.lastName = "Wes";
			testOrganizer.email = "quests@gmail.com";
			testOrganizer.streetAddress = "222 Lucky Lane";
			testOrganizer.password = "eastside";
			members.save(testOrganizer);

			newEvent.name = "Party Hardies people";
			newEvent.location = "West End";
			newEvent.date = "4/5/2017";
			newEvent.information = "Dinner fo thieves";
			newEvent.organizer = testOrganizer;

			events.save(newEvent);
			dbEvent = events.findOne(newEvent.getId());
			assertNotNull(dbEvent);

		} finally {
			events.delete(newEvent);
			members.delete(testOrganizer);


		}
	}

	@Test
	public void testEditEvent() {
		Event newEvent = new Event();
		Event dbEvent = new Event ();
		Member tMember = new Member();
		try {

			tMember.firstName= "Wally";
			tMember.lastName = "Willy";
			tMember.email ="theo@yahoo.com";
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
		try{
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
		Event dbEvent = new Event ();
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

		try{
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

		try{
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


			assertEquals(2,dbPosts.size());

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
		try{
			testOrganization.name = "Lifeline";
			organizations.save(testOrganization);
			dbOrg = organizations.findByName("Lifeline");
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
		OrganizationMember orgMember = null;
		ArrayList<OrganizationMember> dbOrganizations = new ArrayList<OrganizationMember>();
		ArrayList<OrganizationMember> dbMembers = new ArrayList<OrganizationMember>();


		try{
			testOrganization.name = "Peace Corps";
			organizations.save(testOrganization);

			testMember.firstName = "Creep";
			testMember.lastName = "Promised";
			testMember.streetAddress= "333 Promise Ave, Utoy, UT 23094";
			testMember.password = "scratchedouryourname";
			testMember.email = "canterase@gmail.com";
			members.save(testMember);

			orgMember = new OrganizationMember(testOrganization, testMember);
			organizationmembers.save(orgMember);

			dbOrganizations = organizationmembers.findByMemberId(testMember.getId());
			assertNotNull(dbOrganizations);

			dbMembers =  organizationmembers.findByOrganizationId(testOrganization.getId());
			assertNotNull(dbMembers);


		} finally {
			organizationmembers.delete(orgMember);
			organizations.delete(testOrganization);
			members.delete(testMember);
		}
	}

	@Test
	public void testAttendingEvent() {
		Member aEventMember = new Member();
		Event attendEvent = new Event();
		MemberEvent theMemberEvent = new MemberEvent();
		MemberEvent dbMemberEvent = new MemberEvent();;
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

		try{
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


			attendEvent.date ="3/2/16 ~ 4:45 PM";
			attendEvent.organizer = aEventMember;
			attendEvent.information = "Live a day as a your favorite person";
			attendEvent.location = "Underground";
			attendEvent.name = "Pixar day";
			events.save(attendEvent);

			theMemberEvent = new MemberEvent(aEventMember, attendEvent);
			theMemberEventOne= new MemberEvent(aEventMemberOne, attendEvent);
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
	public void testInvitation() {
		Member aMember = new Member();
		Organization testOrganization = new Organization();
		OrganizationMember newOrgMember = null;
		Invitation theInvite = null;
		Invitation dbInvite = null;

		try{
			aMember.firstName = "Charlie";
			aMember.lastName = "Coach";
			aMember.email = "Jeepers@gmail.com";
			aMember.password = "creepers";
			aMember.streetAddress = "2 Peepers Lane Dallas, TX";
			members.save(aMember);

			testOrganization.name = "Horror Fandom";
			organizations.save(testOrganization);

			newOrgMember =  new OrganizationMember(testOrganization, aMember);
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
		Invitation dbInvite = null;
		Invitation dbInviteTwo = null;
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

//
//
//	@Test
//	public void testOrgInfo() {
//
//	}
//
//	@Test
//	public void testMemberInfo() {
//	Member testMember = new Member();
//	Member dbMember = new Member();
//		try{
//			testMember.firstName = "Hirum";
//			testMember.lastName = "Wilcox";
//			testMember.streetAddress = "539 Fells Creek Rd ..";
//			testMember.email= "lostrd@ymail.com";
//			testMember.password = "newroad";
//			members.save(testMember);
//
//
//
//		}finally {
//			members.delete(testMember);
//		}
//	}
}

