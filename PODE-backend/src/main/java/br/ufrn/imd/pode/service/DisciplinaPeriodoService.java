package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.repository.DisciplinaPeriodoRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DisciplinaPeriodoService extends GenericService<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> {

	private DisciplinaPeriodoRepository repository;

	@Override
	public DisciplinaPeriodoDTO convertToDto(DisciplinaPeriodo entity) {
		return null;
	}

	@Override
	public DisciplinaPeriodo convertToEntity(DisciplinaPeriodoDTO dto) {
		return null;
	}

	@Override
	protected GenericRepository<DisciplinaPeriodo, Long> repository() {
		return this.repository;
	}

	public DisciplinaPeriodoRepository getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(DisciplinaPeriodoRepository repository) {
		this.repository = repository;
	}
}