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
public class VinculoControlador extends GenericControlador<Vinculo, VinculoDTO, Long> {

	private VinculoServico service;

	@Autowired
	public void setService(VinculoServico service) {
		this.service = service;
	}

	@Override
	protected GenericoServico<Vinculo, VinculoDTO, Long> service() {
		return this.service;
	}

	@PostMapping("/{id}/muda-enfase/{enfase_id}")
	public ResponseEntity<VinculoDTO> mudaEnfase(@PathVariable Long id, @PathVariable Long enfase_id) {
		return ResponseEntity.ok(service.convertToDto(service.mudaEnfase(id, enfase_id)));
	}

	@PostMapping("/{id}/atualiza-periodo/{periodo}")
	public ResponseEntity<VinculoDTO> mudaEnfase(@PathVariable Long id, @PathVariable Integer periodo) {
		return ResponseEntity.ok(service.convertToDto(service.atualizaPeriodoAtual(id, periodo)));
	}

}
