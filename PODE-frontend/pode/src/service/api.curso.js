export default class APICurso {
	static listDisciplinasObrigatoriasPorCurso(idCurso) {
		return `/curso/${idCurso}/disciplinas_obrigatorias`;
	}

	static listDisciplinasOptativasPorCurso(idCurso) {
		return `/curso/${idCurso}/disciplinas_optativas`;
	}
};