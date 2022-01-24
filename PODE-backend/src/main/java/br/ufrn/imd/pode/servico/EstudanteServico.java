package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Estudante;
import br.ufrn.imd.pode.modelo.dto.EstudanteDTO;
import br.ufrn.imd.pode.repositorio.EstudanteRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

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
public class EstudanteServico extends GenericoServico<Estudante, EstudanteDTO, Long> {

	private EstudanteRepositorio repositorio;
	private VinculoServico vinculoServico;

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	public void setVinculoServico(VinculoServico vinculoServico) {
		this.vinculoServico = vinculoServico;
	}

	@Override
	public EstudanteDTO converterParaDTO(Estudante estudante) {
		return new EstudanteDTO(estudante);
	}

	@Override
	public Estudante converterParaEntidade(EstudanteDTO dto) {
		Estudante estudante = new Estudante();

		//Se for uma edição
		if (dto.getId() != null) {
			estudante = this.buscarPorId(dto.getId());
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
					estudante.getVinculos().add(this.vinculoServico.buscarPorId(idVinculo));
				} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
					throw new EntidadeInconsistenteException("vinculo inconsistente");
				}
			}
		}

		return estudante;
	}

	@Override
	public EstudanteDTO validar(EstudanteDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica nome
		if (StringUtils.isEmpty(dto.getNome())) {
			exceptionHelper.add("nome inválido");
		}
		//Verifica senha
		if (StringUtils.isEmpty(dto.getSenha()) || dto.getSenha().length() < 8) {
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
	protected GenericoRepositorio<Estudante, Long> repositorio() {
		return this.repositorio;
	}

	@Autowired
	public void setRepositorio(EstudanteRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	public Estudante buscarPorEmail(String email) {
		Optional<Estudante> estudante = repositorio.findByAtivoIsTrueAndEmail(email);
		if (estudante.isPresent()){
			return estudante.get();
		} else {
			throw new EntidadeNaoEncontradaException("Estudante de email: '" + email + "' não encontrado");
		}
	}
}
