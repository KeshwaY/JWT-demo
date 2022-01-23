package me.damian.ciepiela.jwtdemo.abstraction;

import me.damian.ciepiela.jwtdemo.abstraction.methods.*;

public interface GenericCRUDServiceWithOneChangeable
        <PostDtoT, PutDtoT, GetDtoT, ResponseDtoT> extends
        CreateServiceMethod<PostDtoT, GetDtoT>,
        ReadByFieldServiceMethod<GetDtoT, String>,
        ReadAllServiceMethod<GetDtoT>,
        UpdateFieldServiceMethod<String, PutDtoT, GetDtoT>,
        DeleteServiceMethod<String, ResponseDtoT> {
}
