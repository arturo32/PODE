package br.ufrn.imd.pode.controle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;

import br.ufrn.imd.pode.modelo.ModeloAbstrato;
import br.ufrn.imd.pode.servico.GenericoServico;

@CrossOrigin
public abstract class GenericoControlador<T extends ModeloAbstrato<PK>, Dto, PK extends Serializable> {

	protected abstract GenericoServico<T, Dto, PK> servico();

	@GetMapping
	public ResponseEntity<Collection<Dto>> buscarTodos(@RequestParam("limit") Integer limite, @RequestParam("page") Integer pagina) {
		return ResponseEntity.ok(servico().converterParaListaDTO(servico().buscarTodos(limite, pagina)));
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
