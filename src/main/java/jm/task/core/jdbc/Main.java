package jm.task.core.jdbc;

import jm.task.core.jdbc.util.HibernateUtil;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        try {
            userService.createUsersTable();
            userService.saveUser("Пётр", "Иванов", (byte) 23);
            userService.saveUser("Иван", "Пётров", (byte) 32);
            userService.saveUser("Василий", "Игнатов", (byte) 45);
            userService.saveUser("Игнат", "Васильев", (byte) 54);
            userService.getAllUsers().forEach(x -> System.out.println(x.toString()));
            userService.cleanUsersTable();
            userService.dropUsersTable();
        } finally {
            HibernateUtil.closeSessionFactory();
        }
    }
}
