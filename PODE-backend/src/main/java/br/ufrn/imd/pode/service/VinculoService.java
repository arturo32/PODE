package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.Vinculo;
import br.ufrn.imd.pode.model.dto.VinculoDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.VinculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class VinculoService extends GenericService<Vinculo, VinculoDTO, Long> {

	VinculoRepository repository;

	@Override
	public VinculoDTO convertToDto(Vinculo entity) {
		return null;
	}

	@Override
	public Vinculo convertToEntity(VinculoDTO dto) {
		return null;
	}

	@Override
	protected GenericRepository<Vinculo, Long> repository() {
		return this.repository;
	}

	public VinculoRepository getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(VinculoRepository repository) {
		this.repository = repository;
	}
}