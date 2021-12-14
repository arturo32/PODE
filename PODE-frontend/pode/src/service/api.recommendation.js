const BASE = '/recomendacoes';

export default class APIRecommendation {
    static getPesByProximityCompletion(studentBondId) {
        return `${BASE}/disciplinas-por-proximadade-conclusao-pes/${studentBondId}`;
    }
    static getSubjectsCoursePlan(studentBondId) {
        return `${BASE}/disciplinas-por-plano-de-curso/${studentBondId}`;
    }
};