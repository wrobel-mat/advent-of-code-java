package solution.y2021.d04;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

class Bingo {

    private final Integer[] numbers;
    private final List<Board> boards;

    Bingo(List<String> input) {
        this.numbers = getNumbers(input.getFirst());
        this.boards = getBoards(input.subList(2, input.size()));
    }

    private Integer[] getNumbers(String numbersInput) {
        return Arrays.stream(numbersInput.split(","))
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
    }

    private List<Board> getBoards(List<String> boardsInput) {
        List<Board> result = new ArrayList<>();
        for (int i = 0; i < boardsInput.size(); i += Board.SIZE + 1) {
            List<Board.Row> rows = getRows(boardsInput.subList(i, i + Board.SIZE));
            List<Board.Column> columns = getColumns(rows);
            result.add(new Board(rows, columns));
        }
        return result;
    }

    private List<Board.Row> getRows(List<String> rowsInput) {
        return rowsInput.stream()
                .map(row -> Arrays.stream(row.trim().split("\s+"))
                        .map(Integer::valueOf)
                        .map(Board.Number::new)
                        .toList())
                .map(Board.Row::new)
                .toList();
    }

    private List<Board.Column> getColumns(List<Board.Row> rows) {
        return IntStream.range(0, rows.size())
                .mapToObj(i -> rows.stream()
                        .map(row -> row.numbers().get(i))
                        .toList())
                .map(Board.Column::new)
                .toList();
    }

    int playUntilFirstWin() {
        for (Integer number : numbers) {
            for (Board board : boards) {
                for (Board.Row row : board.rows()) {
                    for (Board.Number rowNumber : row.numbers()) {
                        if (number.compareTo(rowNumber.value()) == 0) {
                            rowNumber.mark();
                        }
                    }
                }
                if (board.wins()) {
                    Integer unmarkedSum = getUnmarkedNumbersSum(board);
                    return unmarkedSum * number;
                }
            }
        }
        return 0;
    }

    int playAllBoards() {
        Board lastWinnerBoard = new Board(Collections.emptyList(), Collections.emptyList());
        Integer lastWinnerNumber = 0;
        Set<Board> winnerBoards = new HashSet<>();
        for (Integer number : numbers) {
            for (Board board : boards) {
                if (winnerBoards.contains(board)) {
                    continue;
                }
                for (Board.Row row : board.rows()) {
                    for (Board.Number rowNumber : row.numbers()) {
                        if (number.compareTo(rowNumber.value()) == 0) {
                            rowNumber.mark();
                        }
                    }
                }
                if (board.wins()) {
                    lastWinnerBoard = board;
                    lastWinnerNumber = number;
                    winnerBoards.add(board);
                }
            }
        }
        Integer unmarkedSum = getUnmarkedNumbersSum(lastWinnerBoard);
        return unmarkedSum * lastWinnerNumber;
    }

    private Integer getUnmarkedNumbersSum(Board board) {
        return board.rows().stream()
                .flatMap(row ->
                        row.numbers().stream()
                                .filter(Predicate.not(Board.Number::marked))
                                .map(Board.Number::value))
                .reduce(Integer::sum)
                .orElse(0);
    }
}
