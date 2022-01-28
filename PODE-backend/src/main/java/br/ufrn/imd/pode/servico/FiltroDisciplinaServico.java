package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;

import java.util.Map;
import java.util.Set;

public interface FiltroDisciplinaServico {
	String obterNome();

	default Set<DisciplinaDTO> buscarDisciplinasPorFiltro(Map<String, String> parametros) {
		this.validar(parametros);
		return filtrar(parametros);
	}

	void validar(Map<String, String> parametros);

	Set<DisciplinaDTO> filtrar(Map<String, String> parametros);
}
