package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;
import br.ufrn.imd.pode.servico.GenericoServico;
import br.ufrn.imd.pode.servico.VinculoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vinculos")
public abstract class VinculoControlador extends GenericoControlador<Vinculo, VinculoDTO, Long> {

	private VinculoServico servico;

	@Autowired
	public void setServico(VinculoServico servico) {
		this.servico = servico;
	}

	@Override
	protected GenericoServico<Vinculo, VinculoDTO, Long> servico() {
		return this.servico;
	}

	@PostMapping("/{id}/percentual-conclusao")
	public abstract ResponseEntity<VinculoDTO> obterPercentualConclusao(@PathVariable Long id);
}
