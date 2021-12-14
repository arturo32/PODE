import API from '../../service/api';
import APIStudent from '../../service/api.student';
import APIBond from '../../service/api.bond';

export const createStudent = async (data) => {
    return await API.post(APIStudent.create(), data);
};

export const createVinculo = async (data) => {
    return await API.post(APIBond.create(), data);
};