export default class APIEnfase {
	static create() {
		return '/enfase';
	}

	static listPorCurso(idCurso) {
		return '/enfase/curso/' + idCurso;
	}
};