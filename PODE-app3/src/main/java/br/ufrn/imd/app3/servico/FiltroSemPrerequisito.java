package br.ufrn.imd.app3.servico;

import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.FiltroDisciplinaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class FiltroSemPrerequisito implements FiltroDisciplinaServico {

	DisciplinaBTIServico disciplinaBTIServico;

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Override
	public String obterNome() {
		return "sem_prerequisito";
	}

	@Override
	public void validar(Map<String, String> parametros) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		try {
			Integer.parseInt(parametros.get("limit"));
		} catch (Exception e) {
			exceptionHelper.add("Não foi possivel obter o parametro 'limit' necessario para o filtro");
		}
		try {
			Integer.parseInt(parametros.get("page"));
		} catch (Exception e) {
			exceptionHelper.add("Não foi possivel obter o parametro 'page' necessario para o filtro");
		}
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	public Set<DisciplinaDTO> filtrar(Map<String, String> parametros) {
		int limit = Integer.parseInt(parametros.get("limit"));
		int page = Integer.parseInt(parametros.get("page"));
		return new HashSet<>(disciplinaBTIServico.obterDisciplinasSemPrerequisitos(PageRequest.of(page, limit)));
	}
}
