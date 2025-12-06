package solution.y2025.d5;

import solution.ISolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class Solution implements ISolution {

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        List<Range> idRanges = new ArrayList<>();
        int i = 0;
        while (!input.get(i).isEmpty()) {
            String[] split = input.get(i).split("-");
            idRanges.add(new Range(Long.parseLong(split[0]), Long.parseLong(split[1])));
            i++;
        }
        i++;
        int result = 0;
        while (i < input.size()) {
            long id = Long.parseLong(input.get(i));
            for (Range range : idRanges) {
                if (id >= range.start() && id <= range.end()) {
                    result++;
                    break;
                }
            }
            i++;
        }
        return Optional.of(String.valueOf(result));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        PriorityQueue<Range> rangeQueue = new PriorityQueue<>();
        int i = 0;
        while (!input.get(i).isEmpty()) {
            String[] split = input.get(i).split("-");
            rangeQueue.offer(new Range(Long.parseLong(split[0]), Long.parseLong(split[1])));
            i++;
        }
        long result = 0;
        long prevStart = rangeQueue.peek().start();
        long prevEnd = rangeQueue.peek().end();
        while (!rangeQueue.isEmpty()) {
            Range range = rangeQueue.poll();
            if (range.start() > prevEnd) {
                long idCount = prevEnd - prevStart + 1;
                result += idCount;
                prevStart = range.start();
            }
            prevEnd = Math.max(prevEnd, range.end());
        }
        long idCount = prevEnd - prevStart + 1;
        result += idCount;
        return Optional.of(String.valueOf(result));
    }
}