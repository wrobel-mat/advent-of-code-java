package solution.y2021.d08;

import java.util.Set;

record Number(Set<Segment> segments) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number other = (Number) o;
        return this.segments.containsAll(other.segments);
    }

    @Override
    public int hashCode() {
        return segments.hashCode();
    }
}
