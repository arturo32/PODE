import API from '../../service/api';
import APIEmphasis from "../../service/api.emphasis";
import APIPes from "../../service/api.pes";
import APICourse from "../../service/api.course";
import APIVinculo from "../../service/api.vinculo";
import APIPlanoCurso from "../../service/api.plano-curso";

export const listEnfase = async (data, idCurso) => {
	return await API.get(APIEmphasis.listPorCurso(idCurso), data);
};

export const listPes = async (data) => {
	return await API.get(APIPes.create(), data);
};

export const listDisciplinasObrigatoriasCurso = async (data, idCurso) => {
	return await API.get(APICourse.listDisciplinasObrigatoriasPorCurso(idCurso), data);
};

export const listDisciplinasOptivativasCurso = async (data, idCurso) => {
	return await API.get(APICourse.listDisciplinasOptativasPorCurso(idCurso), data);
};

export const listDisciplinasObrigatoriasEnfase = async (data, idEnfase) => {
	return await API.get(APIEmphasis.listDisciplinasObrigatoriasPorEnfase(idEnfase), data);
};

export const listDisciplinasOptativasEnfase = async (data, idEnfase) => {
	return await API.get(APIEmphasis.listDisciplinasOptativasPorEnfase(idEnfase), data);
};

export const listDisciplinasObrigatoriasPes = async (data, idPes) => {
	return await API.get(APIPes.listDisciplinasObrigatoriasPorPes(idPes), data);
};

export const listDisciplinasOptativasPes = async (data, idPes) => {
	return await API.get(APIPes.listDisciplinasOptativasPorPes(idPes), data);
}

export const getInfoVinculos = async (data, idVinculo) => {
	return await API.get(APIVinculo.getInfo(idVinculo), data);
};

export const adicionaDisciplinasCursadas = async (data, idPlanoCurso) => {
	return await API.post(APIPlanoCurso.adicionaDisciplinasCursadas(idPlanoCurso), data);
};

export const adicionaDisciplinasPendentes = async (data, idPlanoCurso) => {
	return await API.post(APIPlanoCurso.adicionaDisciplinasPendentes(idPlanoCurso), data);
};



