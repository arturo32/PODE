import React, {useState} from 'react';
import {Collapse, ListItemButton} from "@mui/material";
import ListItemText from "@mui/material/ListItemText";
import {ExpandLess, ExpandMore} from "@mui/icons-material";
import TransferListSubList from "../transferListSubList";


const TransferListSubMenu = (props) => {
	const { disciplinas , nome } = props;

	const [openNaoCursadas, setOpenNaoCursadas] = useState(true);
	const handleClickNaoCursadas = () => {
		setOpenNaoCursadas(!openNaoCursadas);
	};


	return (
			<div>
				<ListItemButton onClick={handleClickNaoCursadas}>
					<ListItemText  primary={nome} />
					{openNaoCursadas ? <ExpandLess /> : <ExpandMore />}
				</ListItemButton>
				<Collapse in={openNaoCursadas} timeout={400} unmountOnExit>
					<TransferListSubList disciplinas={disciplinas.obrigatorias.disciplinas} nome={'Obrigatórias'}
										 checkedArray={disciplinas.obrigatorias.checked} setCheckedArray={disciplinas.obrigatorias.setChecked}
					/>
					<TransferListSubList disciplinas={disciplinas.optativas.disciplinas} nome={'Optativas'}
										 checkedArray={disciplinas.optativas.checked} setCheckedArray={disciplinas.optativas.setChecked}
					/>
				</Collapse>
			</div>
	)
}

export default TransferListSubMenu;