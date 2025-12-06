package solution.y2025.d4;

import solution.ISolution;

import java.util.*;

public class Solution implements ISolution {

    private static final char ROLL_OF_PAPER = '@';
    private static final char EMPTY_SPACE = '.';

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        int result = 0;
        int colSize = input.size();
        for (int rowIdx = 0; rowIdx < colSize; rowIdx++) {
            char[] row = input.get(rowIdx).toCharArray();
            int rowSize = row.length;
            for (int colIdx = 0; colIdx < rowSize; colIdx++) {
                char pos = row[colIdx];
                if (pos == ROLL_OF_PAPER) {
                    int adjacentRolls = 0;
                    int top = rowIdx - 1;
                    int left = colIdx - 1;
                    int right = colIdx + 1;
                    int bottom = rowIdx + 1;
                    if (top >= 0) {
                        if (input.get(top).charAt(colIdx) == ROLL_OF_PAPER) {
                            adjacentRolls++;
                        }
                        if (left >= 0 && input.get(top).charAt(left) == ROLL_OF_PAPER) {
                            adjacentRolls++;
                        }
                        if (right <= rowSize - 1 && input.get(top).charAt(right) == ROLL_OF_PAPER) {
                            adjacentRolls++;
                        }
                    }
                    if (bottom <= colSize - 1) {
                        if (input.get(bottom).charAt(colIdx) == ROLL_OF_PAPER) {
                            adjacentRolls++;
                        }
                        if (left >= 0 && input.get(bottom).charAt(left) == ROLL_OF_PAPER) {
                            adjacentRolls++;
                        }
                        if (right <= rowSize - 1 && input.get(bottom).charAt(right) == ROLL_OF_PAPER) {
                            adjacentRolls++;
                        }
                    }
                    if (left >= 0 && row[left] == ROLL_OF_PAPER) {
                        adjacentRolls++;
                    }
                    if (right <= rowSize - 1 && row[right] == ROLL_OF_PAPER) {
                        adjacentRolls++;
                    }
                    if (adjacentRolls < 4) {
                        result++;
                    }
                }
            }
        }
        return Optional.of(String.valueOf(result));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        List<String> map = Collections.unmodifiableList(input);
        int result = 0;
        int colSize = map.size();
        boolean removed = true;
        while (removed) {
            removed = false;
            String[] newMap = new String[colSize];
            for (int rowIdx = 0; rowIdx < colSize; rowIdx++) {
                char[] row = map.get(rowIdx).toCharArray();
                int rowSize = row.length;
                char[] newRow = new char[rowSize];
                for (int colIdx = 0; colIdx < rowSize; colIdx++) {
                    char pos = row[colIdx];
                    if (pos == EMPTY_SPACE) {
                        newRow[colIdx] = EMPTY_SPACE;
                    } else {
                        int adjacentRolls = 0;
                        int top = rowIdx - 1;
                        int left = colIdx - 1;
                        int right = colIdx + 1;
                        int bottom = rowIdx + 1;
                        if (top >= 0) {
                            if (map.get(top).charAt(colIdx) == ROLL_OF_PAPER) {
                                adjacentRolls++;
                            }
                            if (left >= 0 && map.get(top).charAt(left) == ROLL_OF_PAPER) {
                                adjacentRolls++;
                            }
                            if (right <= rowSize - 1 && map.get(top).charAt(right) == ROLL_OF_PAPER) {
                                adjacentRolls++;
                            }
                        }
                        if (bottom <= colSize - 1) {
                            if (map.get(bottom).charAt(colIdx) == ROLL_OF_PAPER) {
                                adjacentRolls++;
                            }
                            if (left >= 0 && map.get(bottom).charAt(left) == ROLL_OF_PAPER) {
                                adjacentRolls++;
                            }
                            if (right <= rowSize - 1 && map.get(bottom).charAt(right) == ROLL_OF_PAPER) {
                                adjacentRolls++;
                            }
                        }
                        if (left >= 0 && row[left] == ROLL_OF_PAPER) {
                            adjacentRolls++;
                        }
                        if (right <= rowSize - 1 && row[right] == ROLL_OF_PAPER) {
                            adjacentRolls++;
                        }
                        if (adjacentRolls < 4) {
                            result++;
                            newRow[colIdx] = EMPTY_SPACE;
                            removed = true;
                        } else {
                            newRow[colIdx] = ROLL_OF_PAPER;
                        }
                    }
                }
                newMap[rowIdx] = new String(newRow);
            }
            map = List.of(newMap);
        }
        return Optional.of(String.valueOf(result));
    }
}