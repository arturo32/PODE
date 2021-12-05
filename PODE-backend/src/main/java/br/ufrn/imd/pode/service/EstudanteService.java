package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.Estudante;
import br.ufrn.imd.pode.model.dto.EstudanteDTO;
import br.ufrn.imd.pode.repository.EstudanteRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class EstudanteService extends GenericService<Estudante, EstudanteDTO, Long> {

	private EstudanteRepository repository;

	@Override
	public EstudanteDTO convertToDto(Estudante estudante) {
		return new EstudanteDTO(estudante);
	}

	@Override
	public Estudante convertToEntity(EstudanteDTO dto) {
		Estudante estudante = new Estudante();
		estudante.setNome(dto.getNome());
		estudante.setEmail(dto.getEmail());
		estudante.setSenha(dto.getSenha());
		return estudante;
	}

	@Override
	public EstudanteDTO validate(EstudanteDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica nome
		if (StringUtils.isEmpty(dto.getNome())) {
			exceptionHelper.add("nome inválido");
		}
		//Verifica nome
		if (StringUtils.isEmpty(dto.getSenha())) {
			exceptionHelper.add("senha inválida");
		}
		//Verifica nome
		if (StringUtils.isEmpty(dto.getEmail())) {
			exceptionHelper.add("email inválido");
		}
		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			return dto;
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericRepository<Estudante, Long> repository() {
		return this.repository;
	}

	public EstudanteRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(EstudanteRepository repository) {
		this.repository = repository;
	}
}
