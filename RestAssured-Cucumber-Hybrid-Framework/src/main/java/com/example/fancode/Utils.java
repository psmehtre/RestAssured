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
