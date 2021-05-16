import React, {Component} from "react";
import {Grid} from "@material-ui/core";
import PostRouterComponent from "../../route/PostRouterComponent"

class index extends Component {
    render() {
        return(
            <Grid container sm>
                <Grid item sm>
                    <PostRouterComponent/>
                </Grid>
            </Grid>
        );
    }
}

export default index;

