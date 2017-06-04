Problem
=======

We have a collection of persons. Each person has name and birth date.

Our task is to write standalone Java 8 application that will read that collection from a JSON file and filter it.

Filter should be provided as a parameter. Application should write results to stdout.

Usage
=====
Assuming Git and Maven is installed in the system

Get and compile the code:
```
git clone https://github.com/eddie2k/people-loader.git
cd people-loader
mvn clean package
```

```
java -jar target/people-loader-1.0-jar-with-dependencies.jar -f sample.json
```

Or
```
java -jar target/people-loader-1.0-jar-with-dependencies.jar -f sample.json -e “name == ‘John_Lennon’”
```

Notes
=====
-Names in the JSON file must not contain white spaces
-Birthdates must follow the format YYYY-MM-DD
