package solution.y2021.d11;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class OctopusGrid {

    private final List<List<Octopus>> grid;

    OctopusGrid(List<String> input) {
        this.grid = input.stream()
                .map(line ->
                        Arrays.stream(line.split(""))
                                .map(Integer::valueOf)
                                .map(Octopus::new)
                                .toList())
                .toList();

        calculateAdjacents(grid);
    }

    long simulateSteps(int steps) {
        long flashCount = 0;
        Set<OctopusLocation> flashed = new HashSet<>();

        for (int step = 0; step < steps; step++) {
            flashed.clear();
            for (List<Octopus> row : grid) {
                for (Octopus octopus : row) {
                    incrementEnergy(octopus, flashed);
                }
            }
            flashCount += flashed.size();
        }

        return flashCount;
    }

    long findFirstStepWithSimultaneousFlash() {
        long stepCount = 0;
        Set<OctopusLocation> flashed = new HashSet<>();
        long octopusCount = (long) grid.size() * grid.getFirst().size();

        while (flashed.size() != octopusCount) {
            stepCount++;
            flashed.clear();
            for (List<Octopus> row : grid) {
                for (Octopus octopus : row) {
                    incrementEnergy(octopus, flashed);
                }
            }
        }

        return stepCount;
    }

    private void incrementEnergy(Octopus octopus, Set<OctopusLocation> flashed) {
        if (flashed.contains(octopus.getLocation())) {
            return;
        }
        octopus.incrementEnergy();
        if (octopus.getEnergy() == 0) {
            flashed.add(octopus.getLocation());
            for (Octopus adjacentOctopus : octopus.getAdjacents()) {
                incrementEnergy(adjacentOctopus, flashed);
            }
        }
    }

    private void calculateAdjacents(List<List<Octopus>> grid) {
        for (List<Octopus> row : grid) {
            for (Octopus octopus : row) {
                int rowIndex = grid.indexOf(row);
                int colIndex = row.indexOf(octopus);
                octopus.setLocation(new OctopusLocation(rowIndex, colIndex));
                Set<OctopusLocation> adjacentLocations = getAdjacentOctopusLocations(octopus.getLocation());
                for (OctopusLocation location : adjacentLocations) {
                    octopus.addAdjacent(grid.get(location.row()).get(location.col()));
                }
            }
        }
    }

    private Set<OctopusLocation> getAdjacentOctopusLocations(OctopusLocation location) {
        return ADJACENT_LOCATION_CALCULATORS.stream()
                .map(calculator -> calculator.apply(location))
                .filter(Predicate.not(location::equals))
                .collect(Collectors.toSet());
    }

    private static final List<Function<OctopusLocation, OctopusLocation>> ADJACENT_LOCATION_CALCULATORS =
            List.of(
                    (location) -> new OctopusLocation(location.row(), Math.max(location.col() - 1, 0)), //w
                    (location) -> new OctopusLocation(Math.max(location.row() - 1, 0), Math.max(location.col() - 1, 0)), //nw
                    (location) -> new OctopusLocation(Math.max(location.row() - 1, 0), location.col()), //n
                    (location) -> new OctopusLocation(Math.max(location.row() - 1, 0), Math.min(location.col() + 1, 9)), //ne
                    (location) -> new OctopusLocation(location.row(), Math.min(location.col() + 1, 9)), //e
                    (location) -> new OctopusLocation(Math.min(location.row() + 1, 9), Math.min(location.col() + 1, 9)), //se
                    (location) -> new OctopusLocation(Math.min(location.row() + 1, 9), location.col()), //s
                    (location) -> new OctopusLocation(Math.min(location.row() + 1, 9), Math.max(location.col() - 1, 0))  //sw
            );
}
