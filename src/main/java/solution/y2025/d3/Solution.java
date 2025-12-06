package solution.y2025.d3;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        long result = 0;
        for (String bank : input) {
            long maxJoltage = getMaxJoltage(bank, 2);
            result += maxJoltage;
        }
        return Optional.of(String.valueOf(result));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        long result = 0;
        for (String bank : input) {
            long maxJoltage = getMaxJoltage(bank, 12);
            result += maxJoltage;
        }
        return Optional.of(String.valueOf(result));
    }

    private long getMaxJoltage(String bank, int setSize) {
        String[] batteries = bank.split("");
        StringBuilder joltageBuilder = new StringBuilder();
        int size = setSize - 1;
        int idx = 0;
        while (idx < batteries.length - size && size >= 0) {
            int currentMax = 0;
            for (int i = idx; i < batteries.length - size; i++) {
                int battery = Integer.parseInt(batteries[i]);
                if (battery > currentMax) {
                    currentMax = battery;
                    idx = i;
                }
            }
            joltageBuilder.append(currentMax);
            idx++;
            size--;
        }
        return Long.parseLong(joltageBuilder.toString());
    }
}