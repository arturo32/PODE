package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.VinculoBTI;
import br.ufrn.imd.app1.modelo.dto.VinculoBTIDTO;
import br.ufrn.imd.app1.repositorio.VinculoBTIRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.VinculoRepositorio;
import br.ufrn.imd.pode.servico.VinculoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class VinculoBTIServico extends VinculoServico<VinculoBTI, VinculoBTIDTO> {

	private VinculoBTIRepositorio repositorio;

	@Autowired
	public void setRepositorio(VinculoBTIRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public VinculoRepositorio<VinculoBTI> getRepositorio() {
		return repositorio;
	}

	@Override
	public Double obterPercentualConclusao(Long id) {
		return null;
	}

	@Override
	protected Double gerarPercentualConclusao(Long id) {
		return null;
	}

	@Override
	public VinculoBTIDTO converterParaDTO(VinculoBTI entity) {
		return null;
	}

	@Override
	public VinculoBTI converterParaEntidade(VinculoBTIDTO dto) {
		return null;
	}

	@Override
	protected void validar(VinculoBTIDTO dto) {

	}

	@Override
	protected GenericoRepositorio<VinculoBTI, Long> repositorio() {
		return repositorio;
	}
}
