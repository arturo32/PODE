export const validateEmail = (email) => {
    if (email.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)) {
        return true;
    }
    return false;
};

export const validatePassword = (password) => {
    if (password.length > 7) {
        return true;
    }
    return false;
};

export const validatePeriod = (period) => {
    if (period.length === 6) {
        return true;
    }
    return false;
};

export const validateGeneric = (period) => {
    if (period.length > 0) {
        return true;
    }
    return false;
};
