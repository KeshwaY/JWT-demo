package me.damian.ciepiela.jwtdemo.auth.authority;

import me.damian.ciepiela.jwtdemo.abstraction.GenericCRUDControllerWithOneChangeable;
import me.damian.ciepiela.jwtdemo.auth.authority.dto.AuthorityDto;
import me.damian.ciepiela.jwtdemo.dto.GenericResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authorities")
public class AuthorityController extends GenericCRUDControllerWithOneChangeable
        <AuthorityDto, AuthorityDto, Authority, GenericResponseDto, AuthorityService> {

    public AuthorityController(AuthorityService service) {
        super(service, "/api/v1/authorities");
    }

}
