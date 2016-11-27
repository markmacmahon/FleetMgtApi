package fleetmgt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FleetConstraints {
    private int[] scooters;
    private int managerCapacity; //C
    private int engineerCapacity; //P

    public FleetConstraints(final int[] scooters, final int managerCapacity, final int engineerCapacity) {
        this.scooters = scooters;
        this.managerCapacity = managerCapacity;
        this.engineerCapacity = engineerCapacity;
    }

    public FleetConstraints() {
    }

    public int[] getScooters() {
        return scooters;
    }

    public void setScooters(int[] scooters) {
        this.scooters = scooters;
    }

    public int getManagerCapacity() {
        return managerCapacity;
    }

    @JsonProperty("C")
    public void setManagerCapacity(int managerCapacity) {
        this.managerCapacity = managerCapacity;
    }

    public int getEngineerCapacity() {
        return engineerCapacity;
    }

    @JsonProperty("P")
    public void setEngineerCapacity(int engineerCapacity) {
        this.engineerCapacity = engineerCapacity;
    }
}


