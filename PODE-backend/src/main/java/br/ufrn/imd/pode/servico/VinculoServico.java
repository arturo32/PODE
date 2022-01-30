package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.VinculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public abstract class VinculoServico<T extends Vinculo, E extends VinculoDTO> extends GenericoServico<T, E, Long> implements VinculoServicoInterface{

	public abstract VinculoRepositorio<T> getRepositorio();

	public Double obterPercentualConclusao(Long id) {
		// TODO: Validar o id
		return gerarPercentualConclusao(id);
	}

	protected abstract Double gerarPercentualConclusao(Long id);
}
