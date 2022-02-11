package br.ufrn.imd.app3.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.GradeCurricularControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app3.modelo.Curso;
import br.ufrn.imd.app3.modelo.dto.CursoDTO;
import br.ufrn.imd.app3.servico.CursoServico;

@RestController
@RequestMapping("/cursos")
public class CursoControlador extends GradeCurricularControlador<Curso, CursoDTO> {

	private CursoServico cursoServico;

	@Autowired
	public void setCursoBTIServico(CursoServico cursoServico) {
		this.cursoServico = cursoServico;
	}

	@Override
	protected GenericoServico<Curso, CursoDTO, Long> servico() {
		return this.cursoServico;
	}

}
