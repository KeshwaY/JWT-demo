package me.damian.ciepiela.jwtdemo.auth.role;

import me.damian.ciepiela.jwtdemo.abstraction.GenericCRUDServiceWithOneChangeable;
import me.damian.ciepiela.jwtdemo.auth.role.dto.RoleGetDto;
import me.damian.ciepiela.jwtdemo.auth.role.dto.RolePostDto;
import me.damian.ciepiela.jwtdemo.auth.role.dto.RolePutDto;
import me.damian.ciepiela.jwtdemo.dto.GenericResponseDto;

public interface RoleService extends GenericCRUDServiceWithOneChangeable
        <RolePostDto, RolePutDto, RoleGetDto, GenericResponseDto> {
}
