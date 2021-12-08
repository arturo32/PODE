import React, { useState } from 'react';

import { ThemeProvider } from '@mui/material/styles';

import Box from '@mui/material/Box';
import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';

import FavoriteIcon from '@mui/icons-material/Favorite';
import SentimentVerySatisfiedIcon from '@mui/icons-material/SentimentVerySatisfied';

import RecommendationPes from './pes';
import RecommendationPlan from './plan';

import { generic } from '../../component/theme';

import { css } from './styles';

const Recommendations = () => {

    const [option, setOption] = useState(0);

    const render = () => {
        if (option === 1) {
            return (<RecommendationPes />);
        } else {
            return (<RecommendationPlan />);
        }
    };

    return (
        <ThemeProvider theme={generic}>
            <Box>
                <BottomNavigation
                    showLabels={true}
                    value={option}
                    onChange={(_, newValue) => {
                        setOption(newValue);
                    }}
                >
                    <BottomNavigationAction label="Plano de curso" icon={<FavoriteIcon />} sx={css.option} />
                    <BottomNavigationAction label="Proximidade de conclusão de um PES" icon={<SentimentVerySatisfiedIcon />} />
                </BottomNavigation>
                {render()}
            </Box>
        </ThemeProvider>
    );

};

export default Recommendations;