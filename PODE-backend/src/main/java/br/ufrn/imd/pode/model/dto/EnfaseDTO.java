package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.Enfase;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class EnfaseDTO extends AbstractDTO {

	private String nome;

	@JsonProperty("id-curso")
	private Long idCurso;

	@JsonProperty("id-disciplinas-obrigatorias")
	private Set<Long> idDisciplinasObrigatorias = new HashSet<>();

	public EnfaseDTO() {
	}

	public EnfaseDTO(Enfase enfase) {
		setId(enfase.getId());
		setNome(enfase.getNome());
		setIdCurso(enfase.getCurso().getId());
		for(DisciplinaPeriodo disciplinaPeriodo : enfase.getDisciplinasObrigatorias()) {
			this.idDisciplinasObrigatorias.add(disciplinaPeriodo.getId());
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public Set<Long> getIdDisciplinasObrigatorias() {
		return idDisciplinasObrigatorias;
	}

	public void setIdDisciplinasObrigatorias(Set<Long> idDisciplinasObrigatorias) {
		this.idDisciplinasObrigatorias = idDisciplinasObrigatorias;
	}
}
