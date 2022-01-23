package me.damian.ciepiela.jwtdemo.abstraction.methods;

public interface UpdateFieldsServiceMethod<T, K, PutDotT, GetDtoT> {
    GetDtoT updateDocumentField(T fieldIdentifier, K updateType, PutDotT putDot) throws Exception;
}
