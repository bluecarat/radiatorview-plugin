package hudson.model.coberturaconnector;

import hudson.model.Job;
import hudson.model.Run;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class connects to the Hudson Cobertura Plugin and organizes the current
 * line coverage and the difference between the last two builds.
 * 
 * @author Chris Stahlhut
 * 
 */
class CoberturaConnector {

	/**
	 * Logger of this class.
	 */
	private static final Logger LOGGER = Logger
			.getLogger(CoberturaConnector.class.toString());

	/**
	 * The service to use.
	 */
	private CoberturaService service;;

	/**
	 * The Job to work with.
	 */
	private Job<?, ?> job;

	/**
	 * Simple constructor.
	 * 
	 * @param job
	 *            to get information about the test coverage from
	 */
	CoberturaConnector(Job<?, ?> job, CoberturaService service) {
		this.job = job;
		this.service = service;
	}

	/**
	 * Takes the current line coverage of the job.
	 * 
	 * @return the coverage in percent
	 */
	float getTestCoverage() {
		Run<?, ?> build = job.getLastBuild();

		float lineRatio = getLineCoverage(build);

		return lineRatio;
	}

	/**
	 * Takes a Job and calculates the difference of test coverage between the
	 * last 2 builds.
	 * 
	 * @return the difference in percent
	 */
	float getTestCoverageDiff() {
		Run<?, ?> lastRun = job.getLastBuild();
		float currentCoverage = getLineCoverage(lastRun);
		float coverageBeforeLastRun = 0;
		if (lastRun != null) {
			Run<?, ?> runBeforeLastRun = lastRun.getPreviousBuild();
			coverageBeforeLastRun = getLineCoverage(runBeforeLastRun);
		}
		float difference = currentCoverage - coverageBeforeLastRun;
		// there cannot be any difference, if the coverage can't be read
		if (currentCoverage == -1 || coverageBeforeLastRun == -1) {
			difference = 0;
		}
		return difference;
	}

	/**
	 * The line coverage of the given build if possible. Otherwise -1 will be
	 * returned.
	 * 
	 * @param build
	 *            to get the line coverage from
	 * @return the line coverage
	 */
	private float getLineCoverage(Run<?, ?> build) {
		float lineCoverage = -1; // cannot be negative
		
		// if there are no builds, there can be no test coverage
		if (build != null) {
			try {
				lineCoverage = service.getTestLineCoverageResult(build);
			} catch (Throwable throwable) {
				LOGGER.log(Level.FINE,
						"Problems while trying to get the line coverage: Will return -1.");
			}
		}
		return lineCoverage;
	}

}
