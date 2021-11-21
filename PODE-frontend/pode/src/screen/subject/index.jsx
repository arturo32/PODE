import React, { useState } from 'react';

import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

import { createSubject } from './service';

const Subject = (props) => {

    const [code, setCode] = useState('');
    const [name, setName] = useState('');
    const [ch, setCh] = useState('');

    const submit = () => {
        createSubject({ code, name, ch });
    };

    return (
        <Container>
            <form name="subject" style={{ width: '100%', marginTop: '10vh' }}>
                <Grid container={true} rowSpacing={2} columnSpacing={2}>
                    <Grid item={true} lg={12} md={12} sm={12} xl={12} xs={12}>
                        <Typography variant="h3" component="div" gutterBottom={true}>
                            Disciplina
                        </Typography>
                    </Grid>
                    <Grid item={true} lg={3} md={6} sm={12} xl={12} xs={12}>
                        <TextField
                            type="text"
                            value={code}
                            onChange={event => setCode(event.target.value)}
                            label="Código"
                            variant="outlined"
                            InputLabelProps={{ shrink: true }}
                            fullWidth={true}
                        />
                    </Grid>
                    <Grid item={true} lg={3} md={6} sm={12} xl={12} xs={12}>
                        <TextField
                            type="text"
                            value={name}
                            onChange={event => setName(event.target.value)}
                            label="Nome"
                            variant="outlined"
                            InputLabelProps={{ shrink: true }}
                            fullWidth={true}
                        />
                    </Grid>
                    <Grid item={true} lg={3} md={6} sm={12} xl={12} xs={12}>
                        <TextField
                            type="number"
                            value={ch}
                            onChange={event => setCh(event.target.value)}
                            label="Carga Horária"
                            variant="outlined"
                            InputLabelProps={{ shrink: true }}
                            fullWidth={true}
                        />
                    </Grid>
                    <Grid item={true} lg={3} md={6} sm={12} xl={12} xs={12}>
                        <Button
                            variant="outlined"
                            disabled={!code || !name || !ch}
                            onClick={() => submit()}
                        >
                            ENVIAR
                        </Button>
                    </Grid>
                </Grid>
            </form>
        </Container>
    );

};

export default Subject;