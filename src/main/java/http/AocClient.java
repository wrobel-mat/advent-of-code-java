package http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import config.Configuration;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public class AocClient {

    private static final Logger LOG = Logger.getLogger(AocClient.class.getName());
    
    public List<String> getInput(int year, int day) {
        try {
            Properties props = Configuration.getProperties();
            String sessionKey = props.getProperty("session.key");
            String userAgent = props.getProperty("user.agent");
            URL url = URI.create(String.format("https://adventofcode.com/%d/day/%d/input", year, day)).toURL();
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("Cookie", String.format("session=%s", sessionKey));
            connection.setRequestProperty("User-Agent", userAgent);
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            List<String> input = new ArrayList<>();
            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }
            return input;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AocSubmitResult submitAnswer(int year, int day, int part, String answer) {
        try {
            Properties props = Configuration.getProperties();
            String sessionKey = props.getProperty("session.key");
            String userAgent = props.getProperty("user.agent");
            URL url = URI.create(String.format("https://adventofcode.com/%d/day/%d/answer", year, day)).toURL();
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Cookie", String.format("session=%s", sessionKey));
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setDoOutput(true);
            connection.getOutputStream().write(String.format("level=%d&answer=%s", part, answer).getBytes());
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream response = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int length; (length = inputStream.read(buffer)) != -1; ) {
                response.write(buffer, 0, length);
            }
            Document document = Jsoup.parse(response.toString());
            String responseMsg = document.getElementsByTag("article").text();
            LOG.info(String.format("Part %d%nAnswer: %s%nResponse: %s", part, answer, responseMsg));
            return new AocSubmitResult(answer, responseMsg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
