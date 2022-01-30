package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app1.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.app1.repositorio.DisciplinaPeriodoRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DisciplinaPeriodoServico extends GenericoServico<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> {

	private DisciplinaPeriodoRepositorio repositorio;

	@Autowired
	public void setRepositorio(DisciplinaPeriodoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public DisciplinaPeriodoDTO converterParaDTO(DisciplinaPeriodo entity) {
		return null;
	}

	@Override
	public DisciplinaPeriodo converterParaEntidade(DisciplinaPeriodoDTO dto) {
		return null;
	}

	@Override
	protected void validar(DisciplinaPeriodoDTO dto) {

	}

	@Override
	protected GenericoRepositorio<DisciplinaPeriodo, Long> repositorio() {
		return repositorio;
	}
}
