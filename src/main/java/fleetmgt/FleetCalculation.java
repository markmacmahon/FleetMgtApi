package fleetmgt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FleetCalculation {
    private final int fleetEngineers;
    private final int fleetManagerLocation;

    public FleetCalculation(final int fleetEngineers, final int fleetManagerLocation) {
        this.fleetEngineers = fleetEngineers;
        this.fleetManagerLocation = fleetManagerLocation;
    }

    @JsonProperty("fleet_engineers")
    public int getFleetEngineers() {
        return fleetEngineers;
    }

    @JsonProperty("fleet_manager_location")
    public int getFleetManagerLocation() {
        return fleetManagerLocation;
    }
}
