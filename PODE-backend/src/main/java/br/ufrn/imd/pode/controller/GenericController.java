package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.AbstractModel;
import br.ufrn.imd.pode.model.dto.AbstractDTO;
import br.ufrn.imd.pode.service.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@CrossOrigin
public abstract class GenericController<T extends AbstractModel<PK>, Dto extends AbstractDTO, PK extends Serializable> {
	protected abstract GenericService<T, Dto, PK> service();

	@GetMapping
	public List<Dto> findAll(@RequestParam("limite") Integer limite, @RequestParam("pagina") Integer pagina) {
		return service().convertToDTOList(service().findAll(limite, pagina));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Dto> findById(@PathVariable PK id) {
		return ResponseEntity.ok(service().convertToDto(service().findById(id)));
	}

	@PostMapping
	public ResponseEntity<Dto> save(@Valid @RequestBody Dto dto) {
		return ResponseEntity.ok(service().convertToDto(service().save(service().validate(dto))));
	}

	@PutMapping
	public ResponseEntity<Dto> update(@Valid @RequestBody Dto dto) {
		return ResponseEntity.ok(service().convertToDto(service().update(service().validate(dto))));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Dto> delete(@PathVariable(value = "id") PK id) {
		service().deleteById(id);
		return ResponseEntity.ok().build();
	}
}
