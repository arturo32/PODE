package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.AbstractModel;
import br.ufrn.imd.pode.service.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@CrossOrigin
public abstract class GenericController<T extends AbstractModel<PK>, PK extends Serializable> {
    protected abstract GenericService<T, PK> service();

    @GetMapping
    public List<T> findAll(@RequestParam("limite") Integer limite,
                           @RequestParam("pagina") Integer pagina) {
        return service().findAll(limite, pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable PK id) {
        return ResponseEntity.ok(service().findById(id));
    }

    @PostMapping
    public ResponseEntity<T> save(@Valid @RequestBody T entity) {
        return ResponseEntity.ok(service().save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<T> delete(@PathVariable(value = "id") PK id) {
        service().deleteById(id);
        return ResponseEntity.ok().build();
    }
}
