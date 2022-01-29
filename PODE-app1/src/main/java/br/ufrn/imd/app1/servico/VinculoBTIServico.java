package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.VinculoBTI;
import br.ufrn.imd.app1.modelo.dto.VinculoBTIDTO;
import br.ufrn.imd.pode.servico.VinculoServico;

public class VinculoBTIServico extends VinculoServico<VinculoBTI, VinculoBTIDTO> {

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
}
