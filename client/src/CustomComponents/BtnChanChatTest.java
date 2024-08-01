package CustomComponents;

import static org.junit.Assert.assertEquals;

import javax.annotation.processing.Generated;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import View.CaroBoard;
import junit.framework.Assert;

@Generated(value = "org.junit-tools-1.1.0")
public class BtnChanChatTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	private BtnChanChat createTestSubject() {
		return new BtnChanChat();
	}

	@Test
	public void testCase1() throws Exception {
		BtnChanChat testSubject;
		Boolean result;

		// default test
		testSubject = createTestSubject();
		testSubject.Click();
		result = testSubject.getStatus();
		assertEquals(result, false);
	}
	
//	@Test
//	public void testCase2() throws Exception {
//		BtnChanChat testSubject;
//		Boolean result;
//
//		// default test
//		testSubject = createTestSubject();
//		testSubject.Click();
//		testSubject.Click();
//		result = testSubject.getStatus();
//		assertEquals(result, true);
//	}
	
//	@Test
//	public void testCase3() throws Exception {
//		Entry.Entry.caroboard = new CaroBoard();
//		Boolean result;
//
//		// default test
//		Entry.Entry.caroboard.rdsbtnChnChat.Click();
//		
//		result = Entry.Entry.caroboard.printMessage("TESTING");
//		
//		assertEquals(result, false);
//	}
	
}