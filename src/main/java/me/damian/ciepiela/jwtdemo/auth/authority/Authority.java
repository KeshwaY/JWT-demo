package me.damian.ciepiela.jwtdemo.auth.authority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.damian.ciepiela.jwtdemo.AbstractDocument;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document("Authorities")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Authority extends AbstractDocument {

    @NotBlank
    @Indexed(unique = true)
    private String name;

}
