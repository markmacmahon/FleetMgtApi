package fleetmgt;


import org.springframework.util.Assert;

/**
 * Calculates the minimum number of Engineers given the provided constraints.
 * <p>
 * Note: This is not the most optimal implementation due to implementation time constraints.
 * In particular, it does not draw on any state of the Operations Research algorithms and uses a brute force approach to come up with the result.
 * Given the requirements (number of scooters and districts etc), the performance is likely good enough.
 * </p>
 */
public class FleetResourceCalculator {
    //FIXME - maybe springify this class and externalize constants to config class.
    /**
     * C will be between 1 and 999.
     */
    static final int MAX_MANAGER_CAPACITY = 999;
    /**
     * P will be between 1 and 1000.
     */
    static final int MAX_ENGINEER_CAPACITY = 1000;

    static final int MIN_RESOURCE_CAPACITY = 1;

    /**
     * scooters will contain between 1 and 100 elements.
     */
    static final int MAX_DISTRICTS = 100;

    /**
     * Each element in scooters will be between 0 and 1000.
     */
    static final int MAX_SCOOTERS_PER_DISTRICT = 1000;

    private final int managerCapacity;
    private final int engineerCapacity;
    private final int[] scootersPerDistrict;

    public FleetResourceCalculator(final FleetConstraints fleetConstraints) {
        Assert.notNull(fleetConstraints, "Invalid FleetConstraints");
        Assert.notNull(fleetConstraints.getScooters(), "No Scooters");
        Assert.isTrue(fleetConstraints.getScooters().length > 0, "No Scooters");
        Assert.isTrue(fleetConstraints.getScooters().length <= MAX_DISTRICTS, "Too Many Districts");
        this.managerCapacity = withinRange(fleetConstraints.getManagerCapacity(), MIN_RESOURCE_CAPACITY, MAX_MANAGER_CAPACITY);
        this.engineerCapacity = withinRange(fleetConstraints.getEngineerCapacity(), MIN_RESOURCE_CAPACITY, MAX_ENGINEER_CAPACITY);
        this.scootersPerDistrict = new int[fleetConstraints.getScooters().length];
        for (int i = 0; i < fleetConstraints.getScooters().length; i++) {
            scootersPerDistrict[i] = fleetConstraints.getScooters()[i];
            Assert.isTrue(scootersPerDistrict[i] <= MAX_SCOOTERS_PER_DISTRICT, "Too Many Scooters for district " + i);
            Assert.isTrue(scootersPerDistrict[i] >= 0, "Negative Scooters value for district " + i);
        }
    }

    private static int withinRange(final int providedValue, final int min, final int max) {
        if (providedValue < min) {
            return min;
        }
        if (providedValue > max) {
            return max;
        }
        return providedValue;
    }

    public FleetCalculation getFleetCalculation() {
        int minEngineers = Integer.MAX_VALUE;
        int managerIndex = 0;
        for (int district = 0; district < scootersPerDistrict.length; district++) {
            int current = countEngineersExcludingManagerDistrict(district);
            if (current < minEngineers) {
                minEngineers = current;
                managerIndex = district;
            }
        }
        return new FleetCalculation(minEngineers, managerIndex);
    }

    private int countEngineersExcludingManagerDistrict(final int managerDistrict) {
        int engineerCount = 0;
        for (int i = 0; i < scootersPerDistrict.length; i++) {
            final int slots = scootersPerDistrict[i];
            if (slots == 0) {
                //Ignore districts with no slots as we do not assign resources to them
                continue;
            }
            engineerCount += countEngineersForDistrict(slots, (i == managerDistrict));
        }
        return engineerCount;
    }

    private int countEngineersForDistrict(final int slots, final boolean isManager) {
        int engineerCount = 0;

        if (isManager) {
            if (slots > managerCapacity) { //otherwise manager can take care of the district
                final int slotsWithoutManager = slots - managerCapacity;
                final int minEngineers = (int) Math.ceil(slotsWithoutManager / (double) engineerCapacity);
                engineerCount += minEngineers;
            }
        } else {
            if (slots <= engineerCapacity) {
                engineerCount++;
            } else {
                engineerCount += (int) Math.ceil(slots / (double) engineerCapacity);
            }
        }
        return engineerCount;
    }
}
