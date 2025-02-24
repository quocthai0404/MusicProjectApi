package com.music.project.api.userRole.service;

import com.music.project.entities.Role;

import java.util.List;

public interface UserRoleService {
    public List<Role> getRolesByUserId(Integer userId);
}
