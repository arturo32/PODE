package br.ufrn.imd.pode.servico;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Usuario;
import br.ufrn.imd.pode.modelo.dto.UsuarioDTO;
import br.ufrn.imd.pode.helper.ErrorPersistenciaHelper;

@Service
@Transactional
public abstract class UsuarioService<T extends Usuario, D extends UsuarioDTO> extends GenericoServico<T, D, Long>{

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	protected void validarModoPersistencia(TipoPersistencia tipoPersistencia, D dto) {
		ErrorPersistenciaHelper.validate(tipoPersistencia, super.obterNomeModelo(), dto);
	}

	protected void validarUsuario(UsuarioDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		boolean novaSenha = false;
		if (dto.getId() != null) {
			Usuario entity = this.buscarPorId(dto.getId());
			if (StringUtils.isEmpty(dto.getNome())){
				dto.setNome(entity.getNome());
			}
			if (StringUtils.isEmpty(dto.getEmail())){
				dto.setEmail(entity.getEmail());
			}
			if (StringUtils.isEmpty(dto.getSenha())){
				dto.setSenha(entity.getSenha());
			} else {
				novaSenha = true;
			}
		}
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
			if (dto.getId() == null || novaSenha) {
				// criptografa a senha se for o cadastro do administrador
				dto.setSenha(encoder.encode(dto.getSenha()));
			}
		} else {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}
}
