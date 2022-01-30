package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.DisciplinaServico;
import br.ufrn.imd.pode.servico.FiltroDisciplinaServico;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaControlador<T extends Disciplina, E extends DisciplinaDTO> extends GenericoControlador<T, E, Long> {

	private DisciplinaServico<T, E> servico;

	private Map<String, FiltroDisciplinaServico> filtros;

	@Autowired
	public void setFiltros(Set<FiltroDisciplinaServico> filtros) {
		this.filtros = filtros.stream()
				.collect(Collectors.toMap(FiltroDisciplinaServico::obterNome, Function.identity()));
	}

	@Autowired
	public void setDisciplinaService(DisciplinaServico<T, E> disciplinaService) {
		this.servico = disciplinaService;
	}

	@Override
	protected GenericoServico<T, E, Long> servico() {
		return this.servico;
	}

	@GetMapping("/filtro/{nome_filtro}")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasPorFiltro(@PathVariable String nome_filtro,
	                                                                            @RequestParam Map<String, String> parametros) {
		return ResponseEntity.ok(filtros.get(nome_filtro).filtrar(parametros));
	}

	@GetMapping("/codigos/{codigo}")
	public ResponseEntity<Collection<E>> buscarDisciplinaCodigo(@PathVariable String codigo) {
		return ResponseEntity.ok(servico.converterParaListaDTO(servico.buscarDisciplinasPorCodigo(codigo)));
	}

}
