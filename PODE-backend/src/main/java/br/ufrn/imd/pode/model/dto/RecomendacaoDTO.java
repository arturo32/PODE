package br.ufrn.imd.pode.model.dto;

import java.util.ArrayList;
import java.util.List;

public class RecomendacaoDTO {

	private List<DisciplinaDTO> disciplinasObrigatorias;
	private List<DisciplinaDTO> disciplinasOptativas;

	public RecomendacaoDTO() {
		this.disciplinasObrigatorias = new ArrayList<DisciplinaDTO>();
		this.disciplinasOptativas = new ArrayList<DisciplinaDTO>();
	}

	public RecomendacaoDTO(List<DisciplinaDTO> disciplinasObrigatorias, List<DisciplinaDTO> disciplinasOptativas) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
		this.disciplinasOptativas = disciplinasOptativas;
	}

	public List<DisciplinaDTO> getDisciplinasObrigatorias() {
		return disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(List<DisciplinaDTO> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public List<DisciplinaDTO> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(List<DisciplinaDTO> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}

}
