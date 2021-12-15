const BASE = '/enfases';

export default class APIEmphasis {
	static create() {
		return `${BASE}`;
	}

	static listPorCurso(idCurso) {
		return `${BASE}/cursos/${idCurso}`;
	}

	static listDisciplinasObrigatoriasPorEnfase(id) {
		return `${BASE}/${id}/disciplinas-obrigatorias`;
	}

	static listDisciplinasOptativasPorEnfase(id) {
		return `${BASE}/${id}/disciplinas-optativas`;
	}
};