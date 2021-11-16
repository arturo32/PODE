import API from '../../service/api';
import APISubjects from '../../service/api.subject';

export const createSubject = (data) => {
    return API.post(APISubjects.createSubject, data);
};