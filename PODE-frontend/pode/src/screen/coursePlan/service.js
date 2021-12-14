import API from '../../service/api';
import APIEmphasis from "../../service/api.emphasis";
import APIPes from "../../service/api.pes";
import APICourse from "../../service/api.course";

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