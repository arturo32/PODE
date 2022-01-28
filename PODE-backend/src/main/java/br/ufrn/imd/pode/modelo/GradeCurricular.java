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
	protected Integer chm;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "grade_curricular_disciplina", joinColumns = {
			@JoinColumn(name = "grade_curricular_id")}, inverseJoinColumns = {@JoinColumn(name = "disciplina_id")})
	protected Set<Disciplina> disciplinas;

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

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
}
