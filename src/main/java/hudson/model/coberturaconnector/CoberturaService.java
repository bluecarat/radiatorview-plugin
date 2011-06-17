package hudson.model.coberturaconnector;

import hudson.model.Run;

/**
 * Service to connect to cobertura to make it possible to be mocked.
 *
 * @author Chris Stahlhut
 */
public interface CoberturaService {
    
    /**
     * Gets the last test line coverage of the given Hudson Run, calculated by the Hudson Cobertura Plugin.
     * 
     * @param data.job
     *            to get the test coverage from
     * @return the result of the coverage
     */
    float getTestLineCoverageResult(Run<?,?> run);
}