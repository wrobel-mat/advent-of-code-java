package solution.y2021.d08;

import java.util.*;
import java.util.stream.Collectors;

class DisplaysReport {

    private static final int NUM_1_ACTIVE_SEGMENTS_COUNT = 2;
    private static final int NUM_4_ACTIVE_SEGMENTS_COUNT = 4;
    private static final int NUM_7_ACTIVE_SEGMENTS_COUNT = 3;
    private static final int NUM_8_ACTIVE_SEGMENTS_COUNT = 7;
    private static final int NUM_0_ACTIVE_SEGMENTS_COUNT = 6;

    private final List<DisplayEntry> displayEntries;

    DisplaysReport(List<String> input) {
        displayEntries = input.stream().map(entry -> {
            String[] entryInput = entry.split(" \\| ");
            String[] patternsInput = entryInput[0].split(" ");
            String[] outputInput = entryInput[1].split(" ");
            List<Number> patterns = parseNumbers(patternsInput);
            List<Number> output = parseNumbers(outputInput);
            return new DisplayEntry(patterns, output);
        }).toList();
    }

    long countEasyDigits() {
        Set<Integer> easyDigitSizes = Set.of(NUM_1_ACTIVE_SEGMENTS_COUNT, NUM_4_ACTIVE_SEGMENTS_COUNT, NUM_7_ACTIVE_SEGMENTS_COUNT, NUM_8_ACTIVE_SEGMENTS_COUNT);
        return displayEntries.stream()
                .map(DisplayEntry::output)
                .flatMap(Collection::stream)
                .map(Number::segments)
                .map(Set::size)
                .filter(easyDigitSizes::contains)
                .count();
    }

    long decodeAndSumOutputValues() {
        List<Integer> decodedNumbers = new ArrayList<>();
        for (DisplayEntry entry : displayEntries) {
            List<Number> patterns = entry.patterns();
            Number eight = this.filterNumberWithMatchingActiveSegments(patterns, NUM_8_ACTIVE_SEGMENTS_COUNT);
            Number four = this.filterNumberWithMatchingActiveSegments(patterns, NUM_4_ACTIVE_SEGMENTS_COUNT);
            Number seven = this.filterNumberWithMatchingActiveSegments(patterns, NUM_7_ACTIVE_SEGMENTS_COUNT);
            Number one = this.filterNumberWithMatchingActiveSegments(patterns, NUM_1_ACTIVE_SEGMENTS_COUNT);
            Set<Segment> abcdefg = eight.segments();
            Set<Segment> bcdf = four.segments();
            Set<Segment> acf = seven.segments();
            Set<Segment> cf = one.segments();
            Segment a = this.filterSegmentFromFirstSet(acf, cf);
            Set<Segment> abcdf = this.buildSetFrom(a, bcdf);
            Set<Segment> eg = this.difference(abcdefg, abcdf);
            Set<Segment> bd = this.difference(bcdf, cf);
            Set<Segment> acefg = this.buildSetFrom(a, cf, eg);
            Set<Segment> abdeg = this.buildSetFrom(a, bd, eg);
            Segment b = null;
            Segment f = null;
            Segment g = null;
            Set<Number> zero_six_nine = this.filterNumbersWithMatchingActiveSegments(patterns, NUM_0_ACTIVE_SEGMENTS_COUNT);
            for (Number number : zero_six_nine) {
                Set<Segment> segments = number.segments();
                if (segments.containsAll(acefg)) {
                    b = this.filterSegmentFromFirstSet(segments, acefg);
                }
                if (segments.containsAll(abdeg)) {
                    f = this.filterSegmentFromFirstSet(segments, abdeg);
                }
                if (segments.containsAll(abcdf)) {
                    g = this.filterSegmentFromFirstSet(segments, abcdf);
                }
            }

            Segment c = this.filterSegmentFromFirstSet(cf, Set.of(f));
            Segment d = this.filterSegmentFromFirstSet(bd, Set.of(b));
            Segment e = this.filterSegmentFromFirstSet(eg, Set.of(g));

            Map<Number, Integer> decoder = this.buildNumbersMap(a, b, c, d, e, f, g);

            decodedNumbers.add(this.decodeNumberFromOutput(decoder, entry.output()));
        }

        return decodedNumbers.stream().reduce(Integer::sum).orElse(0);
    }

    private int decodeNumberFromOutput(Map<Number, Integer> decoder, List<Number> output) {
        return decoder.get(output.get(0)) * 1000
                + decoder.get(output.get(1)) * 100
                + decoder.get(output.get(2)) * 10
                + decoder.get(output.get(3));
    }

    private Map<Number, Integer> buildNumbersMap(Segment a, Segment b, Segment c, Segment d, Segment e, Segment f, Segment g) {
        Map<Number, Integer> numbers = new HashMap<>();
        numbers.put(new Number(Set.of(a, b, c, e, f, g)), 0);
        numbers.put(new Number(Set.of(c, f)), 1);
        numbers.put(new Number(Set.of(a, c, d, e, g)), 2);
        numbers.put(new Number(Set.of(a, c, d, f, g)), 3);
        numbers.put(new Number(Set.of(b, c, d, f)), 4);
        numbers.put(new Number(Set.of(a, b, d, f, g)), 5);
        numbers.put(new Number(Set.of(a, b, d, e, f, g)), 6);
        numbers.put(new Number(Set.of(a, c, f)), 7);
        numbers.put(new Number(Set.of(a, b, c, d, e, f, g)), 8);
        numbers.put(new Number(Set.of(a, b, c, d, f, g)), 9);
        return numbers;
    }

    private Number filterNumberWithMatchingActiveSegments(List<Number> patterns, int activeSegmentsCount) {
        return patterns.stream()
                .filter(p -> p.segments().size() == activeSegmentsCount)
                .findFirst()
                .get();
    }

    private Set<Number> filterNumbersWithMatchingActiveSegments(List<Number> patterns, int activeSegmentsCount) {
        return patterns.stream()
                .filter(p -> p.segments().size() == activeSegmentsCount)
                .collect(Collectors.toSet());
    }

    private Segment filterSegmentFromFirstSet(Set<Segment> s1, Set<Segment> s2) {
        if (s1.size() > s2.size() && s1.size() - s2.size() == 1) {
            return s1.stream().filter(s -> !s2.contains(s)).findFirst().get();
        } else {
            throw new IllegalArgumentException("First set size must be greater from the second set size exactly by one element");
        }
    }

    private Set<Segment> difference(Set<Segment> s1, Set<Segment> s2) {
        return s1.size() > s2.size()
                ? s1.stream().filter(s -> !s2.contains(s)).collect(Collectors.toSet())
                : s2.stream().filter(s -> !s1.contains(s)).collect(Collectors.toSet());
    }

    @SafeVarargs
    private Set<Segment> buildSetFrom(Segment s1, Set<Segment> s2, Set<Segment> ... segments) {
        Set<Segment> result = new HashSet<>();
        result.add(s1);
        result.addAll(s2);
        for (Set<Segment> sn : segments) {
            result.addAll(sn);
        }
        return result;
    }

    private List<Number> parseNumbers(String[] input) {
        return Arrays.stream(input)
                .map(String::toCharArray)
                .map(arr -> {
                    Set<Segment> segments = new HashSet<>();
                    for (char c : arr) {
                        segments.add(new Segment(c));
                    }
                    return new Number(segments);
                }).toList();
    }
}
