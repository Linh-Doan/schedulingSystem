# schedulingSystem
Based on the observation of fellow students often forgetting about assessments or starting to work on them too late, this system is delevoped to use the scheduling system (https://github.com/Linh-Doan/schedulingSystem) to retrieve data about upcoming assessment dates for each individual and generate a plan on when to start the assessments. Having its own database, this micro-service can still function when the enrolment system goes down though its data may not be the most uptodate.

A postman collection (https://github.com/Linh-Doan/schedulingSystem/blob/main/Scheduling_system.postman_collection.json) is generated for ease of testing its functionality.

H2 and postgres are 2 database options. H2 does not need to be set up. To run this using postgres, download postgreSQL (https://www.postgresql.org/download/), then create a database with a name of choice (this can omitted if already done for enrolment system). The default port for postgreSQL is 5432 so if your machine has an existing program running in this, the port option can be changed while setting up postgreSQL. When connecting your Java IDE to postgreSQL, choose the authentification method as User and Password and leave them both blank. In application.properties file, change the value of spring.datasource.url to jdbc:postgresql://localhost:{port_used}/{database_name}.

Technologies used:
- Spring boot
- Postgresql database
- Postman
- JUnit
