import React from "react";
import {AppBar, IconButton, Toolbar, Typography} from '@material-ui/core'
import MenuIcon from '@material-ui/icons/Menu';

const style = {
    marginRight : '10px'
}

export default props =>
    <AppBar position="static">
        <Toolbar>
            <IconButton aria-label="menu" style={style}>
                <MenuIcon />
            </IconButton>
            <Typography variant="headline" color="inherit">
                Daily Note
            </Typography>
        </Toolbar>
    </AppBar>