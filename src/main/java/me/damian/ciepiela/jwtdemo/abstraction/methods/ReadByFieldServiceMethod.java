package me.damian.ciepiela.jwtdemo.abstraction.methods;

public interface ReadByFieldServiceMethod<GetDtoT, T> {
    GetDtoT getDocumentByField(T fieldIdentifier) throws Exception;
}
