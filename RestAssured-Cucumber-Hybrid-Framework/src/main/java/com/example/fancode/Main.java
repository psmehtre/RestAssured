package com.example.fancode;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<User> users = Utils.fetchUsers();
            List<Todo> todos = Utils.fetchTodos();

            users.stream()
                .filter(Utils::isUserInFanCodeCity)
                .forEach(user -> {
                    double completionPercentage = Utils.calculateCompletionPercentage(todos, user.getId());
                    System.out.printf("User %s has completed %.2f%% of their todos.%n", user.getName(), completionPercentage);
                    if (completionPercentage <= 50) {
                        System.out.printf("User %s does not have more than half of their todos completed!%n", user.getName());
                    }
                });
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
