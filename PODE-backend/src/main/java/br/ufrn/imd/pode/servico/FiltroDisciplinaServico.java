package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;

import java.util.Map;
import java.util.Set;

public interface FiltroDisciplinaServico {
	String obterNome();

	Set<DisciplinaDTO> buscarDisciplinasPorFiltro(Map<String, Object> parametros);

	void validar(Map<String, Object> parametros);

	Set<DisciplinaDTO> filtrar(Map<String, Object> parametros);
}
