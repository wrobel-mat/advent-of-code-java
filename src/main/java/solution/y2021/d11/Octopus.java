package solution.y2021.d11;

import java.util.ArrayList;
import java.util.List;

class Octopus {

    private static final int MAX_ENERGY_LEVEL = 9;
    private static final int RESET_ENERGY_LEVEL = 0;
    private int energy;
    private final List<Octopus> adjacents = new ArrayList<>();
    private OctopusLocation location;

    Octopus(int energy) {
        this.energy = energy;
    }

    void incrementEnergy() {
        energy = energy == MAX_ENERGY_LEVEL ? RESET_ENERGY_LEVEL : ++energy;
    }

    int getEnergy() {
        return energy;
    }

    List<Octopus> getAdjacents() {
        return new ArrayList<>(adjacents);
    }

    OctopusLocation getLocation() {
        return location;
    }

    void addAdjacent(Octopus octopus) {
        adjacents.add(octopus);
    }

    void setLocation(OctopusLocation octopusLocation) {
        this.location = octopusLocation;
    }
}
