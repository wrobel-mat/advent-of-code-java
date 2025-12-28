package solution.y2025.d01;

record Rotation(char direction, int distance) {

    boolean isTowardsLeft() {
        return direction == 'L';
    }
}
