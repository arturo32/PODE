package br.ufrn.imd.app1.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app1.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.app1.repositorio.DisciplinaPeriodoRepositorio;

@Service
@Transactional
public class DisciplinaPeriodoServico extends GenericoServico<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> {

	private DisciplinaBTIServico disciplinaBTIServico;
	private DisciplinaPeriodoRepositorio repositorio;

	@Autowired
	public void setRepositorio(DisciplinaPeriodoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Override
	public DisciplinaPeriodoDTO converterParaDTO(DisciplinaPeriodo entity) {
		return new DisciplinaPeriodoDTO(entity);
	}

	@Override
	public DisciplinaPeriodo converterParaEntidade(DisciplinaPeriodoDTO dto) {
		DisciplinaPeriodo disciplinaPeriodo = new DisciplinaPeriodo();

		//Se for uma edição
		if (dto.getId() != null) {
			disciplinaPeriodo = this.buscarPorId(dto.getId());
		}

		disciplinaPeriodo.setId(dto.getId());

		if (dto.getIdDisciplinaBTI() == null) {
			throw new EntidadeInconsistenteException("disciplina inconsistente");
		}
		try {
			disciplinaPeriodo
					.setDisciplina(this.disciplinaBTIServico.buscarPorId(dto.getIdDisciplinaBTI()));
		} catch (EntidadeNaoEncontradaException entityNotFoundException){
			throw new EntidadeInconsistenteException("disciplina inconsistente");
		}

		if (dto.getPeriodo() != null) {
			disciplinaPeriodo.setPeriodo(dto.getPeriodo());
		}

		return disciplinaPeriodo;
	}

	@Override
	protected void validar(DisciplinaPeriodoDTO dto) {
		// TODO
	}

	@Override
	protected GenericoRepositorio<DisciplinaPeriodo, Long> repositorio() {
		return repositorio;
	}
}
