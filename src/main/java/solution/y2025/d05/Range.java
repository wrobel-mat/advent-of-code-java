package solution.y2025.d05;

record Range(long start, long end) implements Comparable<Range> {

    @Override
    public int compareTo(Range other) {
        return Long.compare(this.start, other.start);
    }
}
