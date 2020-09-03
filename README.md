# socioweather
A Springboot REST Application of City Weather Reports for Socios

1) General Info About the Socio-Micro-Service-Demo

2) Specific Info Concerning Each Single Application



## General Info ====

The Socio Micro Services Project will consist of about 10 small (backend) Springboot applications, deployed in a Docker Container/ Linux Oracle Virtual Box. SocioRegister is the principal part of a series of four applications called: starter, mock, jpa, socioregister. Together they show a stepwise buildup to a Springboot REST application, which contains use-cases for registering and adding Socios (similar to Facebook). This line of applications goes from an almost empty Springboot shell (starter: one controller method only) to a small but full-fledged REST application: SocioRegister which will be used as a component of our micro-services.

Next you`ll find four other serving applications. The simple SocioWeather, provides a weather-report by city by consulting an external REST-service called Open Weather. SocioBank, permits money transaction between Socios, also consulting an external service for exchange rates. The SocioSecurity, a Cookie/ Token based SpringSecurity (OAUTH2), still has to be written. Finally the SocioDbBatch application is interesting because it will update, on a daily bases, the databases of SocioRegister (socio_db) and SocioBank (soicio_bank_db). The DBs run on MySQL or Postgres.

From SocioRegister-jpa one finds backend-Validation (javax) and REST-Exception Handeling of Spring (RestControllerAdvice).

Testing, in general, will have an important focus and since we are dealing with Spring(boot) there will systematically testing based on five mayor strategies:

	-@ExtendWith(MockitoExtension.class)

	-@ExtendWith(SpringExtension.class) standalone setup (two ways)

	-@ExtendWith(SpringExtension.class) server tests (@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

	-@DataJpaTest wich is database testing on H2

	-Spring Batch testing

Testing is still "work in progress"



## Specific Info SocioWeather ====

This small Weather-Service provides a report per City of its current weather conditions. The service consults a external service called OpenWeather and has a single get-url inserting the city-name e.g.:

	http://localhost:8083/Newfoundland
	
The service will return a json with the following fields:

	{
		"description": "few clouds",
		"main": "Clouds",
		"temp": "14.9",
		"tempMax": "14.9",
		"tempMin": "14.9",
		"humidity": "58",
		"pressure": "1015",
		"clouds": "22",
		"wind": "4.58",
		"city": "Newfoundland",
		"weatherTypeTachs": [
			"COLD",
			"CLOUDY"
		]
}

the weather tags:

	public enum WeatherType {
        	RAINY, SNOWING, CLOUDY, COLD, SUNNY, WARM, HOT, FREEZING, CLEAR, MISTY, WINDY, STORMY, DANGEROUS_STORM
    	}
	
When no city is found it will throw a CityNotFound exception, to be communicated to the front.

The city-field could be used to provide info/ pictures of that selected city. And the list of weather tags may have a similar function.

There is little to say concerning this small Springboot REST-application. There will be some exception handling concerning the external service connection. And there are only view test-units of which the testGetWeatherTagList method is the most interesting one.

