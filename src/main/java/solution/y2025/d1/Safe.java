package solution.y2025.d1;

import java.util.List;

class Safe {

    private int dial;

    Safe(int dial) {
        this.dial = dial;
    }

    String findPassword(List<Rotation> rotations, PasswordResolver passwordResolver) {
        for (Rotation rotation : rotations) {
            dial = passwordResolver.rotateDial(dial, rotation);
        }
        return passwordResolver.getPassword();
    }
}
