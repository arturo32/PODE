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
public class AdministradorServico extends UsuarioService<Administrador, AdministradorDTO> {

	private AdministradorRepositorio repository;

	@Override
	public AdministradorDTO converterParaDTO(Administrador administrador) {
		return new AdministradorDTO(administrador);
	}

	@Override
	public Administrador converterParaEntidade(AdministradorDTO dto) {
		Administrador administrador = new Administrador();

		//Se for uma edição
		if (dto.getId() != null) {
			administrador = this.buscarPorId(dto.getId());
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
	public void validar(AdministradorDTO dto) {
		this.validarUsuario(dto);
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
