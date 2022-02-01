package br.ufrn.imd.pode.servico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;

@Service
@Transactional
public interface FiltroDisciplinaServico {
	String obterNome();

	void validar(Map<String, String> parametros);

	Set<DisciplinaDTO> filtrar(Map<String, String> parametros);

	default Set<DisciplinaDTO> buscarDisciplinasPorFiltro(Map<String, String> parametros) {
		this.validar(parametros);
		return this.filtrar(parametros);
	}
}
