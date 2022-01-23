package me.damian.ciepiela.jwtdemo.abstraction;

import me.damian.ciepiela.jwtdemo.abstraction.methods.*;

public interface GenericCRUDService<PostDtoT, PutDtoT, GetDtoT, ResponseDtoT> extends
        CreateServiceMethod<PostDtoT, GetDtoT>,
        ReadByFieldServiceMethod<GetDtoT, String>,
        ReadAllServiceMethod<GetDtoT>,
        UpdateFieldsServiceMethod<String, String, PutDtoT, GetDtoT>,
        DeleteServiceMethod<String, ResponseDtoT>
{}
