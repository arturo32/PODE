package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.exception.BusinessException;
import br.ufrn.imd.pode.model.AbstractModel;
import br.ufrn.imd.pode.model.dto.AbstractDTO;
import br.ufrn.imd.pode.service.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin
public abstract class GenericController<T extends AbstractModel<PK>, Dto extends AbstractDTO, PK extends Serializable> {
	protected abstract GenericService<T, Dto, PK> service();

	@GetMapping
	public ResponseEntity<Collection<Dto>> findList(@RequestParam("limit") Optional<Integer> limite,
	                                          @RequestParam("page") Optional<Integer> pagina,
	                                          @RequestParam("ids") Optional<List<PK>> ids,
	                                          @RequestParam("start") Optional<PK> start,
	                                          @RequestParam("end") Optional<PK> end) {
		ResponseEntity<Collection<Dto>> result;
		if (limite.isPresent() && pagina.isPresent()) {
			result = ResponseEntity.ok(service().convertToDTOList(service().findAll(limite.get(), pagina.get())));
		} else if (ids.isPresent()) {
			result = ResponseEntity.ok(service().convertToDTOList(service().findByIds(ids.get())));
		} else if (start.isPresent() && end.isPresent()) {
			result = ResponseEntity.ok(service().convertToDTOList(service().findByInterval(start.get(), end.get())));
		} else {
			throw new BusinessException("Informe o limite e a pagina ou os ids a serem buscados ou o intervalo de ids a ser buscado");
		}
		return result;
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
