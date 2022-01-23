package me.damian.ciepiela.jwtdemo.abstraction.methods;

public interface CreateServiceMethod<PostDtoT, GetDtoT> {
    GetDtoT createDocument(PostDtoT postDto) throws Exception;
}
