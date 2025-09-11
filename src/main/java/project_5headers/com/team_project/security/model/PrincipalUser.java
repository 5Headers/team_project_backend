package project_5headers.com.team_project.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project_5headers.com.team_project.entity.Role;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PrincipalUser implements UserDetails {

    private Integer userId;
    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private String profileImg;

    private List<Role> userRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userRoles == null) return List.of();
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }


    @Override
    public boolean isAccountNonExpired() {
        return true; // 필요 시 로직 추가 가능
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 필요 시 로직 추가 가능
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 필요 시 로직 추가 가능
    }

    @Override
    public boolean isEnabled() {
        return true; // 필요 시 로직 추가 가능
    }
}
