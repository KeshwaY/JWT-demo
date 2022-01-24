package me.damian.ciepiela.jwtdemo.auth.user;

import me.damian.ciepiela.jwtdemo.auth.role.Role;
import me.damian.ciepiela.jwtdemo.auth.role.RoleRepository;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserGetDto;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserMapper;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserPostDto;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserPutDto;
import me.damian.ciepiela.jwtdemo.dto.GenericResponseDto;
import me.damian.ciepiela.jwtdemo.exceptions.DocumentAlreadyExistsException;
import me.damian.ciepiela.jwtdemo.exceptions.DocumentNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserGetDto registerUser(UserPostDto postDto) throws Exception {
        if (repository.findByUsername(postDto.getUsername().toLowerCase(Locale.ROOT)).isPresent()) {
            throw new DocumentAlreadyExistsException();
        }
        User user = mapper.userPostDtoToUser(postDto);
        addRoleToUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return mapper.userToUserGetDto(user);
    }

    private void addRoleToUser(User user) throws DocumentNotFoundException {
        Set<Role> roles = new HashSet<>();
        if (repository.findAll().size() == 0) {
            roles.add(getRoleByAuthority("OP_USER_MANAGEMENT"));
        } else {
            roles.add(getRoleByAuthority("BASIC_USER"));
        }
        user.setRoles(roles);
    }

    private Role getRoleByAuthority(String authority) throws DocumentNotFoundException {
        return roleRepository.findByAuthorityName(authority.toUpperCase(Locale.ROOT)).orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public UserGetDto createDocument(UserPostDto postDto) throws Exception {
        if (repository.findByUsername(postDto.getUsername().toLowerCase(Locale.ROOT)).isPresent()) {
            throw new DocumentAlreadyExistsException();
        }
        Set<Role> roles = findRoles(postDto.getRoles());
        User user = mapper.userPostDtoToUser(postDto);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return mapper.userToUserGetDto(user);
    }

    @Override
    public GenericResponseDto deleteDocument(String identifier) throws Exception {
        User user = repository.findByUsername(identifier).orElseThrow(DocumentNotFoundException::new);
        repository.delete(user);
        return new GenericResponseDto("User was successfully deleted!");
    }

    @Override
    public List<UserGetDto> getAllDocuments() {
        return repository.findAll().stream()
                .map(mapper::userToUserGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public UserGetDto getDocumentByField(String fieldIdentifier) throws Exception {
        User user = repository.findByUsername(fieldIdentifier).orElseThrow(DocumentNotFoundException::new);
        return mapper.userToUserGetDto(user);
    }

    @Override
    public UserGetDto updateDocumentField(String fieldIdentifier, UserPutDto putDot) throws Exception {
        User user = repository.findByUsername(fieldIdentifier).orElseThrow(DocumentNotFoundException::new);
        if (putDot.getUsername() != null) {
            checkIfUserAlreadyExist(putDot.getUsername());
            user.setUsername(putDot.getUsername());
        }
        if (putDot.getPassword() != null && !passwordEncoder.matches(putDot.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(putDot.getPassword()));
        }
        if (putDot.getRoles() != null && putDot.getRoles().size() > 0) {
            Set<Role> roles = findRoles(putDot.getRoles());
            user.setRoles(roles);
        }
        return mapper.userToUserGetDto(repository.save(user));
    }

    private void checkIfUserAlreadyExist(String name) throws DocumentAlreadyExistsException {
        if (repository.findByUsername(name).isPresent()) {
            throw new DocumentAlreadyExistsException();
        }
    }

    private Set<Role> findRoles(Collection<String> rolesNames) throws DocumentNotFoundException {
        Set<Role> roles = new HashSet<>();
        for (String name : rolesNames) {
            Role role = roleRepository.findByName(name.toUpperCase(Locale.ROOT)).orElseThrow(DocumentNotFoundException::new);
            roles.add(role);
        }
        return roles;
    }

    @Override
    public User getRawUser(String name) throws DocumentNotFoundException {
        return repository.findByUsername(name).orElseThrow(DocumentNotFoundException::new);
    }

}
