export const get = (key) => {
    return JSON.parse(sessionStorage.getItem(key));
};

export const clear = () => {
    sessionStorage.clear();
};