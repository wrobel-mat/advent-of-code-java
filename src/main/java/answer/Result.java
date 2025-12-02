package answer;

import java.util.Map;
import java.util.TreeMap;

public class Result {

    private int year;
    private int day;
    private Map<Integer, Answer> answers;

    public static Result of(int year, int day) {
        Result result = new Result();
        result.year = year;
        result.day = day;
        result.answers = new TreeMap<>(Map.of(1, Answer.forLevel(1), 2, Answer.forLevel(2)));
        return result;
    }

    public void completeLevel(int level, String answer) {
        Answer currentAnswer = answers.get(level);
        currentAnswer.setCompleted();
        currentAnswer.setAnswer(answer);
    }

    public boolean levelNotCompleted(int level) {
        return !answers.get(level).isCompleted();
    }

    public int year() {
        return year;
    }

    public int day() {
        return day;
    }

    public boolean bothLevelsCompleted() {
        return answers.values().stream().allMatch(Answer::isCompleted);
    }

    @Override
    public String toString() {
        return String.format("year: %d, day: %d%n  answers: %s", year, day, answers.values());
    }
}
