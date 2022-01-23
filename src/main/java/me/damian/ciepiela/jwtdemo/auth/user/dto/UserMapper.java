package me.damian.ciepiela.jwtdemo.auth.user.dto;

import me.damian.ciepiela.jwtdemo.auth.role.Role;
import me.damian.ciepiela.jwtdemo.auth.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "username", source = "username", qualifiedByName = "nameToLowerCase")
    User userPostDtoToUser(UserPostDto userPostDto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToString")
    UserGetDto userToUserGetDto(User user);

    @Named("rolesToString")
    default Collection<String> rolesToString(Collection<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Named("nameToLowerCase")
    default String nameToLowerCase(String name) {
        return name.toLowerCase(Locale.ROOT);
    }

}
