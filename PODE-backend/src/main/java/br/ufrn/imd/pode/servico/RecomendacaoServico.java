package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.modelo.dto.RecomendacaoDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface RecomendacaoServico {

	String getNomeServico();

	void validar(Long vinculoId);

	default RecomendacaoDTO recomendarDisciplinas(Long idVinculo) {
		validar(idVinculo);
		return recomendar(idVinculo);
	}

	RecomendacaoDTO recomendar(Long vinculoId);

}
