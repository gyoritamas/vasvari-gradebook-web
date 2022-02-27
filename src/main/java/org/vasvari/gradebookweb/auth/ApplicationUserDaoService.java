package org.vasvari.gradebookweb.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.vasvari.gradebookweb.service.UserGateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ApplicationUserDaoService implements ApplicationUserDao {
    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserDaoService(UserGateway userGateway, PasswordEncoder passwordEncoder) {
        this.userGateway = userGateway;
        this.passwordEncoder = passwordEncoder;
//        userGateway.save(new User(1L, "admin", passwordEncoder.encode("admin"), ApplicationUserRole.ADMIN));
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        return userGateway.findAllUsers().stream().map(
                user -> new ApplicationUser(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRole().getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        ).collect(Collectors.toList());

//        return List.of(
//                new ApplicationUser(
//                        "johnsmith",
//                        passwordEncoder.encode("password"),
//                        STUDENT.getGrantedAuthorities(),
//                        true,
//                        true,
//                        true,
//                        true
//                ),
//                new ApplicationUser(
//                        "tom",
//                        passwordEncoder.encode("password123"),
//                        ADMINTRAINEE.getGrantedAuthorities(),
//                        true,
//                        true,
//                        true,
//                        true
//                ),
//                new ApplicationUser(
//                        "linda",
//                        passwordEncoder.encode("password123"),
//                        ADMIN.getGrantedAuthorities(),
//                        true,
//                        true,
//                        true,
//                        true
//                )
//        );
    }
}
