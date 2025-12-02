package solution.y2021.d6;

import java.util.Objects;
import java.util.Optional;

record LanternFishSpawnTimer(int daysLeftToSpawn) {

    private static final int ADULT_SPAWN_DAYS = 6;
    private static final int NEW_BORN_SPAWN_DAYS = 8;

    LanternFishSpawnTimer decrementOrReset() {
        if (daysLeftToSpawn == 0) {
            return new LanternFishSpawnTimer(ADULT_SPAWN_DAYS);
        }
        return new LanternFishSpawnTimer(daysLeftToSpawn - 1);
    }

    Optional<LanternFishSpawnTimer> spawnOffspring() {
        if (daysLeftToSpawn == 0) {
            return Optional.of(new LanternFishSpawnTimer(NEW_BORN_SPAWN_DAYS));
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanternFishSpawnTimer that = (LanternFishSpawnTimer) o;
        return daysLeftToSpawn == that.daysLeftToSpawn;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(daysLeftToSpawn);
    }
}
