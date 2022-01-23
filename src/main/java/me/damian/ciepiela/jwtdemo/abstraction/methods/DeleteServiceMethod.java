package me.damian.ciepiela.jwtdemo.abstraction.methods;

public interface DeleteServiceMethod<T, ResponseDto> {
    ResponseDto deleteDocument(T identifier) throws Exception;
}
