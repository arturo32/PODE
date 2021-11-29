package br.ufrn.imd.pode.model;

import br.ufrn.imd.pode.model.interfaces.IGradeCurricularPrimaria;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.model.dto.EnfaseDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "enfase")
public class Enfase extends AbstractModel<Long> implements IGradeCurricularPrimaria {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENFASE")
	@SequenceGenerator(name = "SEQ_ENFASE", sequenceName = "id_seq_enfase", allocationSize = 1)
	private Long id;

	@NotNull
	@NotBlank
	@Column(unique = true)
	private String nome;

	@NotNull
	@OneToOne
	private Curso curso;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "enfase_disciplina_obrigatoria",
			joinColumns = {@JoinColumn(name = "enfase_id")},
			inverseJoinColumns = {@JoinColumn(name = "disciplina_periodo_id")})
	private Set<DisciplinaPeriodo> disciplinasObrigatorias;

	public Enfase() {
		this.disciplinasObrigatorias = new HashSet<>();
	}

	public Enfase(String nome, Curso curso) {
		this.nome = nome;
		this.curso = curso;
	}

	public Enfase(String nome, Curso curso, Set<DisciplinaPeriodo> disciplinasObrigatorias) {
		this.nome = nome;
		this.curso = curso;
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public Enfase(EnfaseDTO enfase) {
		this.id = enfase.getId();
		this.curso = new Curso(enfase.getCurso());
		for (DisciplinaPeriodoDTO disciplinaObrigatoria : enfase.getDisciplinasObrigatorias()) {
			this.disciplinasObrigatorias.add(new DisciplinaPeriodo(disciplinaObrigatoria));
		}
	}

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

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Integer getChEspecifica() {
		Integer cho_especifica = 0;
		for (DisciplinaPeriodo disciplina : this.disciplinasObrigatorias) {
			cho_especifica += disciplina.getDisciplina().getCh();
		}
		return cho_especifica;
	}

	@Override
	public Integer getChm() {
		return this.curso.getChm();
	}

	@Override
	public Integer getCho() {
		return this.curso.getCho() + this.getChEspecifica();
	}

	@Override
	public Integer getChom() {
		return this.curso.getChom() - this.getChEspecifica();
	}

	@Override
	public Integer getChcm() {
		return this.curso.getChcm();
	}

	@Override
	public Integer getChem() {
		return this.curso.getChem();
	}

	@Override
	public Integer getChmaxp() {
		return this.curso.getChmaxp();
	}

	@Override
	public Integer getChminp() {
		return this.curso.getChminp();
	}

	@Override
	public Integer getPrazoMinimo() {
		return this.curso.getPrazoMinimo();
	}

	@Override
	public Integer getPrazoMaximo() {
		return this.curso.getPrazoMaximo();
	}

	@Override
	public Integer getPrazoEsperado() {
		return this.curso.getPrazoEsperado();
	}

	@Override
	public Set<DisciplinaPeriodo> getDisciplinasObrigatorias() {
		Set<DisciplinaPeriodo> resultado = this.curso.getDisciplinasObrigatorias();
		resultado.addAll(this.disciplinasObrigatorias);
		return resultado;
	}

	@Override
	public Set<Disciplina> getDisciplinasOptativas() {
		Set<Disciplina> resultado = this.curso.getDisciplinasOptativas();
		Set<Disciplina> obrigatorias = new ArrayList<>(this.disciplinasObrigatorias).
				stream().map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toSet());
		resultado.removeAll(obrigatorias);
		return resultado;
	}

	@Override
	public Boolean concluida(Set<Disciplina> disciplinas) {
		// TODO
		return false;
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaPeriodo> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}
}