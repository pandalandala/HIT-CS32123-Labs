package poll;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class PollTest {
	
	Poll<String> test = new GeneralPollImpl<String>();

	// test Strategy
	// ����poll�Ĵ���

	@Test
	public void test() {
		assertNotEquals(Poll.create(),null);
	}
}
