package solution.y2025.d1;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        List<Rotation> rotations = getRotations(input);
        Safe safe = new Safe(50);
        String password = safe.findPassword(rotations, new CountingZerosPasswordResolver());
        return Optional.of(password);
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        List<Rotation> rotations = getRotations(input);
        Safe safe = new Safe(50);
        String password = safe.findPassword(rotations, new CountingPassesThroughZeroPasswordResolver());
        return Optional.of(password);
    }

    private List<Rotation> getRotations(List<String> input) {
        return input.stream()
                .map(rotation ->
                        new Rotation(
                                rotation.charAt(0),
                                Integer.parseInt(rotation.substring(1))))
                .toList();
    }
}