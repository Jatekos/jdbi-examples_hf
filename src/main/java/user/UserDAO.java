package user;


import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;

import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(User.class)
public interface UserDAO {

    @SqlUpdate("""
            CREATE TABLE user (
                id IDENTITY primary key,
                username VARCHAR,
                password VARCHAR,
                name VARCHAR,
                email VARCHAR,
                gender  ENUM('FEMALE','MALE'),
                birthDate DATE ,
                enabled BOOLEAN
                 
             
            )
            """
    )
    void createTable();

    @SqlUpdate("INSERT INTO user VALUES (:id, :username,:password, :name, :email, :gender,:birthDate,:enabled)")
    @GetGeneratedKeys
    long insertUser(@BindBean User user);

    @SqlQuery("SELECT * FROM user WHERE id = :id")
    Optional<User> getUser(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM user WHERE username = :username")
    Optional<User> getUser(@Bind("username") String username);

    @SqlUpdate("DELETE  FROM user WHERE id=:id")
    void deleteUser(@BindBean User user);

    @SqlQuery("SELECT * FROM user ")
    List<User> listUser();
}
