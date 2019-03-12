package com.mathodcoast.dao;

import com.mathodcoast.exception.DaoOperationException;
import com.mathodcoast.model.Admin;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDao {
    DataSource dataSource;
    private final String INSERT_ADMIN_SQL = "INSERT INTO admin (username, password) VALUES (?,?)";
    private final String SELECT_ALL_ADMINS = "SELECT * FROM admin";

    public AdminDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ADMINS);
            while (resultSet.next()){
                Admin admin = new Admin(
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
                admins.add(admin);
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Could not get all admins from DB" + e);
        }
        return admins;
    }

    @Override
    public void saveAdmin(Admin admin) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADMIN_SQL);
            preparedStatement.setString(1,admin.getUsername());
            preparedStatement.setString(1,admin.getPassword());
        } catch (SQLException e) {
            throw  new DaoOperationException("Could not save admin to DB" + e);
        }
    }
}
