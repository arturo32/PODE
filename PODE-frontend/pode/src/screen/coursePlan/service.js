import API from '../../service/api';
import APIEnfase from "../../service/api.enfase";
import APIPes from "../../service/api.pes";

export const listEnfase = async (data) => {
	return await API.get(APIEnfase.create(), data);
};

export const listPes = async (data) => {
	return await API.get(APIPes.create(), data);
};