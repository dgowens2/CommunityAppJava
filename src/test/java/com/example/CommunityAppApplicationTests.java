package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

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
		tester.firstName = "Tu Wong";
		tester.lastName = "Foo";
		tester.email = "taka@gmail.com";
		tester.password = "JavaBeanForLife";
		tester.streetAddress = "543 TIY Drive, Atlanta, GA 30102";
		members.save(tester);
		try {
			Member testerTwo = new Member();
			testerTwo.firstName = "Rebecca";
			testerTwo.lastName = "Bearden-Tellez";
			testerTwo.email = "r@gmail.com";
			testerTwo.password = "JavaBeanForLife";
			testerTwo.streetAddress = "543 TIY Drive, Atlanta, GA 30102";
			members.save(testerTwo);
		} catch (Exception exception) {
			thrown = true;
		}
		assertTrue(thrown);
		members.delete(tester);
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
	public void testFindEventByName() { // waiting on updated event class
		Event newEvent = new Event();
		Event dbEvent = new Event ();

		Member newMember = new Member();

		try {
			newMember.lastName = "Adding a member";
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
		Event dbEvent = new Event ();
		try {
			newEvent.name = "Party Hardy";
			newEvent.location = "West End";
			newEvent.information = "Dinner fo thieves";
			newEvent.date = "5/30/2017 ~ 1:30 PM";
			events.save(newEvent);
			dbEvent = events.findOne(newEvent.getId());
			assertNotNull(dbEvent);

		} finally {
			events.delete(newEvent);
		}
	}

	@Test
	public void testEditEvent() {
		Event newEvent = new Event();
		Event dbEvent = new Event ();
		try {
			newEvent.name = "Party Hardy";
			newEvent.location = "West End";
			newEvent.information = "Dinner fo thieves";
			newEvent.date = "5/30/2017 ~ 1:30 PM";
			events.save(newEvent);

			dbEvent = events.findOne(newEvent.getId());
			dbEvent.name = "My new party";
			events.save(dbEvent);

			assertEquals(dbEvent.getId(), newEvent.getId());
		} finally {
			events.delete(newEvent);
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
//
//	@Test
//	public void testFindEventById() {
//
//	}
//
//	@Test
//	public void testCreatePost() {
//
//	}
//
//	@Test
//	public void testEditPost() {
//
//	}
//
//	@Test
//	public void testFindPostById() {
//
//	}
//
//	@Test
//	public void testFindPostByUser() {
//
//	}
//
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
//	@Test
//	public void testOrgMembers() {
//
//	}
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

