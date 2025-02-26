package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.*;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session session = sessionFactory.getCurrentSession();

    private void doBeginAndGetTransaction(Runnable foo) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        foo.run();
        session.getTransaction().commit();
    }

    @Override
    public void createUsersTable() {
        doBeginAndGetTransaction(() -> session.createNativeQuery("create table if not exists Users (id bigint not null" +
                " auto_increment, age tinyint, lastName varchar(255)," +
                " name varchar(255), primary key (id)) engine=InnoDB").executeUpdate());
    }

    @Override
    public void dropUsersTable() {
        doBeginAndGetTransaction(() -> session.createNativeQuery("drop table if exists users;")
                .executeUpdate());
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        doBeginAndGetTransaction(() -> session.save(new User(name, lastName, age)));
    }

    @Override
    public void removeUserById(long id) {
        doBeginAndGetTransaction(() -> session.delete((User) session.get(User.class, id)));
    }

    @Override
    public List<User> getAllUsers() {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            return session.createQuery("from User").getResultList();
        } finally {
            session.getTransaction().commit();
        }
    }

    @Override
    public void cleanUsersTable() {
        doBeginAndGetTransaction(() -> session.createNativeQuery("truncate  users;").executeUpdate());
    }
}
