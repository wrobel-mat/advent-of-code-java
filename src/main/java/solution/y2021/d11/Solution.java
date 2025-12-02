package solution.y2021.d11;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solvePartOne(List<String> input) {
        OctopusGrid octopusGrid = new OctopusGrid(input);
        long flashCount = octopusGrid.simulateSteps(100);
        return Optional.of(String.valueOf(flashCount));
    }

    @Override
    public Optional<String> solvePartTwo(List<String> input) {
        OctopusGrid octopusGrid = new OctopusGrid(input);
        long step = octopusGrid.findFirstStepWithSimultaneousFlash();
        return Optional.of(String.valueOf(step));
    }
}
