const BASE = '/cursos';

export default class APICourse {
	static listDisciplinasObrigatoriasPorCurso(idCurso) {
		return `${BASE}/${idCurso}/disciplinas-obrigatorias`;
	}

	static listDisciplinasOptativasPorCurso(idCurso) {
		return `${BASE}/${idCurso}/disciplinas-optativas`;
	}
};