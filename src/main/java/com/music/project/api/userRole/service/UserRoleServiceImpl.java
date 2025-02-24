package com.music.project.api.userRole.service;

import com.music.project.api.userRole.repository.UserRoleM2MRepository;
import com.music.project.api.userRole.repository.UserRoleRepository;
import com.music.project.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleM2MRepository userRoleM2MRepository;
    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        return userRoleM2MRepository.findRolesByUserId(userId);
    }
}
