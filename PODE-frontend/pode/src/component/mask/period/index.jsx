import React, { forwardRef } from 'react';

import PropTypes from 'prop-types';

import { IMaskInput } from 'react-imask';

const MaskPeriod = forwardRef(function TextMaskCustom(props, ref) {
  const { onChange, ...other } = props;
  return (
    <IMaskInput
      {...other}
      mask="0"
      definitions={{
        '#': /[1-9]/,
      }}
      inputRef={ref}
      onAccept={(value) => onChange({ target: { value } })}
      overwrite={true}
    />
  );
});

MaskPeriod.propTypes = {
  onChange: PropTypes.func.isRequired,
};

export default MaskPeriod;

