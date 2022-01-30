package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.CursoBTI;
import br.ufrn.imd.app1.modelo.Pes;
import br.ufrn.imd.app1.modelo.dto.CursoBTIDTO;
import br.ufrn.imd.app1.modelo.dto.PesDTO;
import br.ufrn.imd.app1.repositorio.CursoBTIRepositorio;
import br.ufrn.imd.app1.repositorio.PesRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.GradeCurricularServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PesServico extends GradeCurricularServico<Pes, PesDTO> {

	private PesRepositorio repositorio;

	@Autowired
	public void setRepositorio(PesRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	protected GenericoRepositorio<Pes, Long> repositorio() {
		return this.repositorio;
	}

	@Override
	public PesDTO converterParaDTO(Pes entity) {
		//TODO conversão
		return null;
	}

	@Override
	public Pes converterParaEntidade(PesDTO dto) {
		//TODO conversão
		return null;
	}

	@Override
	protected void validar(PesDTO dto) {
		//TODO validação
	}
}
