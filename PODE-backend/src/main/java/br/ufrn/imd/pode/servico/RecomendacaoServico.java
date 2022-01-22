package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.modelo.dto.RecomendacaoDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface RecomendacaoServico {

	String getNomeServico();

	Long validar(Long vinculoId);

	RecomendacaoDTO recomendarDisciplinas(Long idVinculo);

	RecomendacaoDTO recomendar(Long vinculoId);

}
