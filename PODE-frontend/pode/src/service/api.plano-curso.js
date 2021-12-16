const BASE = '/planos-de-curso';

export default class APIPlanoCurso {
	static getInfo(id) {
		return `${BASE}/${id}`;
	}
	static create() {
		return `${BASE}`;
	}
	static adicionaDisciplinasCursadas(id){
		return `${BASE}/${id}/disciplinas-cursadas`;
	}
	static adicionaDisciplinasPendentes(id){
		return `${BASE}/${id}/disciplinas-pendentes`;
	}
};