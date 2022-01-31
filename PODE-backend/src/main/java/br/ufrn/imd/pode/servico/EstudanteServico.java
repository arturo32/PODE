package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Estudante;
import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.EstudanteDTO;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;
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
public class EstudanteServico extends UsuarioService<Estudante, EstudanteDTO> {

	private EstudanteRepositorio repositorio;
	private VinculoServicoInterface vinculoServico;

	@Autowired
	public void setVinculoServico(VinculoServicoInterface vinculoServico) {
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

		if (dto.getIdVinculos() != null) {
			estudante.setVinculos(new HashSet<>());

			for (Long idVinculo : dto.getIdVinculos()) {
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
	public void validar(EstudanteDTO dto) {
		this.validarUsuario(dto);
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
		if (estudante.isPresent()) {
			return estudante.get();
		} else {
			throw new EntidadeNaoEncontradaException("Estudante de email: '" + email + "' não encontrado");
		}
	}
}
