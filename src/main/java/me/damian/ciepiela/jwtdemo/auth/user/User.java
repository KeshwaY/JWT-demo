package me.damian.ciepiela.jwtdemo.auth.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.damian.ciepiela.jwtdemo.AbstractDocument;
import me.damian.ciepiela.jwtdemo.auth.role.Role;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document("users")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractDocument {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    @DocumentReference
    private List<Role> roles;

}
