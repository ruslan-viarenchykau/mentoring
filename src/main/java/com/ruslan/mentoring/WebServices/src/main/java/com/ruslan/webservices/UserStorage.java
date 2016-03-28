package com.ruslan.webservices;

import java.util.*;

public final class UserStorage {
    private static Map<String, User> storage;

    static {
        storage = new HashMap<String, User>();
    }

    private UserStorage() {}

    public static User addUser(String id, User user) {
        if (!storage.containsKey(id)) {
            storage.put(id, user);
            return user;
        }
        return null;
    }

    public static User getUser(String id) {
        return storage.get(id);
    }

    public static User putUser(String id, User user) {
        User updatedUser = storage.put(id, user);
        return updatedUser != null ? updatedUser : user;
    }

    public static User deleteUser(String id) {
        return storage.remove(id);
    }

    public static void clear() {
        storage.clear();
    }

    public static List<User> getUsers() {
        return new ArrayList<User>() {{
            addAll(storage.values());
        }};
    }

    public static List<String> getUserIds() {
        return new ArrayList<String>() {{
            addAll(storage.keySet());
        }};
    }
}
