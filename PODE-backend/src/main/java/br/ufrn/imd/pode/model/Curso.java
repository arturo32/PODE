package br.ufrn.imd.pode.model;

import br.ufrn.imd.pode.model.interfaces.IGradeCurricularPrimaria;
import br.ufrn.imd.pode.model.dto.CursoDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "curso")
public class Curso extends AbstractModel<Long> implements IGradeCurricularPrimaria {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CURSO")
	@SequenceGenerator(name = "SEQ_CURSO", sequenceName = "id_seq_curso", allocationSize = 1)
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String nome;

	@NotNull
	//Carga horária mínima
	private Integer chm;

	@NotNull
	//Carga horária obrigatória
	private Integer cho;

	@NotNull
	//Carga horária optativa minima
	private Integer chom;

	@NotNull
	//Carga horária complementar mínima
	private Integer chcm;

	@NotNull
	//Carga horária eletiva máxima
	private Integer chem;

	@NotNull
	//Carga horária mínima por período
	private Integer chminp;

	@NotNull
	//Carga horária máxima por período
	private Integer chmaxp;

	@NotNull
	private Integer prazoMinimo;

	@NotNull
	private Integer prazoMaximo;

	@NotNull
	private Integer prazoEsperado;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "curso_disciplina_obrigatoria",
			joinColumns = {@JoinColumn(name = "curso_id")},
			inverseJoinColumns = {@JoinColumn(name = "disciplina_periodo_id")})
	private Set<DisciplinaPeriodo> disciplinasObrigatorias = new HashSet<>();

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "curso_disciplina_optativa",
			joinColumns = {@JoinColumn(name = "curso_id")},
			inverseJoinColumns = {@JoinColumn(name = "disciplina_id")})
	private Set<Disciplina> disciplinasOptativas = new HashSet<>();

	public Curso() {
	}

	public Curso(String nome, Integer chm, Integer cho, Integer chom, Integer chcm, Integer chem, Integer chminp, Integer chmaxp, Integer prazoMinimo, Integer prazoMaximo, Integer prazoEsperado) {
		this.nome = nome;
		this.chm = chm;
		this.cho = cho;
		this.chom = chom;
		this.chcm = chcm;
		this.chem = chem;
		this.chminp = chminp;
		this.chmaxp = chmaxp;
		this.prazoMinimo = prazoMinimo;
		this.prazoMaximo = prazoMaximo;
		this.prazoEsperado = prazoEsperado;
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

	@Override
	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	@Override
	public Integer getCho() {
		return cho;
	}

	public void setCho(Integer cho) {
		this.cho = cho;
	}

	@Override
	public Integer getChom() {
		return chom;
	}

	public void setChom(Integer chom) {
		this.chom = chom;
	}

	@Override
	public Integer getChcm() {
		return chcm;
	}

	public void setChcm(Integer chcm) {
		this.chcm = chcm;
	}

	@Override
	public Integer getChem() {
		return chem;
	}

	public void setChem(Integer chem) {
		this.chem = chem;
	}

	@Override
	public Integer getChminp() {
		return chminp;
	}

	public void setChminp(Integer chminp) {
		this.chminp = chminp;
	}

	@Override
	public Integer getChmaxp() {
		return chmaxp;
	}

	public void setChmaxp(Integer chmaxp) {
		this.chmaxp = chmaxp;
	}

	@Override
	public Integer getPrazoMinimo() {
		return prazoMinimo;
	}

	public void setPrazoMinimo(Integer prazoMinimo) {
		this.prazoMinimo = prazoMinimo;
	}

	@Override
	public Integer getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(Integer prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	@Override
	public Integer getPrazoEsperado() {
		return prazoEsperado;
	}

	public void setPrazoEsperado(Integer prazoEsperado) {
		this.prazoEsperado = prazoEsperado;
	}

	@Override
	public Set<DisciplinaPeriodo> getDisciplinasObrigatorias() {
		return disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaPeriodo> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	@Override
	public Set<Disciplina> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<Disciplina> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}

	public void adicionarDisciplinaObrigatoria(DisciplinaPeriodo disciplinaPeriodo) {
		this.disciplinasObrigatorias.add(disciplinaPeriodo);
	}

	public void adicionarDisciplinaOptativa(Disciplina disciplina) {
		this.disciplinasOptativas.add(disciplina);
	}
}