package user;

import com.github.javafaker.Faker;
import org.apache.commons.codec.digest.DigestUtils;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class UserSupplier {


    Faker faker;

    public UserSupplier(Locale locale) {
        faker = new Faker(locale);
    }

    public User getUser() {

        return User.builder()
                .username(faker.name().username())
                .password(DigestUtils.md5Hex(faker.internet().password()))
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .gender(faker.options().option(User.Gender.values()))
                .birthDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .enabled(faker.bool().bool())
                .build();
    }


}
