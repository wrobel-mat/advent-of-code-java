package solution.y2025.d08;

import java.util.HashSet;
import java.util.Set;

record Circuit(Set<JunctionBox> junctionBoxes) implements Comparable<Circuit> {

    Circuit {
        if (junctionBoxes.isEmpty()) {
            throw new IllegalArgumentException("Circuit should have at least 1 junction box");
        }
    }

    boolean connectsWith(Circuit other) {
        return other.junctionBoxes.stream().anyMatch(this.junctionBoxes::contains);
    }

    Circuit union(Circuit other) {
        Set<JunctionBox> union = new HashSet<>();
        union.addAll(this.junctionBoxes);
        union.addAll(other.junctionBoxes);
        return new Circuit(union);
    }

    int size() {
        return this.junctionBoxes.size();
    }

    @Override
    public int compareTo(Circuit other) {
        return Integer.compare(this.junctionBoxes.size(), other.junctionBoxes.size());
    }
}
