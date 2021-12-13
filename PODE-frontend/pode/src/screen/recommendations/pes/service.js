import API from '../../../service/api';
import APIRecommendation from '../../../service/api.recommendation';

export const getPesByProximityCompletion = async (studentBondId) => {
    return await API.get(APIRecommendation.getPesByProximityCompletion(studentBondId), { withCredentials: true });
};