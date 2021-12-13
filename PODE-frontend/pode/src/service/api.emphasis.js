const BASE = '/enfase';

export default class APIEmphasis {
	static create() {
		return `${BASE}`;
	}

	static listPorCurso(idCurso) {
		return `${BASE}/curso/${idCurso}`;
	}
};