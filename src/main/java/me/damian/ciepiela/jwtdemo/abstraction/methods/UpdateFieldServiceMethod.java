package me.damian.ciepiela.jwtdemo.abstraction.methods;

public interface UpdateFieldServiceMethod<T, PutDotT, GetDtoT> {
    GetDtoT updateDocumentField(T documentIdentifier, PutDotT putDot) throws Exception;
}
