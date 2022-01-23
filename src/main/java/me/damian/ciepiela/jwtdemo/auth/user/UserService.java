package me.damian.ciepiela.jwtdemo.auth.user;

import me.damian.ciepiela.jwtdemo.abstraction.GenericCRUDServiceWithOneChangeable;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserGetDto;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserPostDto;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserPutDto;
import me.damian.ciepiela.jwtdemo.dto.GenericResponseDto;

public interface UserService extends GenericCRUDServiceWithOneChangeable<UserPostDto, UserPutDto, UserGetDto, GenericResponseDto> {
}
