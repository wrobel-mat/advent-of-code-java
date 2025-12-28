package solution.y2021.d06;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class LanternFishSpawnSimulation {

    private final Map<LanternFishSpawnTimer, Long> timersCountMap;

    LanternFishSpawnSimulation(String input) {
        this.timersCountMap = Arrays.stream(input.split(","))
                .map(Integer::valueOf)
                .map(LanternFishSpawnTimer::new)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private LanternFishSpawnSimulation(Map<LanternFishSpawnTimer, Long> timersCountMap) {
        this.timersCountMap = timersCountMap;
    }

    LanternFishSpawnSimulation simulateSpawn(int days) {
        Map<LanternFishSpawnTimer, Long> simulation = new HashMap<>(this.timersCountMap);
        Map<LanternFishSpawnTimer, Long> cache = new HashMap<>();
        IntStream.range(0, days).forEach(day -> {
            simulation.forEach((timer, fishCount) -> {
                Optional<LanternFishSpawnTimer> optionalOffspringTimer = timer.spawnOffspring();
                optionalOffspringTimer.ifPresent(offspringTimer -> cache.put(offspringTimer, fishCount));
                cache.put(timer.decrementOrReset(),
                        cache.getOrDefault(timer.decrementOrReset(), 0L) + fishCount);
            });
            simulation.clear();
            simulation.putAll(cache);
            cache.clear();
        });
        return new LanternFishSpawnSimulation(simulation);
    }

    long countPopulation() {
        return timersCountMap.values().stream().reduce(Long::sum).orElse(0L);
    }
}
