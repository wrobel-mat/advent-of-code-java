package solution.y2021.d6;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solvePartOne(List<String> input) {
        LanternFishSpawnSimulation simulation = new LanternFishSpawnSimulation(input.getFirst());
        long populationCount = simulation.simulateSpawn(80).countPopulation();
        return Optional.of(String.valueOf(populationCount));
    }

    @Override
    public Optional<String> solvePartTwo(List<String> input) {
        LanternFishSpawnSimulation simulation = new LanternFishSpawnSimulation(input.getFirst());
        long populationCount = simulation.simulateSpawn(256).countPopulation();
        return Optional.of(String.valueOf(populationCount));
    }
}
