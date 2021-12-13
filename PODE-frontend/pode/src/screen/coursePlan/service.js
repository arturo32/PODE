import API from '../../service/api';
import APIEnfase from "../../service/api.enfase";
import APIPes from "../../service/api.pes";
import APICurso from "../../service/api.curso";

export const listEnfase = async (data, idCurso) => {
	return await API.get(APIEnfase.listPorCurso(idCurso), data);
};

export const listPes = async (data) => {
	return await API.get(APIPes.create(), data);
};

export const listDisciplinasObrigatoriasCurso = async (data, idCurso) => {
	return await API.get(APICurso.listDisciplinasObrigatoriasPorCurso(idCurso), data);
};

export const listDisciplinasOptivativasCurso = async (data, idCurso) => {
	return await API.get(APICurso.listDisciplinasOptativasPorCurso(idCurso), data);
};