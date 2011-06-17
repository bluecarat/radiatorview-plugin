import hudson.model.AbstractBuild;
import hudson.model.Run;

import hudson.model.coberturaconnector.CoberturaService;

import hudson.plugins.cobertura.CoberturaBuildAction;
import hudson.plugins.cobertura.CoberturaCoverageParser;
import hudson.plugins.cobertura.CoberturaPublisher;
import hudson.plugins.cobertura.targets.CoverageMetric;
import hudson.plugins.cobertura.targets.CoverageResult;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the {@link CoberturaService}
 * 
 * @author Chris Stahlhut
 */
class CoberturaServiceImpl implements CoberturaService {

    /**
     * Logger of this class.
     */
    private static final Logger LOGGER = Logger.getLogger(CoberturaServiceImpl.class.toString());

    /**
     * {@inheritDoc}
     */
    public float getTestLineCoverageResult(Run<?, ?> build) {
        File[] coberturaReportFiles = build.getRootDir().listFiles(CoberturaPublisher.COBERTURA_FILENAME_FILTER);

        CoverageResult coverage = null;
        for (File reportFile : coberturaReportFiles) {
            // The parser will aggregate the report files, so we do not have to care about old values referenced by
            // coverage
            try {
                coverage = CoberturaCoverageParser.parse(reportFile, coverage);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "coverage could not be parsed.", e);
            }
            CoberturaBuildAction.load((AbstractBuild<?, ?>) build, coverage, null, null, false);
        }
        return coverage.getCoverage(CoverageMetric.LINE).getPercentageFloat();
    }
}