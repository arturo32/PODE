package br.ufrn.imd.pode.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.FiltroDisciplinaServico;

@RestController
@RequestMapping("/disciplinas-filtros")
public class DisciplinaFiltroControlador {

	private Map<String, FiltroDisciplinaServico> filtros = new HashMap<>();

	@Autowired(required = false)
	public void setFiltros(List<FiltroDisciplinaServico> filtros) {
		this.filtros = filtros.stream()
				.collect(Collectors.toMap(FiltroDisciplinaServico::obterNome, Function.identity()));
	}

	@GetMapping()
	public ResponseEntity<Collection<String>> listarFiltros() {
		return ResponseEntity.ok(filtros.keySet());
	}

	@GetMapping("/{nome_filtro}")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasPorFiltro(@PathVariable String nome_filtro,
	                                                                            @RequestParam Map<String, String> dataQuery) {
		return ResponseEntity.ok(filtros.get(nome_filtro).buscarDisciplinasPorFiltro(dataQuery));
	}
}