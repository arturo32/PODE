import API from '../../service/api';
import APIStudent from '../../service/api.student';
import APIVinculo from '../../service/api.vinculo';

export const create = async (data) => {
    return await API.post(APIStudent.create(), data);
};

export const createVinculo = async (data) => {
    return await API.post(APIVinculo.create(), data);
};