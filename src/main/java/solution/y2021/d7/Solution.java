package solution.y2021.d7;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solvePartOne(List<String> input) {
        CrabSwarm crabSwarm = new CrabSwarm(input.getFirst());
        CrabSubmarineFuelUsageCalculator fuelUsageCalculator = distance -> distance;
        CrabSwarmSimulator swarmSimulator = new CrabSwarmSimulator(fuelUsageCalculator);
        long fuelUsage = swarmSimulator.simulateSwarmAlignmentWithLowestFuelUsage(crabSwarm);
        return Optional.of(String.valueOf(fuelUsage));
    }

    @Override
    public Optional<String> solvePartTwo(List<String> input) {
        CrabSwarm crabSwarm = new CrabSwarm(input.getFirst());
        CrabSubmarineFuelUsageCalculator fuelUsageCalculator = distance -> distance * (distance + 1) / 2;
        CrabSwarmSimulator swarmSimulator = new CrabSwarmSimulator(fuelUsageCalculator);
        long fuelUsage = swarmSimulator.simulateSwarmAlignmentWithLowestFuelUsage(crabSwarm);
        return Optional.of(String.valueOf(fuelUsage));
    }
}
