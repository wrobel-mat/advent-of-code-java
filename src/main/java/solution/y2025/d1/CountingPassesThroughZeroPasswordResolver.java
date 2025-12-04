package solution.y2025.d1;

class CountingPassesThroughZeroPasswordResolver implements PasswordResolver {

    private int password = 0;

    @Override
    public int rotateDial(int dial, Rotation rotation) {
        int distance = rotation.distance();
        if (rotation.isTowardsLeft()) {
            dial -= distance;
            if (dial == 0) {
                password++;
            } else if (dial == -distance) {
                int abs = Math.abs(dial);
                dial = (100 - abs % 100) % 100;
                int numPassesThroughZero = Math.floorDiv(abs, 100);
                password += numPassesThroughZero;
            } else if (dial < 0) {
                int abs = Math.abs(dial);
                dial = (100 - abs % 100) % 100;
                if (dial == 0) {
                    password++;
                }
                int numPassesThroughZero = Math.ceilDiv(abs, 100);
                password += numPassesThroughZero;
            }
        } else {
            dial += distance;
            int numPassesThroughZero = Math.floorDiv(dial, 100);
            password += numPassesThroughZero;
            dial %= 100;
        }
        return dial;
    }

    @Override
    public String getPassword() {
        return String.valueOf(password);
    }
}
