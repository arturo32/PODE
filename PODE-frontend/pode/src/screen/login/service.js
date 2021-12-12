import API from '../../service/api';
import APIAuthentication from '../../service/api.authentication';

export const login = async (data) => {
    return await API.post(APIAuthentication.login(), data);
};