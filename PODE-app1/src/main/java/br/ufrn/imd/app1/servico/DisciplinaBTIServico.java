package br.ufrn.imd.app1.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.DisciplinaServico;

import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.app1.modelo.dto.DisciplinaBTIDTO;
import br.ufrn.imd.app1.repositorio.DisciplinaBTIRepositorio;

import java.util.Set;

@Service
@Transactional
public class DisciplinaBTIServico extends DisciplinaServico<DisciplinaBTI, DisciplinaBTIDTO> {

	private DisciplinaBTIRepositorio repositorio;

	@Autowired
	public void setRepositorio(DisciplinaBTIRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public DisciplinaBTIDTO converterParaDTO(DisciplinaBTI disciplina) {
		return new DisciplinaBTIDTO(disciplina);
	}

	@Override
	public DisciplinaBTI converterParaEntidade(DisciplinaBTIDTO dto) {
		DisciplinaBTI disciplina = new DisciplinaBTI();
		if (dto.getId() != null) {
			disciplina = this.buscarPorId(dto.getId());
		}

		disciplina.setId(dto.getId());
		if (dto.getCodigo() != null) {
			disciplina.setCodigo(dto.getCodigo());
		}

		if (dto.getNome() != null) {
			disciplina.setNome(dto.getNome());
		}

		if (dto.getCh() != null) {
			disciplina.setCh(dto.getCh());
		}

		if (dto.getPrerequisitos() != null) {
			disciplina.setPrerequisitos(dto.getPrerequisitos());
		}

		if (dto.getEquivalentes() != null) {
			disciplina.setEquivalentes(dto.getEquivalentes());
		}

		return disciplina;
	}

	@Override
	public void validar(DisciplinaBTIDTO dto) {
		super.validar(dto);
	}

	@Override
	protected GenericoRepositorio<DisciplinaBTI, Long> repositorio() {
		return repositorio;
	}

	public Set<Long> obterDisciplinasObrigatoriasPendentesPes(long vinculoId, long pesId) {
		return this.repositorio.findDisciplinasObrigatoriasPendentesByVinculoAndPes(vinculoId, pesId);
	}

	public Set<Long> obterDisciplinasOptativasPendentesPes(long vinculoId, long pesId) {
		return this.repositorio.findDisciplinasOptativasPendentesByVinculoAndPes(vinculoId, pesId);
	}

}