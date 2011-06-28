package hudson.model.coberturaconnector;

/**
 * The line coverage of a result, provided by the cobertura plugin.
 * 
 * @author Chris Stahlhut
 * 
 */
public class CoberturaLineCoverageResult {

    /**
     * The line coverage.
     */
    private final float lineCoverage;

    /**
     * The difference of the line coverage between two sequent builds
     */
    private final float lineCoverageDiff;

    /**
     * Constructor.
     * 
     * @param lineCoverage
     *            of a build
     * @param lineCoverageDiff
     *            difference of the line coverage of two sequent builds
     */
    public CoberturaLineCoverageResult(final float lineCoverage,
            final float lineCoverageDiff) {
        this.lineCoverage = lineCoverage;
        this.lineCoverageDiff = lineCoverageDiff;
    }

    /**
     * @return the lineCoverage
     */
    public float getLineCoverage() {
        return lineCoverage;
    }

    /**
     * @return the lineCoverageDiff
     */
    public float getLineCoverageDiff() {
        return lineCoverageDiff;
    }
}
