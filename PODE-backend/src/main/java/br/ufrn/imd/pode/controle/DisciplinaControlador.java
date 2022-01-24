package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.DisciplinaServico;
import br.ufrn.imd.pode.servico.FiltroDisciplinaServico;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/disciplinas")
public abstract class DisciplinaControlador extends GenericoControlador<Disciplina, DisciplinaDTO, Long> {

	private DisciplinaServico servico;

	private HashMap<String, FiltroDisciplinaServico> filtros;

	@Autowired
	public void setDisciplinaService(DisciplinaServico disciplinaService) {
		this.servico = disciplinaService;
	}

	@Override
	protected GenericoServico<Disciplina, DisciplinaDTO, Long> servico() {
		return this.servico;
	}

	/*@GetMapping("/filtro/{nome_filtro}")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasPorFiltro(@PathVariable String nome_filtro,
																				@RequestParam Optional<List<String>> parametro) {
		if(parametro.isPresent()) {
			return ResponseEntity.ok(filtros.get(nome_filtro).filtrar(parametro));
		}
		else {
			return
		}

	}*/

	@GetMapping("/codigos/{codigo}")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinaCodigo(@PathVariable String codigo) {
		return ResponseEntity.ok(servico.converterParaListaDTO(servico.buscarDisciplinasPorCodigo(codigo)));
	}

}
