package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityAppApplicationTests {

	@Autowired
	MemberRepository members;


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
		tester.firstName = "Rebecca";
		tester.lastName = "Bearden-Tellez";
		tester.email = "r@gmail.com";
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

		} catch (DataIntegrityViolationException exception) {
			thrown = true;
		}
		assertTrue(thrown);
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


//	@Test
//	public void testCreateEvent() {
//
//	}
//
//	@Test
//	public void testEditEvent() {
//
//	}
//
//	@Test
//	public void testEmptyFieldForEvent() {
//
//	}
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

