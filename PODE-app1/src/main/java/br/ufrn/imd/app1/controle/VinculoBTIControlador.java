package br.ufrn.imd.app1.controle;

import br.ufrn.imd.app1.modelo.VinculoBTI;
import br.ufrn.imd.app1.modelo.dto.VinculoBTIDTO;
import br.ufrn.imd.app1.servico.VinculoBTIServico;
import br.ufrn.imd.pode.controle.GenericoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vinculos")
public class VinculoBTIControlador extends GenericoControlador<VinculoBTI, VinculoBTIDTO, Long> {

	private VinculoBTIServico vinculoBTIServico;

	@Autowired
	public void setVinculoBTIServico(VinculoBTIServico vinculoBTIServico) {
		this.vinculoBTIServico = vinculoBTIServico;
	}

	@Override
	protected GenericoServico<VinculoBTI, VinculoBTIDTO, Long> servico() {
		return vinculoBTIServico;
	}

	@GetMapping("/{id}/percentual-conclusao")
	public ResponseEntity<Double> obterPercentualConclusao(@PathVariable Long id) {
		return ResponseEntity.ok(vinculoBTIServico.obterPercentualConclusao(id));
	}
}
