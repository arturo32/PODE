package br.ufrn.imd.pode.servico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.pode.modelo.dto.RecomendacaoDTO;

@Service
@Transactional
public interface RecomendacaoServico {

	String getNomeServico();

	void validar(Long vinculoId);

	RecomendacaoDTO recomendar(Long vinculoId);

	default RecomendacaoDTO recomendarDisciplinas(Long idVinculo) {
		validar(idVinculo);
		return recomendar(idVinculo);
	}

}
