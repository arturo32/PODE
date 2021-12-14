const BASE = '/enfases';

export default class APIEmphasis {
	static create() {
		return `${BASE}`;
	}

	static listPorCurso(idCurso) {
		return `${BASE}/cursos/${idCurso}`;
	}
};