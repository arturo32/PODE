package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Administrador;
import br.ufrn.imd.pode.modelo.dto.AdministradorDTO;
import br.ufrn.imd.pode.repositorio.AdministradorRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class AdministradorServico extends GenericoServico<Administrador, AdministradorDTO, Long> {

	private AdministradorRepositorio repository;

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public AdministradorDTO converterParaDTO(Administrador administrador) {
		return new AdministradorDTO(administrador);
	}

	@Override
	public Administrador converterParaEntidade(AdministradorDTO dto) {
		Administrador administrador = new Administrador();

		//Se for uma edição
		if (dto.getId() != null) {
			administrador = this.buscarPorId(administrador.getId());
		}

		administrador.setId(dto.getId());
		if (dto.getNome() != null) {
			administrador.setNome(dto.getNome());
		}
		if (dto.getEmail() != null) {
			administrador.setEmail(dto.getEmail());
		}
		if (dto.getSenha() != null) {
			administrador.setSenha(dto.getSenha());
		}

		return administrador;
	}

	@Override
	public AdministradorDTO validar(AdministradorDTO dto) {
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
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericoRepositorio<Administrador, Long> repositorio() {
		return this.repository;
	}

	public AdministradorRepositorio getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(AdministradorRepositorio administradorRepository) {
		this.repository = administradorRepository;
	}


	public Administrador findByEmail(String email) {
		Optional<Administrador> adm = repository.findByAtivoIsTrueAndEmail(email);
		if (adm.isPresent()) {
			return adm.get();
		} else {
			throw new EntidadeNaoEncontradaException("Administrador de email: '" + email + "' não encontrado");
		}
	}

	public Administrador findByNome(String nome) {
		Optional<Administrador> adm = repository.findByAtivoIsTrueAndNome(nome);
		if (adm.isPresent()) {
			return adm.get();
		} else {
			throw new EntidadeNaoEncontradaException("Administrador de nome: '" + nome + "' não encontrado");
		}
	}
}
