package me.damian.ciepiela.jwtdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data @NoArgsConstructor @AllArgsConstructor
public abstract class AbstractDocument {

    @Id
    private String id;

}
