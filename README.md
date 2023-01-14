# Action_execution.java

Hey! Welcome to my simple Spring Boot application for an imaginary action execution.

Application contains a scheduler service that runs at the time(s) specified in Spring Boot configuration file (evey minute by default but can be changed) and a CSV file containing 2 columns:
  1) time - a time in the HH:MI format specifying time when the action shoud be executed
2) bitmask - a bitmask specified in numeric format. Bitmask will be representing days of the week when the action should be made. For example value 1 (0x01) means that action should be made on Monday, value 5 (0x5) means Monday and Wednesady.

In each scheduler run cycle application will load data from the file, parse it and determine should the action be done at the current time in Lagos, Nigeria.

To change the time a scheduler service will run, you can use `application.properties` file and change `scheduler.cron=0 * * * * *` values.

To run application:

> clone this repository `https://github.com/IKromans/Action_execution.java.git`

> open terminal in cloned folder and run command `mvnw spring-boot:run` from console.

> if time and bitmask in `scheduler.csv` file will correspond to the day and time of the week, the program will print message "Executing action.".

![Screenshot 2023-01-15 000730](https://user-images.githubusercontent.com/66387211/212499191-0ac9a0dc-a626-4f74-b48e-e3d990631b66.jpg)

> To stop application press `Ctrl + C`.

Enjoy!
