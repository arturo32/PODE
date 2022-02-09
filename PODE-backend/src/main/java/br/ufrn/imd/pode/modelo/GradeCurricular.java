package br.ufrn.imd.pode.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "gradecurricular")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GradeCurricular extends ModeloAbstrato<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRADECURRICULAR")
	@SequenceGenerator(name = "SEQ_GRADECURRICULAR", sequenceName = "id_seq_grade_curricular", allocationSize = 1)
	protected Long id;

	@NotNull
	@NotBlank
	protected String nome;

	@NotNull
	//Carga horária obrigatória mínima
	protected Integer chobm;

	@NotNull
	//Carga horária optativa mínima
	protected Integer chopm;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "gradecurricular_disciplina_obrigatorias", joinColumns = {
			@JoinColumn(name = "gradecurricular_id")}, inverseJoinColumns = {@JoinColumn(name = "disciplina_id")})
	protected Set<DisciplinaCursada> disciplinasObrigatorias;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "gradecurricular_disciplina_optativas", joinColumns = {
			@JoinColumn(name = "gradecurricular_id")}, inverseJoinColumns = {@JoinColumn(name = "disciplina_id")})
	protected Set<Disciplina> disciplinasOptativas;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getChobm() {
		return chobm;
	}

	public void setChobm(Integer chobm) {
		this.chobm = chobm;
	}

	public Integer getChopm() {
		return chopm;
	}

	public void setChopm(Integer chopm) {
		this.chopm = chopm;
	}

	public Set<DisciplinaCursada> getDisciplinasObrigatorias() {
		return disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaCursada> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public Set<Disciplina> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<Disciplina> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}
}
