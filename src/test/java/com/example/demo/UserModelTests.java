package com.example.demo;

import com.example.demo.model.PermissionModel;
import com.example.demo.model.UserModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserModelTests {

    @Test
    public void testUserModelProperties() {
        UserModel userModel = new UserModel();
        userModel.setEmail("test@example.com");
        userModel.setPassword("password");
        userModel.setFull_name("John Doe");
        List<PermissionModel> permissions = new ArrayList<>();
        userModel.setPermissions(permissions);

        assertEquals("test@example.com", userModel.getEmail());
        assertEquals("password", userModel.getPassword());
        assertEquals("John Doe", userModel.getFull_name());
        assertEquals(permissions, userModel.getPermissions());
    }

    @Test
    public void testUserModelUserDetailsMethods() {
        UserModel userModel = new UserModel();
        assertEquals(userModel.getEmail(), userModel.getUsername());
        assertTrue(userModel.getAuthorities().isEmpty());
        assertTrue(userModel.isAccountNonExpired());
        assertTrue(userModel.isAccountNonLocked());
        assertTrue(userModel.isCredentialsNonExpired());
        assertTrue(userModel.isEnabled());
    }
}
