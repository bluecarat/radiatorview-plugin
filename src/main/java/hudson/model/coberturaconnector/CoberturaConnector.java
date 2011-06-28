package hudson.model.coberturaconnector;

import hudson.model.Job;
import hudson.model.Run;

/**
 * This class connects to the Hudson Cobertura Plugin and organizes the current
 * line coverage and the difference between the last two builds.
 * 
 * @author Chris Stahlhut
 * 
 */
public class CoberturaConnector {

    /**
     * The service to use.
     */
    private final CoberturaService service;;

    /**
     * The Job to work with.
     */
    private final Job<?, ?> job;

    /**
     * Simple constructor.
     * 
     * @param job
     *            to get information about the test coverage from
     */
    public CoberturaConnector(final Job<?, ?> job,
            final CoberturaService service) {
        this.job = job;
        this.service = service;
    }

    /**
     * Takes the current line coverage of the job.
     * 
     * @return the coverage in percent
     */
    public float getTestCoverage() {
        final Run<?, ?> build = job.getLastBuild();

        final float lineRatio = getLineCoverage(build);

        return lineRatio;
    }

    /**
     * Takes a Job and calculates the difference of test coverage between the
     * last 2 builds.
     * 
     * @return the difference in percent
     */
    public float getTestCoverageDiff() {
        final Run<?, ?> lastRun = job.getLastBuild();
        final float currentCoverage = getLineCoverage(lastRun);
        float coverageBeforeLastRun = 0;
        if (lastRun != null) {
            final Run<?, ?> runBeforeLastRun = lastRun.getPreviousBuild();
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
    private float getLineCoverage(final Run<?, ?> build) {
        float lineCoverage = -1; // cannot be negative

        // if there are no builds, there can be no test coverage
        if (build != null) {
            lineCoverage = service.getTestLineCoverageResult(build);
        }
        return lineCoverage;
    }
}
