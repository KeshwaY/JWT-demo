package me.damian.ciepiela.jwtdemo.auth.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.damian.ciepiela.jwtdemo.AbstractDocument;
import me.damian.ciepiela.jwtdemo.auth.authority.Authority;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Document("roles")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractDocument {

    @NotNull
    private String name;

    @NotNull
    @DocumentReference
    private Set<Authority> authorities;

}
