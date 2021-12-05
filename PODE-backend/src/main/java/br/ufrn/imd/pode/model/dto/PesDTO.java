package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.Pes;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class PesDTO extends AbstractDTO {

	private String nome;

	//Carga horária mínima
	private Integer chm;

	//Carga horaria obrigatória
	private Integer cho;

	@JsonProperty("id-disciplinas-obrigatorias")
	private Set<Long> idDisciplinasObrigatorias = new HashSet<>();

	@JsonProperty("id-disciplinas-optativas")
	private Set<Long> idDisciplinasOptativas = new HashSet<>();

	public PesDTO() {
	}

	public PesDTO(Pes pes) {
		this.setId(pes.getId());
		this.setNome(pes.getNome());
		this.setChm(pes.getChm());
		this.setCho(pes.getCho());
		for (Disciplina disciplina : pes.getDisciplinasObrigatorias()) {
			this.idDisciplinasObrigatorias.add(disciplina.getId());
		}
		for (Disciplina disciplina : pes.getDisciplinasOptativas()) {
			this.idDisciplinasOptativas.add(disciplina.getId());
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Integer getCho() {
		return cho;
	}

	public void setCho(Integer cho) {
		this.cho = cho;
	}

	public Set<Long> getIdDisciplinasObrigatorias() {
		return idDisciplinasObrigatorias;
	}

	public void setIdDisciplinasObrigatorias(Set<Long> idDisciplinasObrigatorias) {
		this.idDisciplinasObrigatorias = idDisciplinasObrigatorias;
	}

	public Set<Long> getIdDisciplinasOptativas() {
		return idDisciplinasOptativas;
	}

	public void setIdDisciplinasOptativas(Set<Long> idDisciplinasOptativas) {
		this.idDisciplinasOptativas = idDisciplinasOptativas;
	}
}
