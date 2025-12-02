package solution.y2021.d12;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

import static solution.y2021.d12.CaveTraversalRules.CaveType.BIG_CAVE;
import static solution.y2021.d12.CaveTraversalRules.CaveType.SMALL_CAVE;
import static solution.y2021.d12.CaveTraversalRules.CaveVisitLimit.*;
import static solution.y2021.d12.CaveTraversalRules.CavesPerPathLimit.ALL_CAVES_PER_PATH;
import static solution.y2021.d12.CaveTraversalRules.CavesPerPathLimit.ONE_CAVE_PER_PATH;

public class Solution implements ISolution {

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        CaveMap caveMap = new CaveMap(input);
        CaveTraversalRules traversalRules =
                new CaveTraversalRules.Builder()
                        .addRuleForCaveType(BIG_CAVE)
                        .withCavesPerPathLimit(ALL_CAVES_PER_PATH)
                        .withVisitLimit(UNLIMITED)
                        .and()
                        .addRuleForCaveType(SMALL_CAVE)
                        .withCavesPerPathLimit(ALL_CAVES_PER_PATH)
                        .withVisitLimit(AT_MOST_ONCE)
                        .build();
        long answer = caveMap.countAllPaths(traversalRules);
        return Optional.of(String.valueOf(answer));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        CaveMap caveMap = new CaveMap(input);
        CaveTraversalRules traversalRules =
                new CaveTraversalRules.Builder()
                        .addRuleForCaveType(BIG_CAVE)
                        .withCavesPerPathLimit(ALL_CAVES_PER_PATH)
                        .withVisitLimit(UNLIMITED)
                        .and()
                        .addRuleForCaveType(SMALL_CAVE)
                        .withCavesPerPathLimit(ONE_CAVE_PER_PATH)
                        .withVisitLimit(AT_MOST_TWICE)
                        .build();
        long answer = caveMap.countAllPaths(traversalRules);
        return Optional.of(String.valueOf(answer));
    }
}