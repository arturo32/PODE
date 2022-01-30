package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.ModeloAbstrato;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(
		name = "disciplinaperiodo"
)
public class DisciplinaPeriodo extends ModeloAbstrato<Long> implements DisciplinaInterface {

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "SEQ_DISCIPLINA_PERIODO"
	)
	@SequenceGenerator(
			name = "SEQ_DISCIPLINA_PERIODO",
			sequenceName = "id_seq_disciplina_periodo",
			allocationSize = 1
	)
	protected Long id;

	@ManyToOne
	private DisciplinaBTI disciplina;

	private Integer periodo;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public DisciplinaBTI getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(DisciplinaBTI disciplina) {
		this.disciplina = disciplina;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	@Override
	public String getCodigo() {
		return disciplina.getCodigo();
	}

	@Override
	public void setCodigo(String codigo) {
		// TODO: o que fazer aqui?
	}

	@Override
	public String getNome() {
		return disciplina.getNome();
	}

	@Override
	public void setNome(String nome) {
		// TODO: o que fazer aqui?
	}

	@Override
	public Integer getCh() {
		return disciplina.getCh();
	}

	@Override
	public void setCh(Integer ch) {
		// TODO: o que fazer aqui?
	}

	@Override
	public String getPrerequisitos() {
		return disciplina.getPrerequisitos();
	}

	@Override
	public void setPrerequisitos(String prerequisitos) {
		// TODO: o que fazer aqui?
	}

	@Override
	public boolean checarPrerequisitosCodigos(Collection<String> codigos) {
		return disciplina.checarPrerequisitosCodigos(codigos);
	}
}
