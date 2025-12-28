package solution.y2025.d06;

import solution.ISolution;

import java.util.*;

public class Solution implements ISolution {

    private static final String MULTIPLY = "*";

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        String[] ops = new String[0];
        List<long[]> groups = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (i == input.size() - 1) {
                ops = input.get(i).split("\\s+");
            } else {
                long[] group = Arrays.stream(input.get(i).split("\\s+")).mapToLong(Long::parseLong).toArray();
                groups.add(group);
            }
        }
        long result = 0;
        for (int i = 0; i < ops.length; i++) {
            if (ops[i].equals(MULTIPLY)) {
                long product = 1;
                for (long[] group : groups) {
                    product *= group[i];
                }
                result += product;
            } else {
                long sum = 0;
                for (long[] group : groups) {
                    sum += group[i];
                }
                result += sum;
            }
        }
        return Optional.of(String.valueOf(result));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        int numSize = input.size() - 1;
        List<char[]> rotatedNums = new ArrayList<>();
        String[] ops = new String[0];
        for (int inputIdx = 0; inputIdx < input.size(); inputIdx++) {
            if (inputIdx == input.size() - 1) {
                ops = input.get(inputIdx).split("\\s+");
            } else {
                char[] group = input.get(inputIdx).toCharArray();
                for (int groupIdx = group.length - 1, numIdx = 0; groupIdx >= 0; groupIdx--, numIdx++) {
                    if (rotatedNums.size() == numIdx) {
                        rotatedNums.add(new char[numSize]);
                    }
                    rotatedNums.get(numIdx)[inputIdx] = group[groupIdx];
                }
            }
        }
        long result = 0;
        int opIdx = ops.length - 1;
        String currentOp = ops[opIdx];
        long currentProduct = 1;
        long currentSum = 0;
        for (char[] rotatedNum : rotatedNums) {
            String num = new String(rotatedNum).trim();
            if (num.isBlank()) {
                if (currentOp.equals(MULTIPLY)) {
                    result += currentProduct;
                    currentProduct = 1;
                } else {
                    result += currentSum;
                    currentSum = 0;
                }
                currentOp = ops[--opIdx];
            } else {
                if (currentOp.equals(MULTIPLY)) {
                    currentProduct *= Long.parseLong(num);
                } else {
                    currentSum += Long.parseLong(num);
                }
            }
        }
        if (currentOp.equals(MULTIPLY)) {
            result += currentProduct;
        } else {
            result += currentSum;
        }
        return Optional.of(String.valueOf(result));
    }
}