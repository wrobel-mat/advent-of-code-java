package solution.y2025.d08;

import solution.ISolution;

import java.util.*;

public class Solution implements ISolution {

    private PriorityQueue<Pair> pairs = null;

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        PriorityQueue<Pair> pairs = getPairs(input);
        PriorityQueue<Circuit> circuits = new PriorityQueue<>(Comparator.reverseOrder());
        int pairCount = 1000;
        while (pairCount > 0 && !pairs.isEmpty()) {
            Circuit newCircuit = pairs.poll().toCircuit();
            Queue<Circuit> connected = new ArrayDeque<>();
            Iterator<Circuit> circuitIterator = circuits.iterator();
            while (circuitIterator.hasNext()) {
                Circuit circuit = circuitIterator.next();
                if (circuit.connectsWith(newCircuit)) {
                    connected.offer(circuit);
                    circuitIterator.remove();
                }
            }
            while (!connected.isEmpty()) {
                newCircuit = newCircuit.union(connected.poll());
            }
            circuits.offer(newCircuit);
            pairCount--;
        }

        long result = 1;
        int maxCircuitsCount = 3;
        while (maxCircuitsCount > 0 && !circuits.isEmpty()) {
            result *= circuits.poll().size();
            maxCircuitsCount--;
        }
        return Optional.of(String.valueOf(result));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        PriorityQueue<Pair> pairs = getPairs(input);
        List<Circuit> circuits = new ArrayList<>();
        long result = -1;
        outer:
        while (!pairs.isEmpty()) {
            Pair pair = pairs.poll();
            Circuit newCircuit = pair.toCircuit();
            Queue<Circuit> connected = new ArrayDeque<>();
            Iterator<Circuit> circuitIterator = circuits.iterator();
            while (circuitIterator.hasNext()) {
                Circuit circuit = circuitIterator.next();
                if (circuit.connectsWith(newCircuit)) {
                    connected.offer(circuit);
                    circuitIterator.remove();
                }
            }
            while (!connected.isEmpty()) {
                newCircuit = newCircuit.union(connected.poll());
                if (newCircuit.size() == input.size()) {
                    result = pair.a().x() * pair.b().x();
                    break outer;
                }
            }
            circuits.add(newCircuit);
        }

        return Optional.of(String.valueOf(result));
    }

    private PriorityQueue<Pair> getPairs(List<String> input) {
        if (pairs != null) {
            return new PriorityQueue<>(pairs);
        }
        pairs = new PriorityQueue<>();
        for (int i = 0; i < input.size(); i++) {
            long[] currentCoordinates = Arrays.stream(input.get(i).split(",")).mapToLong(Long::parseLong).toArray();
            JunctionBox current = new JunctionBox(currentCoordinates[0], currentCoordinates[1], currentCoordinates[2]);
            for (int j = i + 1; j < input.size(); j++) {
                long[] otherCoordinates = Arrays.stream(input.get(j).split(",")).mapToLong(Long::parseLong).toArray();
                JunctionBox other = new JunctionBox(otherCoordinates[0], otherCoordinates[1], otherCoordinates[2]);
                pairs.offer(new Pair(current, other));
            }
        }
        return new PriorityQueue<>(pairs);
    }
}