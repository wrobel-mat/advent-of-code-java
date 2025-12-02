package solution.y2021.d10;

import java.util.*;

class NavigationSubsystemProcessor {

    private static final Map<Character, Character> CHARACTER_PAIRS = Map.of('(', ')', '[', ']', '{', '}', '<', '>');
    private static final Map<Character, Integer> ERROR_POINTS = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);
    private static final Map<Character, Integer> AUTOCOMPLETE_POINTS = Map.of('(', 1, '[', 2, '{', 3, '<', 4);
    private final List<String> input;

    NavigationSubsystemProcessor(List<String> input) {
        this.input = input;
    }

    public long calculateSyntaxErrorsScore() {
        long score = 0;
        for (String navSubsystemLine : input) {
            Deque<Character> openingCharsStack = new ArrayDeque<>();
            for (char currentChar : navSubsystemLine.toCharArray()) {
                if (isOpeningCharacter(currentChar)) {
                    openingCharsStack.push(currentChar);
                } else {
                    char openingChar = openingCharsStack.pop();
                    if (!isMatchingPair(openingChar, currentChar)) {
                        score += getErrorScoreForChar(currentChar);
                        break;
                    }
                }
            }
        }
        return score;
    }

    public long calculateAutocompleteScoreWinner() {
        Set<Long> autocompleteScores = new TreeSet<>();
        for (String navSubsystemLine : input) {
            boolean isCorrupted = false;
            Deque<Character> openingCharsStack = new ArrayDeque<>();
            for (char currentChar : navSubsystemLine.toCharArray()) {
                if (isOpeningCharacter(currentChar)) {
                    openingCharsStack.push(currentChar);
                } else {
                    char openingChar = openingCharsStack.pop();
                    if (!isMatchingPair(openingChar, currentChar)) {
                        isCorrupted = true;
                        break;
                    }
                }
            }
            if (!isCorrupted) {
                autocompleteScores.add(calculateAutocompleteScore(openingCharsStack));
            }
        }
        int winnerIndex = (autocompleteScores.size() - 1) / 2;
        return (long) autocompleteScores.toArray()[winnerIndex];
    }

    private boolean isOpeningCharacter(char c) {
        return CHARACTER_PAIRS.containsKey(c);
    }

    private boolean isMatchingPair(char openingChar, char closingChar) {
        return CHARACTER_PAIRS.get(openingChar) == closingChar;
    }

    private Integer getErrorScoreForChar(char currentChar) {
        return ERROR_POINTS.get(currentChar);
    }

    private long calculateAutocompleteScore(Deque<Character> openingCharsStack) {
        long score = 0;
        while (!openingCharsStack.isEmpty()) {
            score *= 5;
            score += AUTOCOMPLETE_POINTS.get(openingCharsStack.pop());
        }
        return score;
    }
}
