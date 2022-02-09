package br.ufrn.imd.app2.servico;

import br.ufrn.imd.pode.servico.GradeCurricularServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

import br.ufrn.imd.app2.modelo.Enfase;
import br.ufrn.imd.app2.modelo.dto.EnfaseDTO;
import br.ufrn.imd.app2.repositorio.EnfaseRepositorio;

@Service
@Transactional
public class EnfaseServico extends GradeCurricularServico<Enfase, EnfaseDTO> {

	private DisciplinaPeriodoServico disciplinaPeriodoServico;
	private DisciplinaBTIServico disciplinaBTIServico;
	private EnfaseRepositorio repositorio;

	@Autowired
	public void setRepositorio(EnfaseRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Autowired
	public void setDisciplinaPeriodoServico(DisciplinaPeriodoServico disciplinaPeriodoServico) {
		this.disciplinaPeriodoServico = disciplinaPeriodoServico;
	}

	@Override
	protected GenericoRepositorio<Enfase, Long> repositorio() {
		return this.repositorio;
	}

	@Override
	public EnfaseDTO converterParaDTO(Enfase entity) {
		return new EnfaseDTO(entity);
	}

	@Override
	public Enfase converterParaEntidade(EnfaseDTO dto) {
		//TODO:
		return new Enfase();
	}

	@Override
	protected void validar(EnfaseDTO dto) {
		// TODO
	}

}
