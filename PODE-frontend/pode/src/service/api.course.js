const BASE = '/curso';

export default class APICourse {
	static listDisciplinasObrigatoriasPorCurso(idCurso) {
		return `${BASE}/${idCurso}/disciplinas_obrigatorias`;
	}

	static listDisciplinasOptativasPorCurso(idCurso) {
		return `${BASE}/${idCurso}/disciplinas_optativas`;
	}
};