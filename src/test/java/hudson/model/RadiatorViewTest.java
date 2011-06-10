package hudson.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

public class RadiatorViewTest extends HudsonTestCase {

	private RadiatorView view;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		view = new RadiatorView("testview");
	}
	
	/**
     * Tests {@link RadiatorView#getLastUpdateTime()}.
     */
	@Test
    public void testGetLastUpdateTime() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertEquals(formatter.format(new Date()), view.getLastUpdateTime());
    }
	
	@Test
	public void testIsAfterJava5() {
		
		// can only be true, because this plugin cannot be build with Java < 1.6
		assertTrue(view.isAvailableHarddiskSpaceShowable());
		
	}
}
