package solution.y2025.d1;

record Rotation(char direction, int distance) {

    boolean isTowardsLeft() {
        return direction == 'L';
    }
}
