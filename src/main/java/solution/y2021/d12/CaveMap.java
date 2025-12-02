package solution.y2021.d12;

import java.util.*;

class CaveMap {

    private final Map<String, Cave> cavesMap;
    private final Set<CavePath> cavePaths;

    CaveMap(List<String> input) {
        cavesMap = new HashMap<>();
        for (String pair : input) {
            List<Cave> cavesPair = Arrays.stream(pair.split("-")).map(Cave::new).toList();
            Cave first = cavesMap.getOrDefault(cavesPair.getFirst().name(), cavesPair.getFirst());
            Cave second = cavesMap.getOrDefault(cavesPair.getLast().name(), cavesPair.getLast());
            first.connectsTo(second);
            second.connectsTo(first);
            cavesMap.putIfAbsent(first.name(), first);
            cavesMap.putIfAbsent(second.name(), second);
        }
        cavePaths = new HashSet<>();
    }

    long countAllPaths(CaveTraversalRules traversalRules) {
        findAllPathsBetween(cavesMap.get("start"), cavesMap.get("end"), traversalRules);
        return cavePaths.size();
    }

    private void findAllPathsBetween(Cave start, Cave end, CaveTraversalRules traversalRules) {
        CavePath initialPath = CavePath.of(List.of(start), traversalRules);
        for (Cave adjacent : start.connectedCaves()) {
            walkCavePathRecursively(adjacent, end, initialPath);
        }
    }

    private void walkCavePathRecursively(Cave current, Cave end, CavePath initialPath) {
        if (!initialPath.canTraverseToNextCave(current)) {
            return;
        }
        if (current.equals(end)) {
            cavePaths.add(initialPath.addNextStep(end));
            return;
        }
        for (Cave adjacent : current.connectedCaves()) {
            walkCavePathRecursively(adjacent, end, initialPath.addNextStep(current));
        }
    }
}
