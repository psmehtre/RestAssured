# RestAssured-Cucumber-Hybrid-Framework
This project is a hybrid framework using RestAssured for API testing and Cucumber for BDD (Behavior-Driven Development).

Prerequisites
Java 11 or higher
Maven 3.6 or higher
Project Structure

```
RestAssured-Cucumber-Hybrid-Framework/
│
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/fancode/
│   │   │       ├── Todo.java
│   │   │       ├── User.java
│   │   │       └── Utils.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   └── com/example/fancode/
│       │       ├── StepDefinitions.java
│       │       └── TestRunner.java
│       └── resources/
│           └── features/
│               └── FanCode.feature
└── README.md
```

# Dependencies
The pom.xml file includes the following dependencies:

OkHttp: For making HTTP requests
Gson: For JSON parsing
JUnit 5: For running the tests
Cucumber: For BDD testing
Lombok: For reducing boilerplate code

# Installation
1. Clone the repository
2. Build the project `mvn clean install`


# Running the Tests
To run the tests, execute: `mvn test`

# Detailed Explanation
pom.xml
Ensure your pom.xml includes the necessary dependencies and plugins:

```
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://www.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>RestAssured-Cucumber-Hybrid-Framework</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>5.0.0-alpha.14</version>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.11.0</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.11.0-M2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.11.0-M2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>7.18.0</version>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>7.18.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.28</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```


# TestRunner.java
The test runner is configured as follows:

```
package com.example.fancode;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.example.fancode",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class TestRunner {
}
```

# Utils.java
Utility class to fetch data from the API:

```
package com.example.fancode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Utils {
    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";
    private static final OkHttpClient client = new OkHttpClient();

    public static List<User> fetchUsers() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/users")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();
            Type userListType = new TypeToken<List<User>>(){}.getType();
            return new Gson().fromJson(responseBody, userListType);
        }
    }

    public static List<Todo> fetchTodos() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/todos")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();
            Type todoListType = new TypeToken<List<Todo>>(){}.getType();
            return new Gson().fromJson(responseBody, todoListType);
        }
    }

    public static boolean isUserInFanCodeCity(User user) {
        double lat = user.getAddress().getGeo().getLat();
        double lng = user.getAddress().getGeo().getLng();
        return lat >= -40 && lat <= 5 && lng >= 5 && lng <= 100;
    }

    public static double calculateCompletionPercentage(List<Todo> todos, int userId) {
        long totalTodos = todos.stream().filter(todo -> todo.getUserId() == userId).count();
        if (totalTodos == 0) return 0;
        long completedTodos = todos.stream().filter(todo -> todo.getUserId() == userId && todo.isCompleted()).count();
        return (double) completedTodos / totalTodos * 100;
    }
}
```

# StepDefinitions.java
Defines the Cucumber steps:

```
package com.example.fancode;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

    private List<User> users;
    private List<Todo> todos;

    @Given("User has the todo tasks")
    public void user_has_the_todo_tasks() throws IOException {
        todos = Utils.fetchTodos();
    }

    @When("User belongs to the city FanCode")
    public void user_belongs_to_the_city_fancode() throws IOException {
        users = Utils.fetchUsers();
    }

    @Then("User Completed task percentage should be greater than {int}%")
    public void user_completed_task_percentage_should_be_greater_than(int percentage) {
        for (User user : users) {
            if (Utils.isUserInFanCodeCity(user)) {
                double completionPercentage = Utils.calculateCompletionPercentage(todos, user.getId());
                assertTrue(completionPercentage > percentage, "User " + user.getName() + " does not have more than half of their todos completed!");
            }
        }
    }
}
```

# FanCode.feature
Feature file describing the test scenarios:

```
Feature: Validate completion percentage of todos for users in FanCode city

  Scenario: All users in FanCode should have more than half of their todos completed
    Given User has the todo tasks
    When User belongs to the city FanCode
    Then User Completed task percentage should be greater than 50%
```

# Running the Project
To run the tests and generate the report:

Clean and Install the Project: `mvn clean install`
Run the Tests: `mvn test`


# View the Report:

The HTML report will be generated in the target/cucumber-reports.html directory.

