import API from '../../servico/api';
import APISubjects from '../../servico/api.subject';

export const createSubject = (data) => {
    return API.post(APISubjects.createSubject, data);
};