package solution.y2025.d8;

import java.util.Set;

class Pair implements Comparable<Pair> {

    private final JunctionBox a;
    private final JunctionBox b;
    private final Distance distance;

    Pair(JunctionBox a, JunctionBox b) {
        this.a = a;
        this.b = b;
        this.distance = Distance.between(a, b);
    }

    JunctionBox a() {
        return a;
    }

    JunctionBox b() {
        return b;
    }

    @Override
    public int compareTo(Pair other) {
        return Double.compare(this.distance.val(), other.distance.val());
    }

    Circuit toCircuit() {
        return new Circuit(Set.of(a, b));
    }
}
