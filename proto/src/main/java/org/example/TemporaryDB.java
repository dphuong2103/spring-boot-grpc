package org.example;

import com.grpc.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemporaryDB {
    public static List<User> users = Arrays.asList(
            User.newBuilder().setId("User1").setName("User 1").build(),
            User.newBuilder().setId("User2").setName("User 2").build(),
            User.newBuilder().setId("User3").setName("User 3").build()
            );
}
