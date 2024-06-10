package utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getHeaderE() {
        String prop = properties.getProperty("userEHeader");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property headerE is not specified in the config.properties file");
    }

    public String getHeaderT() {
        String prop = properties.getProperty("userTHeader");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property headerT is not specified in the config.properties file");
    }

    public String getIEmailVercel() {
        String prop = properties.getProperty("iEmailVercel");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property iEmail is not specified in the config.properties file");
    }

    public String getIPassVercel() {
        String prop = properties.getProperty("iPassVercel");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property iPassVercel is not specified in the config.properties file");
    }

    public String getVendor() {
        String prop = properties.getProperty("vendorURI");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property vendor staging URI is not specified in the config.properties file");
    }

    public String getVendorEndpoint() {
        String prop = properties.getProperty("salesAgentSessionEndpoint");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property vendor staging sales agent session endpoint is not specified in the config.properties file");
    }

    public String getVendorContentType() {
        String prop = properties.getProperty("contentTypeVercelStaging");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property content type for vendor staging is not specified in the config.properties file");
    }

    public String getVendorSearchEndpoint() {
        String prop = properties.getProperty("searchEndpoint");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property vendor staging search endpoint is not specified in the config.properties file");
    }

    public String getFilePathTrainItalia() {
        String prop = properties.getProperty("filePathTrainItalia");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property file path to Train Italia file is not specified in the config.properties file");
    }

    public String getFilePathNSI() {
        String prop = properties.getProperty("filePathNSI");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property file path to NSI file is not specified in the config.properties file");
    }

    public String getFilePathEurail() {
        String prop = properties.getProperty("filePathEurail");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property file path to Eurail file is not specified in the config.properties file");
    }

    public String getFilePathACP() {
        String prop = properties.getProperty("filePathACP");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property file path to ACP file is not specified in the config.properties file");
    }

    public String getFilePathGATE() {
        String prop = properties.getProperty("filePathGATE");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property file path to GATE file is not specified in the config.properties file");
    }

}
