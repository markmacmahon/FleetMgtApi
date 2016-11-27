package fleetmgt;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for for {@link FleetResourceCalculator}.
 */
public class FleetResourceCalculatorTest {

    @Test
    public void shouldReturn3EngineersForExample1() throws Exception {
        // given:
        final FleetConstraints fleetConstraints = new FleetConstraints(new int[]{15, 10}, 12, 5);
        final int expectedOutcome = 3;

        // when:
        final FleetCalculation fleetCalculation = new FleetResourceCalculator(fleetConstraints).getFleetCalculation();

        // then:
        assertThat(fleetCalculation.getFleetEngineers()).isEqualTo(expectedOutcome);
    }

    @Test
    public void shouldReturn7EngineersForExample2() throws Exception {
        // given:
        final FleetConstraints fleetConstraints = new FleetConstraints(new int[]{11, 15, 13}, 9, 5);
        final int expectedOutcome = 7;

        // when:
        final FleetCalculation fleetCalculation = new FleetResourceCalculator(fleetConstraints).getFleetCalculation();

        // then:
        assertThat(fleetCalculation.getFleetEngineers()).isEqualTo(expectedOutcome);
    }

    @Test
    public void shouldReturnManagerDistrict() throws Exception {
        // given:
        final FleetConstraints fleetConstraints = new FleetConstraints(new int[]{13, 15, 22}, 200, 5);
        final int expectedOutcome = 7;
        final int expectedDistrict = 2; //not deterministic if there are other candidate matches

        // when:
        final FleetCalculation fleetCalculation = new FleetResourceCalculator(fleetConstraints).getFleetCalculation();

        // then:
        assertThat(fleetCalculation.getFleetEngineers()).isEqualTo(expectedOutcome);
        assertThat(fleetCalculation.getFleetManagerLocation()).isEqualTo(expectedDistrict);
    }

    @Test
    public void shouldRevertToMaxManagerCapacity() throws Exception {
        // given:
        final FleetConstraints fleetConstraintsValid = new FleetConstraints(new int[]{13, 15, 22}, FleetResourceCalculator.MAX_MANAGER_CAPACITY, 5);
        final FleetConstraints fleetConstraints = new FleetConstraints(new int[]{13, 15, 22}, Integer.MAX_VALUE, 5);
        int expectedOutcome = new FleetResourceCalculator(fleetConstraintsValid).getFleetCalculation().getFleetEngineers();

        // when:
        final FleetCalculation fleetCalculation = new FleetResourceCalculator(fleetConstraints).getFleetCalculation();

        // then:
        assertThat(fleetCalculation.getFleetEngineers()).isEqualTo(expectedOutcome);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeScooterCounts() throws Exception {
        // given:
        final FleetConstraints fleetConstraints = new FleetConstraints(new int[]{-1, 15, 22}, 200, 5);

        // when:
        new FleetResourceCalculator(fleetConstraints).getFleetCalculation();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectScooterCountOverLimit() throws Exception {
        // given:
        final FleetConstraints fleetConstraints = new FleetConstraints(new int[]{FleetResourceCalculator.MAX_SCOOTERS_PER_DISTRICT + 1, 15, 22}, 200, 5);

        // when:
        new FleetResourceCalculator(fleetConstraints).getFleetCalculation();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullScooters() {
        // given:
        final FleetConstraints fleetConstraints = new FleetConstraints(null, 200, 5);

        // when:
        new FleetResourceCalculator(fleetConstraints).getFleetCalculation();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectEmptyScooters() {
        // given:
        final FleetConstraints fleetConstraints = new FleetConstraints(new int[0], 200, 5);

        // when:
        new FleetResourceCalculator(fleetConstraints).getFleetCalculation();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectTooManyDistricts() {
        // given:
        final FleetConstraints fleetConstraints = new FleetConstraints(new int[FleetResourceCalculator.MAX_DISTRICTS + 1], 200, 5);

        // when:
        new FleetResourceCalculator(fleetConstraints).getFleetCalculation();
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullConstraints() {
        // given:
        final FleetConstraints fleetConstraints = null;
        // when:
        new FleetResourceCalculator(fleetConstraints).getFleetCalculation();
    }

}
