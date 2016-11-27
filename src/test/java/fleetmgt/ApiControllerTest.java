/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fleetmgt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for for {@link ApiController}.
 * <p>
 * Note: Just testing the happy path here as most of the tests are in  {@link FleetResourceCalculatorTest}.
 * </p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class ApiControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnFleetCalculation() throws Exception {
        //given
        final FleetConstraints fleetConstraints = new FleetConstraints(new int[]{15, 10}, 12, 5);

        // when:
        final ResponseEntity<Map> response = testRestTemplate.exchange("/fleet-calculation", HttpMethod.POST, new HttpEntity<>(fleetConstraints), Map.class);

        // then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).containsKey("fleet_engineers");
        assertThat(response.getBody()).containsKey("fleet_manager_location");
    }

}
