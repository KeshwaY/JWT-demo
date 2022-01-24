package me.damian.ciepiela.jwtdemo.auth.user;

import me.damian.ciepiela.jwtdemo.abstraction.GenericCRUDServiceWithOneChangeable;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserGetDto;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserPostDto;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserPutDto;
import me.damian.ciepiela.jwtdemo.dto.GenericResponseDto;
import me.damian.ciepiela.jwtdemo.exceptions.DocumentNotFoundException;

public interface UserService extends GenericCRUDServiceWithOneChangeable<UserPostDto, UserPutDto, UserGetDto, GenericResponseDto> {
    User getRawUser(String name) throws DocumentNotFoundException;
    UserGetDto registerUser(UserPostDto postDto) throws Exception;
}
