package me.damian.ciepiela.jwtdemo.auth.role.dto;

import me.damian.ciepiela.jwtdemo.auth.authority.Authority;
import me.damian.ciepiela.jwtdemo.auth.role.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring"
)
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(source = "name", target = "name", qualifiedByName = "stringToUpperCase")
    Role rolePostDtoToRole(RolePostDto postDto);

    @Mapping(target = "authorities", source = "authorities", qualifiedByName = "authoritiesToString")
    RoleGetDto roleToRoleGetDto(Role role);

    @Named("authoritiesToString")
    default Collection<String> authoritiesToString(Collection<Authority> authorities) {
        return authorities.stream()
                .map(Authority::getName)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Named("stringToUpperCase")
    default String toUpperCase(String s) {
        return s.toUpperCase(Locale.ROOT);
    }

}
