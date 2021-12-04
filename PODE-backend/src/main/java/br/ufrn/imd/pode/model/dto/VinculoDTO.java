package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.Vinculo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class VinculoDTO extends AbstractDTO {

	private String matricula;

	private Integer periodoInicial;

	private Integer periodoAtual;

	private CursoDTO curso;

	private List<EnfaseDTO> enfase = new ArrayList<>();

	private PlanoCursoDTO planoCurso;

	private EstudanteDTO estudante;

	public VinculoDTO(Vinculo vinculo) {
		this.setId(vinculo.getId());
		this.setMatricula(vinculo.getMatricula());
		this.setPeriodoInicial(vinculo.getPeriodoInicial());
		this.setPeriodoAtual(vinculo.getPeriodoAtual());
		this.setCurso(new CursoDTO(vinculo.getCurso()));
		for(Enfase enfase : vinculo.getEnfases()){
			this.enfase.add(new EnfaseDTO(enfase));
		}
		this.setPlanoCurso(new PlanoCursoDTO(vinculo.getPlanoCurso()));
		this.setEstudante(new EstudanteDTO(vinculo.getEstudante()));
	}


	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Integer getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Integer periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Integer getPeriodoAtual() {
		return periodoAtual;
	}

	public void setPeriodoAtual(Integer periodoAtual) {
		this.periodoAtual = periodoAtual;
	}

	public CursoDTO getCurso() {
		return curso;
	}

	public void setCurso(CursoDTO curso) {
		this.curso = curso;
	}

	public List<EnfaseDTO> getEnfase() {
		return enfase;
	}

	public void setEnfase(List<EnfaseDTO> enfase) {
		this.enfase = enfase;
	}

	public PlanoCursoDTO getPlanoCurso() {
		return planoCurso;
	}

	public void setPlanoCurso(PlanoCursoDTO planoCurso) {
		this.planoCurso = planoCurso;
	}

	public EstudanteDTO getEstudante() {
		return estudante;
	}

	public void setEstudante(EstudanteDTO estudante) {
		this.estudante = estudante;
	}
}
