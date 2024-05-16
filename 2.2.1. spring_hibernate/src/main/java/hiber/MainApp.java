package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;


public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        User user = new User("pavel", "lazarchuk", "ogheat1337@gmail.com");
        Car car = new Car("tesla model", 3);
        user.setCar(car);
        userService.add(user);

        User user1 = new User("ivan", "ivanov", "ivan1337@gmail.com");
        Car car1 = new Car("vaz", 2107);
        user1.setCar(car1);
        userService.add(user1);

        List<User> usersList = userService.listUsers();
        for (User users : usersList) {
            System.out.println("Id = " + users.getId());
            System.out.println("First Name = " + users.getFirstName());
            System.out.println("Last Name = " + users.getLastName());
            System.out.println("Email = " + users.getEmail());
            System.out.println();
        }

        userService.getUserWithCar("tesla model", 3);
        userService.getUserWithCar("cherry tiggo", 4);
        userService.getUserWithCar("vaz", 2107);


        context.close();
    }
}
