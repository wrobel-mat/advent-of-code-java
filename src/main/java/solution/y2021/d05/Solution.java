package solution.y2021.d05;

import solution.ISolution;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Solution implements ISolution {

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        VentMap ventMap = new VentMap(input);
        Predicate<VentLine> horizontalVents = VentLine::isHorizontal;
        Predicate<VentLine> verticalVents = VentLine::isVertical;
        long overlapCount = ventMap.countOverlappingVentPoints(horizontalVents.or(verticalVents));
        return Optional.of(String.valueOf(overlapCount));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        VentMap ventMap = new VentMap(input);
        Predicate<VentLine> allVents = _ -> true;
        long overlapCount = ventMap.countOverlappingVentPoints(allVents);
        return Optional.of(String.valueOf(overlapCount));
    }
}
