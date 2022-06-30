package poll;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class PollTest {
	
	Poll<String> test = new GeneralPollImpl<String>();

	// test Strategy
	// ²âÊÔpollµÄ´´½¨

	@Test
	public void test() {
		assertNotEquals(Poll.create(),null);
	}
}
