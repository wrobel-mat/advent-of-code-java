package solution.y2025.d8;

record Distance(double val) {

    public static Distance between(JunctionBox a, JunctionBox b) {
        long xDiff = a.x() - b.x();
        long yDiff = a.y() - b.y();
        long zDiff = a.z() - b.z();
        double distance = Math.sqrt(Math.powExact(xDiff, 2) + Math.powExact(yDiff, 2) + Math.powExact(zDiff, 2));
        return new Distance(distance);
    }
}
