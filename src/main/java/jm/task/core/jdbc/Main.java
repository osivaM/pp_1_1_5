package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Petr", "Petrov", (byte) 20);
        userService.saveUser("Nikolay", "Nikolaev", (byte) 21);
        userService.saveUser("Semen", "Semenov", (byte) 22);
        userService.saveUser("Viktor", "Viktorov", (byte) 23);

        for (User u : userService.getAllUsers()) {
            System.out.println(u.toString());
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
