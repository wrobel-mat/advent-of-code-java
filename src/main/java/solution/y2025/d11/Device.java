package solution.y2025.d11;

record Device(String label) {

    static Device of(String label) {
        return new Device(label);
    }
}
