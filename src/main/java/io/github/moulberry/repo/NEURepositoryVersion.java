package io.github.moulberry.repo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class NEURepositoryVersion {

    public static Properties VERSION_PROPERTIES = new Properties();

    static {
        try (InputStream resourceAsStream = NEURepositoryVersion.class.getClassLoader().getResourceAsStream("neurepoparser.properties");) {
            VERSION_PROPERTIES.load(resourceAsStream);
        } catch (IOException | NullPointerException e) {
            new RuntimeException("NEURepositoryVersion could not load neurepoparser.properties.", e).printStackTrace();
        }
    }

    public static String REPOSITORY_PARSER_VERSION = VERSION_PROPERTIES.getProperty("neurepository.parser.version", "unknown");
    public static int REPOSITORY_SCHEMA_VERSION_MINOR = Integer.parseInt(VERSION_PROPERTIES.getProperty("neurepository.schema.minor", "-1"));
    public static int REPOSITORY_SCHEMA_VERSION_MAJOR = Integer.parseInt(VERSION_PROPERTIES.getProperty("neurepository.schema.major", "-1"));


}
