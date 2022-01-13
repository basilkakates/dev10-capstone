package learn.capstone.clubrunner.security.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUser extends User {

    private static final String AUTHORITY_PREFIX = "ROLE_";

    private int userId;
    private String firstName;
    private String lastName;

    public AppUser(int userId, String email, String firstName, String lastName, String password,
                   boolean disabled, List<String> roles) {
        super(email, password, !disabled,
                true, true, true,
                convertRolesToAuthorities(roles));
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private List<String> roles = new ArrayList<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static List<GrantedAuthority> convertRolesToAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (String role : roles) {
            Assert.isTrue(!role.startsWith(AUTHORITY_PREFIX),
                    () ->
                            String.
                                    format("%s cannot start with %s (it is automatically added)",
                                            role, AUTHORITY_PREFIX));
            authorities.add(new SimpleGrantedAuthority(AUTHORITY_PREFIX + role));
        }
        return authorities;
    }

    public static List<String> convertAuthoritiesToRoles(Collection<GrantedAuthority> authorities) {
        return authorities.stream()
                .map(a -> a.getAuthority().substring(AUTHORITY_PREFIX.length()))
                .collect(Collectors.toList());
    }
}