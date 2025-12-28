package solution.y2025.d01;

class CountingZerosPasswordResolver implements PasswordResolver {

    private int password = 0;

    @Override
    public int rotateDial(int dial, Rotation rotation) {
        if (rotation.isTowardsLeft()) {
            dial -= rotation.distance();
            if (dial < 0) {
                dial = (100 - Math.abs(dial) % 100) % 100;
            }
        } else {
            dial += rotation.distance();
            dial %= 100;
        }
        if (dial == 0) {
            password++;
        }
        return dial;
    }

    @Override
    public String getPassword() {
        return String.valueOf(password);
    }
}
