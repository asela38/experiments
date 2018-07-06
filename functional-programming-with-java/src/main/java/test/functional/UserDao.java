package test.functional;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    @Autowired
    private NamedParameterJdbcTemplate template;

    public List<User> getAllUsers() {
        String selectQuery = "                                               "
                + " SELECT                                                   "
                + "     first_name ,                                         "
                + "     last_name,                                           "
                + "     date_of_birth                                        "
                + " FROM user                                                ";

        return template.query(selectQuery.replaceAll("\\s+", " "), Collections.emptyMap(), (RowMapper<User>) (rs, rowNum) -> {
            User user = new User();
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setBirthDate(rs.getDate("date_of_birth").toLocalDate());
            return user;
        });
    }

}
