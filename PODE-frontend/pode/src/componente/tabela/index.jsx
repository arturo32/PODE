import React, { useState } from 'react';

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableFooter from '@mui/material/TableFooter';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

import TabelaPaginacao from './paginacao';

const Tabela = (props) => {

    const { rows } = props;

    const [pagina, definirPagina] = useState(0);
    const [registrosPorPagina, definirRegistrosPorPagina] = useState(5);

    const emptyRows =
        pagina > 0 ? Math.max(0, (1 + pagina) * registrosPorPagina - rows.length) : 0;

    const handleChangePage = (event, newPage) => {
        definirPagina(newPage);
    };

    const handleChangeregistrosPorPagina = (event) => {
        definirRegistrosPorPagina(parseInt(event.target.value, 10));
        definirPagina(0);
    };

    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 500 }} aria-label="custom pagination table">
                <TableBody>
                    {(registrosPorPagina > 0
                        ? rows.slice(pagina * registrosPorPagina, pagina * registrosPorPagina + registrosPorPagina)
                        : rows
                    ).map((row) => (
                        <TableRow key={row.name}>
                            <TableCell component="th" scope="row">
                                {row.name}
                            </TableCell>
                            <TableCell align="right">
                                {row.calories}
                            </TableCell>
                            <TableCell align="right">
                                {row.fat}
                            </TableCell>
                        </TableRow>
                    ))}
                    {emptyRows > 0 && (
                        <TableRow style={{ height: 53 * emptyRows }}>
                            <TableCell colSpan={6} />
                        </TableRow>
                    )}
                </TableBody>
                <TableFooter>
                    <TableRow>
                        <TablePagination
                            registrosPorPaginaOptions={[5, 10, 25, { label: 'All', value: -1 }]}
                            colSpan={3}
                            count={rows.length}
                            registrosPorPagina={registrosPorPagina}
                            page={pagina}
                            SelectProps={{
                                inputProps: {
                                    'aria-label': 'Registros por página',
                                },
                                native: true,
                            }}
                            onPageChange={handleChangePage}
                            onregistrosPorPaginaChange={handleChangeregistrosPorPagina}
                            ActionsComponent={TabelaPaginacao}
                        />
                    </TableRow>
                </TableFooter>
            </Table>
        </TableContainer>
    );
};

export default Tabela;