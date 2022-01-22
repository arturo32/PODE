package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.exception.NegocioException;
import br.ufrn.imd.pode.modelo.ModeloAbstrato;
import br.ufrn.imd.pode.modelo.dto.AbstratoDTO;
import br.ufrn.imd.pode.servico.GenericoServico;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin
public abstract class GenericControlador<T extends ModeloAbstrato<PK>, Dto extends AbstratoDTO, PK extends Serializable> {
	protected abstract GenericoServico<T, Dto, PK> service();

	@GetMapping
	public ResponseEntity<Collection<Dto>> findList(@RequestParam("limit") Optional<Integer> limite,
			@RequestParam("page") Optional<Integer> pagina, @RequestParam("ids") Optional<List<PK>> ids,
			@RequestParam("start") Optional<PK> start, @RequestParam("end") Optional<PK> end) {
		ResponseEntity<Collection<Dto>> result;
		if (limite.isPresent() && pagina.isPresent()) {
			result = ResponseEntity.ok(service().convertToDTOList(service().findAll(limite.get(), pagina.get())));
		} else if (ids.isPresent()) {
			result = ResponseEntity.ok(service().convertToDTOList(service().findByIds(ids.get())));
		} else if (start.isPresent() && end.isPresent()) {
			result = ResponseEntity.ok(service().convertToDTOList(service().findByInterval(start.get(), end.get())));
		} else {
			throw new NegocioException(
					"Informe o limite e a pagina ou os ids a serem buscados ou o intervalo de ids a ser buscado");
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
