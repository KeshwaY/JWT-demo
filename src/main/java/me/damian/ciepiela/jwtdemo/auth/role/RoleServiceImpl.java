package me.damian.ciepiela.jwtdemo.auth.role;

import me.damian.ciepiela.jwtdemo.auth.authority.Authority;
import me.damian.ciepiela.jwtdemo.auth.authority.AuthorityRepository;
import me.damian.ciepiela.jwtdemo.auth.role.dto.RoleGetDto;
import me.damian.ciepiela.jwtdemo.auth.role.dto.RoleMapper;
import me.damian.ciepiela.jwtdemo.auth.role.dto.RolePostDto;
import me.damian.ciepiela.jwtdemo.auth.role.dto.RolePutDto;
import me.damian.ciepiela.jwtdemo.dto.GenericResponseDto;
import me.damian.ciepiela.jwtdemo.exceptions.DocumentAlreadyExistsException;
import me.damian.ciepiela.jwtdemo.exceptions.DocumentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final AuthorityRepository authorityRepository;
    private final RoleMapper mapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, AuthorityRepository authorityRepository) {
        this.repository = roleRepository;
        this.mapper = roleMapper;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public RoleGetDto createDocument(RolePostDto postDto) throws Exception {
        checkIfNameAlreadyExist(postDto.getName());
        Role role = mapper.rolePostDtoToRole(postDto);
        Collection<Authority> authorities = findAuthorities(postDto.getAuthorities());
        role.setAuthorities(authorities);
        return mapper.roleToRoleGetDto(repository.save(role));
    }

    @Override
    public GenericResponseDto deleteDocument(String identifier) throws Exception {
        Role role = repository.findByName(identifier.toUpperCase(Locale.ROOT)).orElseThrow(DocumentNotFoundException::new);
        repository.delete(role);
        return new GenericResponseDto("Role was successfully deleted!");
    }

    @Override
    public List<RoleGetDto> getAllDocuments() {
        return repository.findAll().stream()
                .map(mapper::roleToRoleGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public RoleGetDto getDocumentByField(String identifier) throws Exception {
        Role role = repository.findByName(identifier.toUpperCase(Locale.ROOT)).orElseThrow(DocumentNotFoundException::new);
        return mapper.roleToRoleGetDto(role);
    }

    @Override
    public RoleGetDto updateDocumentField(String fieldIdentifier, RolePutDto putDot) throws Exception {
        Role role = repository.findByName(fieldIdentifier.toUpperCase(Locale.ROOT)).orElseThrow(DocumentNotFoundException::new);
        if (putDot.getName() != null) {
            checkIfNameAlreadyExist((putDot.getName()));
            role.setName(putDot.getName().toUpperCase(Locale.ROOT));
        }
        if (putDot.getAuthorities() != null && putDot.getAuthorities().size() > 0) {
            Collection<Authority> authorities = findAuthorities((putDot.getAuthorities()));
            role.setAuthorities(authorities);
        }
        return mapper.roleToRoleGetDto(repository.save(role));
    }

    private void checkIfNameAlreadyExist(String name) throws DocumentAlreadyExistsException {
        if (repository.findByName(name.toUpperCase(Locale.ROOT)).isPresent()) {
            throw new DocumentAlreadyExistsException();
        }
    }

    private Collection<Authority> findAuthorities(Collection<String> names) throws DocumentNotFoundException {
        Collection<Authority> authorityCollection = new ArrayList<>();
        for (String name : names) {
            Authority authority = authorityRepository.findByName(name.toUpperCase(Locale.ROOT)).orElseThrow(DocumentNotFoundException::new);
            authorityCollection.add(authority);
        }
        return authorityCollection;
    }

}
