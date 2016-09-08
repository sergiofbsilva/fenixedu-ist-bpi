package org.fenixedu.bennu;

import org.fenixedu.commons.configuration.ConfigurationInvocationHandler;
import org.fenixedu.commons.configuration.ConfigurationManager;
import org.fenixedu.commons.configuration.ConfigurationProperty;

public class BPIIntegrationConfiguration {

    @ConfigurationManager(description = "BPI Integration Configuration")
    public interface ConfigurationProperties {

        @ConfigurationProperty(key = "converter.url", defaultValue="http://localhost:8000")
        public String converterUrl();

        @ConfigurationProperty(key = "converter.secret", defaultValue="changeme")
        public String converterSecret();

    }

    public static ConfigurationProperties getConfiguration() {
        return ConfigurationInvocationHandler.getConfiguration(ConfigurationProperties.class);
    }

}