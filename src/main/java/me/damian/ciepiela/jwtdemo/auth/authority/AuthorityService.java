package me.damian.ciepiela.jwtdemo.auth.authority;

import me.damian.ciepiela.jwtdemo.abstraction.GenericCRUDServiceWithOneChangeable;
import me.damian.ciepiela.jwtdemo.auth.authority.dto.AuthorityDto;
import me.damian.ciepiela.jwtdemo.dto.GenericResponseDto;

public interface AuthorityService extends GenericCRUDServiceWithOneChangeable
        <AuthorityDto, AuthorityDto, Authority, GenericResponseDto> {
}
