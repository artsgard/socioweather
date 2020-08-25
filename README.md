# socioweather
A Springboot REST Application of City Weather Reports for Socios

1) General Info About the Socio-Micro-Service-Demo

2) Specific Info Concerning Each Single Application



General Info

The Socio Micro Services Project will consist of about 10 small (backend) Springboot application, deployed in a Docker Container. SocioRegister is the principal part of a series of four application (starter, mock, jpa, socioregister). Together they show a stepwise buildup to a Springboot REST application, which contains use-cases of registering and adding Socios (similar to Facebook). The line goes from an almost empty shell Springboot (starter: one controller one method only) to a small but full-fledged REST application, SocioRegister.

Next you`ll find four serving application. The simple SocioWeather, provides the weather-report by city. SocioBank, permits money transaction between Socios. The SocioSecurity, a Cookie/ Token based SpringSecurity (OATH2), still has to be written. Finally the SocioDbBatch application is interesting. It will update, on a daily bases, the databases of SocioRegister (socio_db) and SocioBank (soicio_bank_db). The DBs run on MySQL or Postgres.

Specific Info SocioWeather
