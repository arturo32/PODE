const BASE = '/vinculos';

export default class APIVinculo {
	static getInfo(id) {
		return `${BASE}/${id}`;
	}
	static create() {
		return `${BASE}`;
	}
};