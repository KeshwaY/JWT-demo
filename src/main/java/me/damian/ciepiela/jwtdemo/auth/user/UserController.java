package me.damian.ciepiela.jwtdemo.auth.user;

import me.damian.ciepiela.jwtdemo.abstraction.GenericCRUDControllerWithOneChangeable;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserGetDto;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserPostDto;
import me.damian.ciepiela.jwtdemo.auth.user.dto.UserPutDto;
import me.damian.ciepiela.jwtdemo.dto.GenericResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends GenericCRUDControllerWithOneChangeable<UserPostDto, UserPutDto, UserGetDto, GenericResponseDto, UserService> {
    public UserController(UserService service) {
        super(service, "/api/v1/users");
    }

    @PostMapping("/register")
    public ResponseEntity<UserGetDto> registerUser(
            @RequestBody @Valid UserPostDto postDto
    ) throws Exception {
        UserGetDto response = service.registerUser(postDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
