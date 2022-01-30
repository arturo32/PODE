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
public abstract class VinculoServico<T extends Vinculo, E extends VinculoDTO> extends GenericoServico<T, E, Long> {

	private VinculoRepositorio<T> repositorio;
	private EstudanteServico estudanteServico;

	@Override
	protected GenericoRepositorio<T, Long> repositorio() {
		return this.repositorio;
	}

	public VinculoRepositorio<T> getRepositorio() {
		return repositorio;
	}

	@Autowired
	public void setRepositorio(VinculoRepositorio<T> repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setEstudanteServico(EstudanteServico estudanteServico) {
		this.estudanteServico = estudanteServico;
	}

	public Double obterPercentualConclusao(Long id) {
		// TODO: Validar o id
		return gerarPercentualConclusao(id);
	}

	protected abstract Double gerarPercentualConclusao(Long id);
}
