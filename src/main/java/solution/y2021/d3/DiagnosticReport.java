package solution.y2021.d3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class DiagnosticReport {

    private static final String ZERO = "0";
    private static final String ONE = "1";
    private final List<String[]> binaries;

    DiagnosticReport(List<String> input) {
        this.binaries = input.stream()
                .map(binary -> binary.split(""))
                .collect(Collectors.toList());
    }

    int calculatePowerConsumption() {
        int[] bitCounters = new int[binaries.getFirst().length];
        for (String[] bits : binaries) {
            for (int i = 0; i < bits.length; i++) {
                int bitCounter = bitCounters[i];
                bitCounters[i] = bits[i].equals(ONE) ? bitCounter + 1 : bitCounter - 1;
            }
        }
        StringBuilder gammaRateBuilder = new StringBuilder();
        StringBuilder epsilonRateBuilder = new StringBuilder();
        for (int bitCounter : bitCounters) {
            if (bitCounter >= 0) {
                gammaRateBuilder.append(ONE);
                epsilonRateBuilder.append(ZERO);
            } else {
                gammaRateBuilder.append(ZERO);
                epsilonRateBuilder.append(ONE);
            }
        }
        int gammaRate = Integer.parseInt(gammaRateBuilder.toString(), 2);
        int epsilonRate = Integer.parseInt(epsilonRateBuilder.toString(), 2);
        return gammaRate * epsilonRate;
    }

    int calculateLifeSupportRating() {
        List<String[]> oxyRemainingCandidates = new ArrayList<>(binaries);
        List<String[]> co2RemainingCandidates = new ArrayList<>(binaries);
        int oxygenGeneratorRating = 0;
        int co2ScrubberRating = 0;
        for (int i = 0; i < binaries.getFirst().length; i++) {
            final int j = i;
            int currentBitCounter = 0;

            for (String[] ratingCandidate : oxyRemainingCandidates) {
                currentBitCounter = ratingCandidate[i].equals(ONE) ? currentBitCounter + 1 : currentBitCounter - 1;
            }
            String mostCommonBit = currentBitCounter >= 0 ? ONE : ZERO;
            oxyRemainingCandidates.removeIf(bits -> !bits[j].equals(mostCommonBit));

            currentBitCounter = 0;

            for (String[] ratingCandidate : co2RemainingCandidates) {
                currentBitCounter = ratingCandidate[i].equals(ONE) ? currentBitCounter + 1 : currentBitCounter - 1;
            }
            String leastCommonBit = currentBitCounter >= 0 ? ZERO : ONE;
            co2RemainingCandidates.removeIf(bits -> !bits[j].equals(leastCommonBit));

            if (oxyRemainingCandidates.size() == 1) {
                oxygenGeneratorRating = Integer.parseInt(String.join("", oxyRemainingCandidates.getFirst()), 2);
            }
            if (co2RemainingCandidates.size() == 1) {
                co2ScrubberRating = Integer.parseInt(String.join("", co2RemainingCandidates.getFirst()), 2);
            }
        }

        return oxygenGeneratorRating * co2ScrubberRating;
    }
}
