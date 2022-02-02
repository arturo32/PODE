package br.ufrn.imd.pode.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "disciplinacursada")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DisciplinaCursada extends ModeloAbstrato<Long> implements DisciplinaInterface {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISCIPLINA_CURSADA")
	@SequenceGenerator(name = "SEQ_DISCIPLINA_CURSADA", sequenceName = "id_seq_disciplina_cursada", allocationSize = 1)
	protected Long id;

	@NotNull
	@ManyToOne
	protected Disciplina disciplina;

	public DisciplinaCursada() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	@Override
	public String getCodigo() {
		return disciplina.getCodigo();
	}

	@Override
	public String getNome() {
		return disciplina.getNome();
	}

	@Override
	public Integer getCh() {
		return disciplina.getCh();
	}

	@Override
	public String getPrerequisitos() {
		return disciplina.getPrerequisitos();
	}

	@Override
	public boolean checarPrerequisitosCodigos(Collection<String> codigos) {
		return disciplina.checarPrerequisitosCodigos(codigos);
	}
}