package project_5headers.com.team_project.sequrity;

import java.util.Collection;

public interface UserDetails {

    Collection<String> getAuthorities();

    String getPassword();

    String getUsername();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialNonExpired();

    boolean isEnabled();


}
