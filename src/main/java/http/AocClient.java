package http;

import config.Configuration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.lang.String.format;

public class AocClient {

    private static final Logger LOG = Logger.getLogger(AocClient.class.getSimpleName());

    public List<String> getInput(int year, int day) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            String sessionKey = Configuration.getSessionKey();
            String userAgent = Configuration.getUserAgent();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(format("https://adventofcode.com/%d/day/%d/input", year, day)))
                    .header("Cookie", format("session=%s", sessionKey))
                    .header("User-Agent", userAgent)
                    .build();
            HttpResponse<Stream<String>> response = client.send(request, HttpResponse.BodyHandlers.ofLines());
            LOG.info(format("Fetched input for year [%d] day [%d]", year, day));
            return response.body().toList();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public AocSubmitResult submitAnswer(int year, int day, int level, String answer) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            String sessionKey = Configuration.getSessionKey();
            String userAgent = Configuration.getUserAgent();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(format("https://adventofcode.com/%d/day/%d/answer", year, day)))
                    .header("Cookie", format("session=%s", sessionKey))
                    .header("User-Agent", userAgent)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(format("level=%d&answer=%s", level, answer)))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Document document = Jsoup.parse(response.body());
            String responseMsg = document.getElementsByTag("article").text();
            LOG.info(format("Submitted solution for year [%d] day [%d] level [%d]", year, day, level));
            LOG.info(format("Answer: [%s] Response: %s", answer, responseMsg));
            return new AocSubmitResult(answer, responseMsg);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
