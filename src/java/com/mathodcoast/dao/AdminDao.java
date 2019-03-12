package com.mathodcoast.dao;

import com.mathodcoast.model.Admin;

import java.util.List;

public interface AdminDao {
    List<Admin> getAllAdmins();
    void saveAdmin(Admin admin);
}
