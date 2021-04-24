package user;


import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        UserSupplier userSupplier = new UserSupplier(new Locale("hu"));
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDAO dao = handle.attach(UserDAO.class);
            dao.createTable();

            for (int i = 0; i < 10; i++) {
                dao.insertUser(userSupplier.getUser());
            }

            Long id = dao.insertUser(userSupplier.getUser());

            Optional<User> optionalUser = dao.getUser(id);
            optionalUser.ifPresent(a -> System.out.println(a.toString()));
            User user;
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
                System.out.println(dao.getUser(user.getUsername()).toString());
                dao.deleteUser(user);
            }


            List<User> userList = dao.listUser();
            userList.forEach(System.out::println);
            System.err.println(userList.size());


        }
    }
}
