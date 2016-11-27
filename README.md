# FleetMgtApi
A Simple Spring Boot Example Fleet Management API.


### Installation

Install Java 8.

```
$ ./gradlew clean build

$ ./gradlew clean build && java -jar build/libs/FleetMgtApi-0.1.0.jar
```

### Usage

```
curl -XPOST -H 'Content-Type: application/json' 'localhost:9000/fleet-calculation' -d '
{
	"scooters": [15, 10],
	"C": 12,
	"P": 5
}
'
```

It responds with the following JSON:

```
{
	"fleet_engineers": 3,
	"fleet_manager_location": 0
}
```