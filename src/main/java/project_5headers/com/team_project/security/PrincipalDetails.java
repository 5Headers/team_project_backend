//package project_5headers.com.team_project.security;
//
//
//import project_5headers.com.team_project.entity.User;
//import java.util.Collection;
//import java.util.Collections;
//
//public class PrincipalDetails implements UserDetails {
//
//    private final User user;
//
//    public PrincipalDetails(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<String> getAuthorities() {
//        return Collections.emptyList();
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//
//    }
//
//}
