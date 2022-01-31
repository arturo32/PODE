package br.ufrn.imd.app1.modelo.dto;

import br.ufrn.imd.app1.modelo.Pes;
import br.ufrn.imd.app1.modelo.PlanoCursoPes;
import br.ufrn.imd.pode.modelo.dto.PlanoCursoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.stream.Collectors;

public class PlanoCursoPesDTO extends PlanoCursoDTO {

	@JsonProperty("id-pes")
	private Set<Long> idPes;

	public PlanoCursoPesDTO() {
	}

	public PlanoCursoPesDTO(PlanoCursoPes planoCurso) {
		super(planoCurso);
		idPes = planoCurso.getGradesParalelas().stream().map(Pes::getId).collect(Collectors.toSet());
	}

	public Set<Long> getIdPes() {
		return idPes;
	}

	public void setIdPes(Set<Long> idPes) {
		this.idPes = idPes;
	}
}
