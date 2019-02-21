package org.newdevelopment.ca.data.dao.mapper;

import org.newdevelopment.ca.data.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.newdevelopment.ca.data.util.AppConstants.*;

public class UserTableEntryRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setUsername(rs.getString(USER_USERNAME));
        user.setPassword(rs.getBytes(USER_PASSWORD));
        user.setSalt(rs.getBytes(USER_SALT));

        return user;
    }

}

