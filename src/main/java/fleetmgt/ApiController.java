package fleetmgt;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApiController {

    @RequestMapping(path = "/fleet-calculation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<FleetCalculation> calculate(@RequestBody FleetConstraints fleetConstraints) {
        final FleetCalculation fleetCalculation = new FleetResourceCalculator(fleetConstraints).getFleetCalculation();
        return new ResponseEntity<>(fleetCalculation, HttpStatus.OK);
    }

}
