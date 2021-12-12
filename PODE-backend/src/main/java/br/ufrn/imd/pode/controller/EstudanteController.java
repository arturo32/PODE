package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Estudante;
import br.ufrn.imd.pode.model.dto.EstudanteDTO;
import br.ufrn.imd.pode.service.EstudanteService;
import br.ufrn.imd.pode.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estudante")
public class EstudanteController extends GenericController<Estudante, EstudanteDTO, Long> {

	private EstudanteService service;

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	public void setService(EstudanteService service) {
		this.service = service;
	}

	@Override
	protected GenericService<Estudante, EstudanteDTO, Long> service() {
		return this.service;
	}

	@Override
	public ResponseEntity<EstudanteDTO> save(@RequestBody EstudanteDTO dto) {
		dto.setSenha(encoder.encode(dto.getSenha()));
		return super.save(dto);
	}
}
