package com.backend.online_qwiz.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.backend.online_qwiz.model.Permission.*;

@RequiredArgsConstructor
public enum Role {
    STUDENT(
            Set.of(
                    STUDENT_READ,
                    STUDENT_UPDATE

            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_READ,
                    ADMIN_DELETE
            )
    );
    public static boolean hasRole(Set<Role> roles, Role role) {
        return roles.contains(role);
    }

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
