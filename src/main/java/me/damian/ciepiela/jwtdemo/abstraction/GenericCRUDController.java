package me.damian.ciepiela.jwtdemo.abstraction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

public abstract class GenericCRUDController
        <PostDtoT, PutDtoT, GetDtoT, ResponseDtoT,
        ServiceT extends GenericCRUDService<PostDtoT, PutDtoT, GetDtoT, ResponseDtoT>> {

    protected final ServiceT service;
    protected final String createPath;

    public GenericCRUDController(ServiceT service, String createPath) {
        this.service = service;
        this.createPath = createPath;
    }

    @PostMapping
    public ResponseEntity<GetDtoT> create(@RequestBody @Valid PostDtoT postDto) throws Exception {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(createPath).toUriString());
        return ResponseEntity.created(uri).body(service.createDocument(postDto));
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<GetDtoT> read(@PathVariable String identifier) throws Exception {
        return ResponseEntity.ok(service.getDocumentByField(identifier));
    }

    @GetMapping
    public ResponseEntity<List<GetDtoT>> readAll() {
        return ResponseEntity.ok(service.getAllDocuments());
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<GetDtoT> update(
            @PathVariable String identifier,
            @RequestParam String field,
            @RequestBody @Valid PutDtoT putDto
    ) throws Exception {
        return ResponseEntity.ok(service.updateDocumentField(identifier, field, putDto));
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<ResponseDtoT> delete(@PathVariable String identifier) throws Exception {
        return ResponseEntity.ok(service.deleteDocument(identifier));
    }

}
