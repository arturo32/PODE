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

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoControlador {

	private final HashMap<String, RecomendacaoServico> recomendadores = new HashMap<>();

	@Autowired
	public void setRecomendadores(RecomendacaoServico servico) {
		this.recomendadores.put(servico.getNomeServico(), servico);
	}

	@GetMapping("/recomendar-disciplinas/{nome_recomendador}/{id_vinculo}")
	public ResponseEntity<RecomendacaoDTO> recomendarDisciplinas(
			@PathVariable(value = "nome_recomendador") String nome_recomendador,
			@PathVariable(value = "id_vinculo") Long id_vinculo) {
		return ResponseEntity.ok(this.recomendadores.get(nome_recomendador).recomendarDisciplinas(
				this.recomendadores.get(nome_recomendador).validar(id_vinculo)
		));
	}

}