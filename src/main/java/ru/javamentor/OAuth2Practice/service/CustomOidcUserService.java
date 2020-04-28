package ru.javamentor.OAuth2Practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import ru.javamentor.OAuth2Practice.entity.Role;
import ru.javamentor.OAuth2Practice.entity.User;
import ru.javamentor.OAuth2Practice.repository.RoleRepository;
import ru.javamentor.OAuth2Practice.repository.UserRepository;
import ru.javamentor.OAuth2Practice.util.OAuth2UserInfo;

import java.time.LocalDate;
import java.util.HashSet;

@Service
public class CustomOidcUserService extends OidcUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public CustomOidcUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        try {
            return processOidcUser(oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUser oidcUser) {
        OAuth2UserInfo userInfo = new OAuth2UserInfo(oidcUser.getAttributes());

        final User userFromDB = userRepository.findByUsername(userInfo.getEmail());
        if (userFromDB == null) {
            User user = new User();
            user.setUsername(userInfo.getEmail());
            user.setPassword("$2y$12$ml0NGU9FvLASj77K3Pveq.h4jNi2dn4maaOsiSqm0Gt7dwhZuMOsO");
            user.setFirstName("");
            user.setSecondName(userInfo.getName());
            user.setBirthday(LocalDate.now());

            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRole("ROLE_USER"));

            user.setRoles(roles);

            userRepository.save(user);
        }

        return oidcUser;
    }
}