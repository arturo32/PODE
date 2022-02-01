package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.dto.RecomendacaoDTO;
import br.ufrn.imd.pode.servico.RecomendacaoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoControlador {

	private Map<String, RecomendacaoServico> recomendadores = new HashMap<>();

	@Autowired(required = false)
	public void setRecomendadores(List<RecomendacaoServico> recomendadores) {
		this.recomendadores = recomendadores.stream()
				.collect(Collectors.toMap(RecomendacaoServico::getNomeServico, Function.identity()));
	}

	@GetMapping("/recomendar-disciplinas/{nome_recomendador}/{id_vinculo}")
	public ResponseEntity<RecomendacaoDTO> recomendarDisciplinas(
			@PathVariable(value = "nome_recomendador") String nome_recomendador,
			@PathVariable(value = "id_vinculo") Long id_vinculo) {
		return ResponseEntity.ok(this.recomendadores.get(nome_recomendador).recomendarDisciplinas(id_vinculo));
	}

}