package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.CursoBTI;
import br.ufrn.imd.app1.modelo.dto.CursoBTIDTO;
import br.ufrn.imd.app1.repositorio.CursoBTIRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.GradeCurricularServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CursoBTIServico extends GradeCurricularServico<CursoBTI, CursoBTIDTO> {

	private CursoBTIRepositorio repositorio;

	@Autowired
	public void setRepositorio(CursoBTIRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	protected GenericoRepositorio<CursoBTI, Long> repositorio() {
		return this.repositorio;
	}

	@Override
	public CursoBTIDTO converterParaDTO(CursoBTI entity) {
		//TODO conversão
		return null;
	}

	@Override
	public CursoBTI converterParaEntidade(CursoBTIDTO dto) {
		//TODO conversão
		return null;
	}

	@Override
	protected void validar(CursoBTIDTO dto) {
		//TODO validação
	}
}
