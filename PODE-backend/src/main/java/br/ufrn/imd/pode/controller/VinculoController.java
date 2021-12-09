package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Vinculo;
import br.ufrn.imd.pode.model.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.model.dto.VinculoDTO;
import br.ufrn.imd.pode.service.GenericService;
import br.ufrn.imd.pode.service.VinculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vinculo")
public class VinculoController extends GenericController<Vinculo, VinculoDTO, Long> {

	private VinculoService service;

	@Autowired
	public void setService(VinculoService service) {
		this.service = service;
	}

	@Override
	protected GenericService<Vinculo, VinculoDTO, Long> service() {
		return this.service;
	}

	@PostMapping("/{id}/mudaEnfase/{enfase_id}")
	public ResponseEntity<VinculoDTO> mudaEnfase(@PathVariable Long id, @PathVariable Long enfase_id) {
		return ResponseEntity.ok(service.convertToDto(service.mudaEnfase(id, enfase_id)));
	}

	@PostMapping("/{id}/atualizaPeriodo/{periodo}")
	public ResponseEntity<VinculoDTO> mudaEnfase(@PathVariable Long id, @PathVariable Integer periodo) {
		return ResponseEntity.ok(service.convertToDto(service.atualizaPeriodoAtual(id, periodo)));
	}


}
