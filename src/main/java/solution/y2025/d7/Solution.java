package solution.y2025.d7;

import solution.ISolution;

import java.util.*;

public class Solution implements ISolution {

    private static final char SPLITTER = '^';
    private Splitter root = null;
    private Map<Position, Splitter> activatedSplitters = null;

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        Splitter root = findRoot(input);
        Map<Position, Splitter> activated = getActivatedSplitters(root, input);
        return Optional.of(String.valueOf(activated.size()));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        Splitter root = findRoot(input);
        long timelines = getTimelines(root);
        return Optional.of(String.valueOf(timelines));
    }

    private Splitter findRoot(List<String> input) {
        if (root != null) {
            return root;
        }

        outer:
        for (int row = 0; row < input.size(); row++) {
            int col = 0;
            String line = input.get(row);
            while (col < line.length()) {
                if (line.charAt(col) == SPLITTER) {
                    root = new Splitter(new Position(row, col));
                    break outer;
                } else {
                    col++;
                }
            }
        }
        return root;
    }

    private Map<Position, Splitter> getActivatedSplitters(Splitter root, List<String> input) {
        if (activatedSplitters != null) {
            return activatedSplitters;
        }
        activatedSplitters = new HashMap<>();
        activatedSplitters.put(root.position, root);
        Deque<Splitter> splitterDeque = new ArrayDeque<>();
        splitterDeque.offer(root);

        int height = input.size();
        int width = input.get(0).length();

        while (!splitterDeque.isEmpty()) {
            Splitter currentSplitter = splitterDeque.poll();
            if (currentSplitter.position.col() - 1 >= 0) {
                int rowIdx = currentSplitter.position.row() + 1;
                int colIdx = currentSplitter.position.col() - 1;
                while (rowIdx < height) {
                    if (input.get(rowIdx).charAt(colIdx) == SPLITTER) {
                        Position splitterPos = new Position(rowIdx, colIdx);
                        if (!activatedSplitters.containsKey(splitterPos)) {
                            Splitter splitter = new Splitter(splitterPos);
                            splitterDeque.offer(splitter);
                            activatedSplitters.put(splitterPos, splitter);
                            currentSplitter.left = splitter;
                        } else {
                            currentSplitter.left = activatedSplitters.get(splitterPos);
                        }
                        break;
                    }
                    rowIdx++;
                }
            }
            if (currentSplitter.position.col() + 1 < width) {
                int rowIdx = currentSplitter.position.row() + 1;
                int colIdx = currentSplitter.position.col() + 1;
                while (rowIdx < height) {
                    if (input.get(rowIdx).charAt(colIdx) == SPLITTER) {
                        Position splitterPos = new Position(rowIdx, colIdx);
                        if (!activatedSplitters.containsKey(splitterPos)) {
                            Splitter splitter = new Splitter(splitterPos);
                            splitterDeque.offer(splitter);
                            activatedSplitters.put(splitterPos, splitter);
                            currentSplitter.right = splitter;
                        } else {
                            currentSplitter.right = activatedSplitters.get(splitterPos);
                        }
                        break;
                    }
                    rowIdx++;
                }
            }
        }
        return activatedSplitters;
    }

    private long getTimelines(Splitter splitter) {
        // recursive DFS + memoization
        if (splitter == null) {
            return 1;
        }
        if (splitter.timelines != -1) {
            return splitter.timelines;
        }
        splitter.timelines = getTimelines(splitter.left) + getTimelines(splitter.right);
        return splitter.timelines;
    }

    private long getTimelines(Map<Position, Splitter> activatedSplitters, int inputWidth) {
        // non-recursive version from reddit with tracking of timelines in array
        long[] timelines = new long[inputWidth];
        timelines[root.position.col()] = 1;
        activatedSplitters.values().stream().sorted().forEachOrdered(splitter -> {
            int currentCol = splitter.position.col();
            timelines[currentCol - 1] = timelines[currentCol - 1] + timelines[currentCol];
            timelines[currentCol + 1] = timelines[currentCol + 1] + timelines[currentCol];
            timelines[currentCol] = 0;
        });
        return Arrays.stream(timelines).sum();
    }
}