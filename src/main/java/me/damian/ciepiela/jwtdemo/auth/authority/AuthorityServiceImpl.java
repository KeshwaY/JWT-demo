package me.damian.ciepiela.jwtdemo.auth.authority;

import me.damian.ciepiela.jwtdemo.auth.authority.dto.AuthorityDto;
import me.damian.ciepiela.jwtdemo.dto.GenericResponseDto;
import me.damian.ciepiela.jwtdemo.exceptions.DocumentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority createDocument(AuthorityDto postDto) throws Exception {
        postDto.setName(postDto.getName().toUpperCase(Locale.ROOT));
        if (authorityRepository.findByName(postDto.getName()).isPresent()) {
            throw new DocumentNotFoundException();
        }
        Authority authority = new Authority();
        authority.setName(postDto.getName());
        return authorityRepository.save(authority);
    }

    @Override
    public GenericResponseDto deleteDocument(String identifier) throws Exception {
        Authority authority = authorityRepository.findByName(identifier.toUpperCase(Locale.ROOT)).orElseThrow(DocumentNotFoundException::new);
        authorityRepository.delete(authority);
        return new GenericResponseDto(String.format("Successfully deleted %s authority.", authority.getName()));
    }

    @Override
    public List<Authority> getAllDocuments() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority getDocumentByField(String identifier) throws Exception {
        return authorityRepository.findByName(identifier.toUpperCase(Locale.ROOT)).orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    @Transactional
    public Authority updateDocumentField(String identifier, AuthorityDto putDot) throws Exception {
        Authority authority = authorityRepository.findByName(identifier.toUpperCase(Locale.ROOT)).orElseThrow(DocumentNotFoundException::new);
        authority.setName(putDot.getName().toUpperCase(Locale.ROOT));
        return authority;
    }

}
