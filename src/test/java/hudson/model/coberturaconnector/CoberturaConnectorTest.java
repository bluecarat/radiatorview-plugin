package hudson.model.coberturaconnector;

import java.io.IOException;

import javax.servlet.ServletException;

import hudson.model.FreeStyleProject;
import hudson.model.Hudson;
import hudson.model.Run;

import org.easymock.EasyMock;
import org.jvnet.hudson.test.HudsonTestCase;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * Test for {@link CoberturaConnector}
 * 
 * @author Chris Stahlhut
 * 
 */
public class CoberturaConnectorTest extends HudsonTestCase {

	/**
	 * Name of a test-project.
	 */
	private static final String PROJECT_NAME = "TestProject";

	/**
	 * The test-project.
	 */
	private FreeStyleProject project;

	/**
	 * The connector to test.
	 */
	private CoberturaConnector connector;

	/**
	 * {@inheritDoc}
	 */
	protected void setUp() throws Exception {
		super.setUp();
		project = (FreeStyleProject) Hudson.getInstance().createProject(
				FreeStyleProject.class, PROJECT_NAME);
		CoberturaService serviceMock = EasyMock
				.createMock(CoberturaService.class);
		connector = new CoberturaConnector(project, serviceMock);
	}

	/**
	 * Test the method {@link CoberturaConnector#getTestCoverage()}.
	 */
	public void testGetTestCoverage() {

		// without any builds
		assertEquals(-1f, connector.getTestCoverage());

		prepareMocks();
		assertEquals(50f, connector.getTestCoverage());
	}

	private void prepareMocks() {
		CoberturaService serviceMock = EasyMock
				.createMock(CoberturaService.class);

		float firstCoverageResult = 50;
		float secondCoverageResult = 20;

		EasyMock.expect(
				serviceMock.getTestLineCoverageResult((Run<?, ?>) EasyMock
						.anyObject())).andReturn(firstCoverageResult);
		EasyMock.expect(
				serviceMock.getTestLineCoverageResult((Run<?, ?>) EasyMock
						.anyObject())).andReturn(secondCoverageResult);
		EasyMock.replay(serviceMock);
		connector = new CoberturaConnector(project, serviceMock);

		StaplerRequest req = EasyMock.createMock(StaplerRequest.class);
		EasyMock.expect(req.getParameter(EasyMock.contains("delay"))).andReturn(
				"0sec");
		EasyMock.expect(req.getParameter(EasyMock.contains("delay"))).andReturn(
		"0sec");
		EasyMock.replay(req);
		StaplerResponse rsp = EasyMock.createMock(StaplerResponse.class);
//		EasyMock.replay(rsp);
		
		try {
			for (int i = 0; i < 2; i++) {
				project.doBuild(req, rsp);
				while (!project.isBuilding()) {
				}
				while (project.isBuilding()) {
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			fail("Project could not be build");
		}
	}

	/**
	 * Test the method {@link CoberturaConnector#getTestCoverageDiff()}.
	 */
	public void testGetTestCoverageDiff() {

		// without builds
		assertEquals(0f, connector.getTestCoverageDiff());

		prepareMocks();

		assertEquals(30f, connector.getTestCoverageDiff());
	}
}
