package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.app1.modelo.dto.DisciplinaBTIDTO;
import br.ufrn.imd.pode.servico.DisciplinaServico;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DisciplinaBTIServico extends DisciplinaServico<DisciplinaBTI, DisciplinaBTIDTO> {

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
}
