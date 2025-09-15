package project_5headers.com.team_project.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project_5headers.com.team_project.entity.Role;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.entity.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrincipalUser implements UserDetails {

    private Integer userId;
    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private String profileImg;

    private List<UserRole> userRoles; // UserRole 리스트

    // ===== User -> PrincipalUser 변환 메서드 =====
    public static PrincipalUser fromEntity(User user) {
        return PrincipalUser.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .userRoles(user.getUserRoles()) // User 엔티티에 있는 userRoles 사용
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userRoles == null) return List.of();
        return userRoles.stream()
                .map(UserRole::getRole) // UserRole → Role
                .map(Role::getRoleName) // Role → roleName
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}