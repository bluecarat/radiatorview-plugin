package hudson.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

import static org.junit.Assert.*;


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
}
