package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "permissions")
@Getter
@Setter
public class PermissionModel extends BaseModel implements GrantedAuthority {

    @Column(name = "role")
    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }
}
