package solution.y2021.d12;

import java.util.List;
import java.util.stream.Stream;

class CavePath {

    private final List<Cave> steps;
    private final CaveTraversalRules traversalRules;

    private CavePath(List<Cave> steps, CaveTraversalRules traversalRules) {
        if (!traversalRules.validate(steps)) {
            throw new IllegalArgumentException("CavePath steps must satisfy provided CaveTraversalRules");
        }

        this.steps = steps;
        this.traversalRules = traversalRules;
    }

    static CavePath of(List<Cave> steps, CaveTraversalRules traversalRules) {
        return new CavePath(steps, traversalRules);
    }

    CavePath addNextStep(Cave step) {
        return of(Stream.concat(steps.stream(), Stream.of(step)).toList(), traversalRules);
    }

    boolean canTraverseToNextCave(Cave potentialNextCave) {
        return traversalRules.validate(Stream.concat(steps.stream(), Stream.of(potentialNextCave)).toList());
    }
}
