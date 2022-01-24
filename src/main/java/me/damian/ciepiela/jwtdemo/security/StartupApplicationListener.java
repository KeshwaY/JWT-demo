package me.damian.ciepiela.jwtdemo.security;

import me.damian.ciepiela.jwtdemo.auth.authority.Authority;
import me.damian.ciepiela.jwtdemo.auth.authority.AuthorityRepository;
import me.damian.ciepiela.jwtdemo.auth.role.Role;
import me.damian.ciepiela.jwtdemo.auth.role.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    public StartupApplicationListener(AuthorityRepository authorityRepository, RoleRepository roleRepository) {
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Set<String> adminAuthoritiesNames = Set.of(
                "OP_ROLE_MANAGEMENT",
                "OP_USER_MANAGEMENT"
        );

        Set<String> userAuthoritiesNames = Set.of(
                "BASIC_USER"
        );

        Set<String> allAuthorities = new HashSet<>();
        allAuthorities.addAll(adminAuthoritiesNames);
        allAuthorities.addAll(userAuthoritiesNames);
        initAuthorities(allAuthorities);
        Set<Authority> adminAuthorities = authorityRepository.findAllByNameIn(adminAuthoritiesNames);
        Set<Authority> userAuthorities = authorityRepository.findAllByNameIn(userAuthoritiesNames);
        createRoleWithAuthoritiesIfNotExist("admin", adminAuthorities);
        createRoleWithAuthoritiesIfNotExist("user", userAuthorities);
    }

    private void initAuthorities(Set<String> names) {
        findAuthoritiesToCreate(names);
        Set<Authority> authorities = createAuthorities(names);
        authorityRepository.saveAll(authorities);
    }

    private void findAuthoritiesToCreate(Set<String> authorityNames) {
        Set<Authority> authorities = authorityRepository.findAllByNameIn(authorityNames);
        authorities.forEach(a -> authorityNames.remove(a.getName()));
    }

    private Set<Authority> createAuthorities(Set<String> authorityNames) {
        Set<Authority> authorities = new HashSet<>();
        for (String authorityName : authorityNames) {
            Authority authority = new Authority();
            authority.setName(authorityName);
            authorities.add(authority);
        }
        return authorities;
    }

    private void createRoleWithAuthoritiesIfNotExist(String name, Set<Authority> authorities) {
        if (roleRepository.findByAuthoritiesIsContaining(authorities).isEmpty()) {
            Role role = new Role();
            role.setAuthorities(authorities);
            role.setName(name.toUpperCase(Locale.ROOT));
            roleRepository.save(role);
        }
    }


}
