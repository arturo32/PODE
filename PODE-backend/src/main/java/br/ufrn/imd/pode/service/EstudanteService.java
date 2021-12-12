package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.Estudante;
import br.ufrn.imd.pode.model.dto.EstudanteDTO;
import br.ufrn.imd.pode.repository.EstudanteRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class EstudanteService extends GenericService<Estudante, EstudanteDTO, Long> {

	private EstudanteRepository repository;
	private VinculoService vinculoService;

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public VinculoService getVinculoService() {
		return vinculoService;
	}

	@Autowired
	public void setVinculoService(VinculoService vinculoService) {
		this.vinculoService = vinculoService;
	}

	@Override
	public EstudanteDTO convertToDto(Estudante estudante) {
		return new EstudanteDTO(estudante);
	}

	@Override
	public Estudante convertToEntity(EstudanteDTO dto) {
		Estudante estudante = new Estudante();

		//Se for uma edição
		if (dto.getId() != null) {
			estudante = this.findById(dto.getId());
		}

		estudante.setId(dto.getId());
		if (dto.getNome() != null) {
			estudante.setNome(dto.getNome());
		}
		if (dto.getEmail() != null) {
			estudante.setEmail(dto.getEmail());
		}
		if (dto.getSenha() != null) {
			estudante.setSenha(dto.getSenha());
		}

		if(dto.getIdVinculos() != null){
			estudante.setVinculos(new HashSet<>());

			for (Long idVinculo: dto.getIdVinculos()) {
				try {
					estudante.getVinculos().add(this.vinculoService.findById(idVinculo));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("vinculo inconsistente");
				}
			}
		}

		return estudante;
	}

	@Override
	public EstudanteDTO validate(EstudanteDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica nome
		if (StringUtils.isEmpty(dto.getNome())) {
			exceptionHelper.add("nome inválido");
		}
		//Verifica senha
		if (StringUtils.isEmpty(dto.getSenha()) || dto.getSenha().length() < 7) {
			exceptionHelper.add("senha inválida");
		}
		//Verifica email
		String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		if (StringUtils.isEmpty(dto.getEmail()) || !Pattern.compile(regex).matcher(dto.getEmail()).matches()) {
			exceptionHelper.add("email inválido");
		}
		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			dto.setSenha(encoder.encode(dto.getSenha()));
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

	public Estudante findByEmail(String email) {
		Optional<Estudante> estudante = repository.findByAtivoIsTrueAndEmail(email);
		if (estudante.isPresent()){
			return estudante.get();
		} else {
			throw new EntityNotFoundException("Estudante de email: '" + email + "' não encontrado");
		}
	}

	public Estudante findByNome(String nome) {
		Optional<Estudante> estudante = repository.findByAtivoIsTrueAndNome(nome);
		if (estudante.isPresent()){
			return estudante.get();
		} else {
			throw new EntityNotFoundException("Estudante de nome: '" + nome + "' não encontrado");
		}
	}
}
