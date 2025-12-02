package solution.y2021.d7;

record CrabSwarmSimulator(CrabSubmarineFuelUsageCalculator fuelUsageCalculator) {

    long simulateSwarmAlignmentWithLowestFuelUsage(CrabSwarm crabSwarm) {
        long lowestFuelUsage = Long.MAX_VALUE;
        for (CrabSubmarinePosition submarine1 : crabSwarm.submarines()) {
            long currentFuelUsage = 0;
            for (CrabSubmarinePosition submarine2 : crabSwarm.submarines()) {
                if (!submarine2.onTheSamePositionAs(submarine1)) {
                    currentFuelUsage += fuelUsageCalculator.apply(submarine2.measureDistanceFrom(submarine1));
                }
            }
            lowestFuelUsage = Math.min(currentFuelUsage, lowestFuelUsage);
        }
        return lowestFuelUsage;
    }
}
