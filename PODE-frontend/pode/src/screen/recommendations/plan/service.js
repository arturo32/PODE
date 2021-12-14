import API from '../../../service/api';
import APIRecommendation from '../../../service/api.recommendation';

export const getSubjectsCoursePlan = async (studentBondId) => {
    return await API.get(APIRecommendation.getSubjectsCoursePlan(studentBondId), { withCredentials: true });
};