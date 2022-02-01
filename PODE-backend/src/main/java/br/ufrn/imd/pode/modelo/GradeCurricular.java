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
	protected Integer chobm;

	@NotNull
	protected Integer chopm;

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

	public abstract Set<DisciplinaInterface> getDisciplinasObrigatorias();

	public abstract Set<DisciplinaInterface> getDisciplinasOptativas();
}
