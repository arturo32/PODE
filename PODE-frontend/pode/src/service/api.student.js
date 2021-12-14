const BASE = '/estudantes';

export default class APIStudent {
    static getInfo(id) {
        return `${BASE}/${id}`;
    }
    static create() {
        return `${BASE}`;
    }
};