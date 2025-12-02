package solution.y2021.d4;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solvePartOne(List<String> input) {
        Bingo bingo = new Bingo(input);
        int answer = bingo.playUntilFirstWin();
        return Optional.of(String.valueOf(answer));
    }

    @Override
    public Optional<String> solvePartTwo(List<String> input) {
        Bingo bingo = new Bingo(input);
        int answer = bingo.playAllBoards();
        return Optional.of(String.valueOf(answer));
    }
}
