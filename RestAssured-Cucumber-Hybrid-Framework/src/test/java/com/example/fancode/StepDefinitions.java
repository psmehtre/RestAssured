package com.example.fancode;

import io.cucumber.java.en.*;

import static org.junit.Assert.*;

import io.cucumber.junit.CucumberOptions;

import java.util.List;

public class StepDefinitions {

    private List<User> users;
    private List<Todo> todos;

    @Given("I fetch all users")
    public void i_fetch_all_users() throws Exception {
        users = Utils.fetchUsers();
        assertNotNull(users);
    }

    @Given("I fetch all todos")
    public void i_fetch_all_todos() throws Exception {
        todos = Utils.fetchTodos();
        assertNotNull(todos);
    }

    @When("I filter users in FanCode city")
    public void i_filter_users_in_fancode_city() {
        users = users.stream().filter(Utils::isUserInFanCodeCity).toList();
    }

    @Then("I verify each user has more than half of their todos completed")
    public void i_verify_each_user_has_more_than_half_of_their_todos_completed() {
        for (User user : users) {
            double completionPercentage = Utils.calculateCompletionPercentage(todos, user.getId());
            System.out.printf("User %s has completed %.2f%% of their todos.%n", user.getName(), completionPercentage);
            assertTrue("User " + user.getName() + " does not have more than half of their todos completed!", completionPercentage > 50);
        }
    }
}
