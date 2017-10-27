package uk.ac.qub.csc3045.api.integration.util;

import java.io.IOException;
import java.util.Properties;
import java.io.File;
import java.io.FileReader;

public class PropertiesHelper {
	public static Properties LoadEnvironmentProperties() throws IOException {

        Properties properties = new Properties();
        //TODO make configurable in POM and/or command line argument if implementing CI/CD
        String propertiesFilePath = "src/test/resources/environments/" +  "local.properties";
        try {
            properties.load(new FileReader(new File(propertiesFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
