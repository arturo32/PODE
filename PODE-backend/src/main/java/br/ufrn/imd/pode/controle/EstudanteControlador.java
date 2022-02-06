package br.ufrn.imd.pode.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.modelo.Estudante;
import br.ufrn.imd.pode.modelo.dto.EstudanteDTO;
import br.ufrn.imd.pode.servico.EstudanteServico;
import br.ufrn.imd.pode.servico.GenericoServico;

@RestController
@RequestMapping("/estudantes")
public class EstudanteControlador extends GenericoControlador<Estudante, EstudanteDTO, Long> {

	private EstudanteServico service;

	@Autowired
	public void setService(EstudanteServico service) {
		this.service = service;
	}

	@Override
	protected GenericoServico<Estudante, EstudanteDTO, Long> servico() {
		return this.service;
	}

}
