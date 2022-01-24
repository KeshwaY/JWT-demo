package me.damian.ciepiela.jwtdemo.auth.role;

import me.damian.ciepiela.jwtdemo.abstraction.GenericCRUDControllerWithOneChangeable;
import me.damian.ciepiela.jwtdemo.auth.role.dto.RoleGetDto;
import me.damian.ciepiela.jwtdemo.auth.role.dto.RolePostDto;
import me.damian.ciepiela.jwtdemo.auth.role.dto.RolePutDto;
import me.damian.ciepiela.jwtdemo.dto.GenericResponseDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController extends GenericCRUDControllerWithOneChangeable<RolePostDto, RolePutDto, RoleGetDto, GenericResponseDto, RoleService> {

    public RoleController(RoleService service) {
        super(service, "/api/v1/roles");
    }

}
