package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.exception.NegocioException;
import br.ufrn.imd.pode.modelo.ModeloAbstrato;
import br.ufrn.imd.pode.modelo.dto.AbstratoDTO;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin
public abstract class GenericoControlador<T extends ModeloAbstrato<PK>, Dto extends AbstratoDTO, PK extends Serializable> {

	protected abstract GenericoServico<T, Dto, PK> servico();

	@GetMapping
	public ResponseEntity<Collection<Dto>> buscarTodos(@RequestParam("limit") Optional<Integer> limite,
													   @RequestParam("page") Optional<Integer> pagina, @RequestParam("ids") Optional<List<PK>> ids,
													   @RequestParam("start") Optional<PK> start, @RequestParam("end") Optional<PK> end) {
		ResponseEntity<Collection<Dto>> result;
		if (limite.isPresent() && pagina.isPresent()) {
			result = ResponseEntity.ok(servico().converterParaListaDTO(servico().buscarTodos(limite.get(), pagina.get())));
		} else if (ids.isPresent()) {
			result = ResponseEntity.ok(servico().converterParaListaDTO(servico().buscarPorIds(ids.get())));
		} else if (start.isPresent() && end.isPresent()) {
			result = ResponseEntity.ok(servico().converterParaListaDTO(servico().buscarPorIntervalo(start.get(), end.get())));
		} else {
			throw new NegocioException(
					"Informe o limite e a pagina ou os ids a serem buscados ou o intervalo de ids a ser buscado");
		}
		return result;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Dto> buscarPorId(@PathVariable PK id) {
		return ResponseEntity.ok(servico().converterParaDTO(servico().buscarPorId(id)));
	}

	@PostMapping
	public ResponseEntity<Dto> salvar(@Valid @RequestBody Dto dto) {
		return ResponseEntity.ok(servico().converterParaDTO(servico().salvar(dto)));
	}

	@PutMapping
	public ResponseEntity<Dto> atualizar(@Valid @RequestBody Dto dto) {
		return ResponseEntity.ok(servico().converterParaDTO(servico().atualizar(dto)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Dto> remover(@PathVariable(value = "id") PK id) {
		servico().remover(id);
		return ResponseEntity.ok().build();
	}
}
