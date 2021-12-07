import API from '../../service/api';
import APIStudent from '../../service/api.student';

export const create = async (data) => {
    return await API.post(APIStudent.create(), data);
};