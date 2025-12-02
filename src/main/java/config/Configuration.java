package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static Properties props;

    private Configuration() {
    }

    private static Properties getProperties() {
        if (props != null) {
            return props;
        }

        try {
            InputStream propsConfig = Configuration.class.getClassLoader().getResourceAsStream("aoc.properties");
            props = new Properties();
            props.load(propsConfig);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getYear() {
        return Integer.parseInt(getProperties().getProperty("year"));
    }

    public static int getDay() {
        return Integer.parseInt(getProperties().getProperty("day"));
    }

    public static String getSessionKey() {
        return getProperties().getProperty("session.key");
    }

    public static String getUserAgent() {
        return getProperties().getProperty("user.agent");
    }
}
