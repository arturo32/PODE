import API from '../../service/api';
import APIStudent from '../../service/api.student';

export const getInfoStudent = async (id) => {
    return await API.get(APIStudent.getInfo(id), { withCredentials: true });
};
