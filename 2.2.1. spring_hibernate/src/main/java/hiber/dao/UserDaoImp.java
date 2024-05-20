package hiber.dao;

import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUser(String model, int series) {

        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("SELECT u FROM User u WHERE u.car.id IN (SELECT c.id FROM hiber.model.Car c WHERE c.model = :modelParam AND c.series = :seriesParam)", User.class);
            query.setParameter("modelParam", model);
            query.setParameter("seriesParam", series);

            User user = (User) query.getSingleResult();
            System.out.println(user.toString());
            return user;
        } catch (NoResultException | HibernateException e) {
            System.out.println("Пользователь с моделью " + model + "и серией " + series + " не найден");
            return null;
        }

    }

}
