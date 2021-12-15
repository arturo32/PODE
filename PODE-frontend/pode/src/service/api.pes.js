const BASE = '/pes';

export default class APIPes {
	static create() {
		return `${BASE}`;
	}

	static listDisciplinasObrigatoriasPorPes(id) {
		return `${BASE}/${id}/disciplinas-obrigatorias`;
	}

	static listDisciplinasOptativasPorPes(id) {
		return `${BASE}/${id}/disciplinas-optativas`;
	}
};