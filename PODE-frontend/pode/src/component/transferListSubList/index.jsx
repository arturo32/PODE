import React, {useState} from 'react';
import ListItem from "@mui/material/ListItem";
import ListItemIcon from "@mui/material/ListItemIcon";
import Checkbox from "@mui/material/Checkbox";
import ListItemText from "@mui/material/ListItemText";
import List from "@mui/material/List";
import {Collapse, ListItemButton} from "@mui/material";
import {ExpandLess, ExpandMore} from "@mui/icons-material";


const TransferListSubList = (props) => {
	const { disciplinas, nome, checkedArray, setCheckedArray } = props;

	const [openState, setOpenState] = useState(true);
	const handleClickOpenState = () => {
		setOpenState(!openState);
	};

	/*Se o nome da disciplina passado não está presente no array checkedCursoObrigatorias, ele é adicionado.
	* Se estiver presente, é removido.*/
	const handleToggle = (value) => () => {
		const currentIndex = checkedArray.indexOf(value);
		const newChecked = [...checkedArray];
		if (currentIndex === -1) {
			newChecked.push(value);
		} else {
			newChecked.splice(currentIndex, 1);
		}
		setCheckedArray(newChecked);
	};




	const listDisciplinas = () => (
		<List component="div" disablePadding >
			{disciplinas.map((value) => {
				const labelId = `transfer-list-item-${value}-label`;
				return (
					<ListItem
							key={value.id}
							role="listitem"
							button={true}
							onClick={handleToggle(value)}
							sx={{ pl: 8 }}
					>
						<ListItemIcon>
							<Checkbox
									checked={checkedArray.indexOf(value) !== -1}
									tabIndex={-1}
									disableRipple
									inputProps={{
										'aria-labelledby': labelId,
									}}
							/>
						</ListItemIcon>
						<ListItemText id={labelId} primary={value.nome} />
					</ListItem>
				);
			})}
		</List>
	)

	return (
		<div>
			<ListItemButton onClick={handleClickOpenState} sx={{ pl: 4 }}>
				<ListItemText  primary={nome} />
				{openState ? <ExpandLess /> : <ExpandMore />}
			</ListItemButton>
			<Collapse in={openState} timeout={400} unmountOnExit>
				{listDisciplinas()}
			</Collapse>
		</div>
	);
};

export default TransferListSubList;