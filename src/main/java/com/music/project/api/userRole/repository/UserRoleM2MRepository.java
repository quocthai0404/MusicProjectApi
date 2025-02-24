package com.music.project.api.userRole.repository;

import com.music.project.entities.Role;
import com.music.project.entities.UserRole;
import com.music.project.entities.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleM2MRepository extends JpaRepository<UserRole, UserRoleId> {
//    @Query("SELECT ur.role FROM UserRole ur WHERE ur.user.id = :userId")
//    List<Role> findRolesByUserId(@Param("userId") Integer userId);

    @Query("SELECT new com.music.project.entities.Role(r.id, r.name) FROM User u JOIN u.userRoles ur JOIN ur.role r WHERE u.id = :userId")
    List<Role> findRolesByUserId(@Param("userId") Integer userId);
}
