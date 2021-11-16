package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.model.dto.PesDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.PesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PesService extends GenericService<Pes, PesDTO, Long> {

	private PesRepository repository;

	@Override
	public PesDTO convertToDto(Pes entity) {
		return null;
	}

	@Override
	public Pes convertToEntity(PesDTO dto) {
		return null;
	}

	@Override
	protected GenericRepository<Pes, Long> repository() {
		return this.repository;
	}

	public PesRepository getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(PesRepository repository) {
		this.repository = repository;
	}
}
