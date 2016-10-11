package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
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
//			testMember.firstName = "Tam Tam";
//			testMember.lastName = "Bam";
//			testMember.streetAddress = "3824 Winder Rd";
//			testMember.email = "red@gmail.com";
//			testMember.password = "lucky";
//			members.save(testMember);

			newEvent.name = "Party Hardy";
			newEvent.location = "West End";
			newEvent.information = "Dinner fo thieves";
			newEvent.date = "5/30/2017 ~ 1:30 PM";
//			newEvent.organizer = testMember;
			events.save(newEvent);

			dbEvent = events.findById(newEvent.getId());
			dbEvent.name = "My new party";
			events.save(dbEvent);

			assertEquals(dbEvent.getId(), newEvent.getId());
		} finally {
			events.delete(newEvent);
//			members.delete(testMember);
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
			testPost.member = tester;
			posts.save(testPost);
			dbPosts = posts.findByMember(tester);

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
			testPost.member = tester;
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
			newPost.member = testMember;
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
			testPost.member = tester;
			posts.save(testPost);

			secondPost.title = "Waterfalls";
			secondPost.body = "Don't go chasing";
			secondPost.date = "6/4/2020 ~ 7:30 PM";
			secondPost.member = tester;
			posts.save(secondPost);

			ArrayList dbPosts = posts.findByMember(tester);


			assertEquals(2,dbPosts.size());

		} finally {
			posts.delete(testPost);
			posts.delete(secondPost);
			members.delete(tester);
		}
	}


	@Test
	public void testOrgMembers() {
		


	}

//	@Test
//	public void testAttendingEvent() {
//
//	}
//
//	@Test
//	public void testUsersAttending() {
//
//	}
//
//
//	@Test
//	public void testOrgInfo() {
//
//	}
//
//	@Test
//	public void testMemberInfo() {
//
//	}
}

