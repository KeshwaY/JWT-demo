package me.damian.ciepiela.jwtdemo.abstraction.methods;

import java.util.List;

public interface ReadAllServiceMethod<GetDtoT> {
    List<GetDtoT> getAllDocuments();
}
