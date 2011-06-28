package hudson.model.coberturaconnector;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.codehaus.groovy.control.CompilationFailedException;

import groovy.lang.GroovyClassLoader;
import hudson.model.Hudson;

/**
 * Compiles and loads the groovy class at run-time so there doesn't has to be a
 * dependency from the cobertura plugin at compile-time.
 * 
 * @author Chris Stahlhut
 * 
 */
public final class CoberturaServiceLoaderUtil {

    /**
     * Logger of this class.
     */
    private static final Logger LOGGER = Logger
            .getLogger(CoberturaServiceLoaderUtil.class.toString());

    /**
     * Private constructor to avoid instantiation.
     */
    private CoberturaServiceLoaderUtil() {
        // nop
    }

    /**
     * Loads the CoberturaServiceImpl.
     * 
     * @return the service implementation
     */
    public static CoberturaService loadCoberturaServiceImpl() {

        // We need access to classes of another plugin
        final ClassLoader parentLoader = Hudson.getInstance()
                .getPluginManager().uberClassLoader;
        final GroovyClassLoader loader = new GroovyClassLoader(parentLoader);

        CoberturaService service = null;

        try {

            final Class<?> groovyClass = loader.parseClass(new File(
                    "src/main/resources/CoberturaServiceImpl.groovy"));

            service = (CoberturaService) groovyClass.newInstance();

        } catch (CompilationFailedException e) {
            final String message = "Groovy file could not be parsed.";
            LOGGER.warning(message);
            throw new CoberturaExecutionException(message, e);
        } catch (IOException e) {
            final String message = "Groovy file could not be read.";
            LOGGER.warning(message);
            throw new CoberturaExecutionException(message, e);
        } catch (InstantiationException e) {
            final String message = "CoberturaServiceImpl could not be instantiated.";
            LOGGER.warning(message);
            throw new CoberturaExecutionException(message, e);
        } catch (IllegalAccessException e) {
            final String message = "Constructor of class was not accessable.";
            LOGGER.warning(message);
            throw new CoberturaExecutionException(message, e);
        }
        return service;
    }

    private static final class CoberturaExecutionException extends
            RuntimeException {

        /**
         * Version UID.
         */
        private static final long serialVersionUID = -782400958522766185L;

        /**
         * Constructor.
         * 
         * @param message
         * @param embeddedException
         */
        public CoberturaExecutionException(final String message,
                final Throwable embeddedException) {
            super(message, embeddedException);
            // TODO Auto-generated constructor stub
        }
    }
}
