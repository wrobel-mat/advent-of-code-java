package solution.y2021.d12;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Cave {

    private final String name;
    private final Set<Cave> connectedCaves;

    Cave(String name) {
        this.name = name;
        this.connectedCaves = new HashSet<>();
    }

    void connectsTo(Cave other) {
        connectedCaves.add(other);
    }

    String name() {
        return name;
    }

    Set<Cave> connectedCaves() {
        return new HashSet<>(connectedCaves);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cave cave = (Cave) o;
        return Objects.equals(name, cave.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
