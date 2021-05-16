import React, {Component, Fragment} from "react";
import {Header , Footer} from "./components/Layouts"
import Posts from "./components/Posts"

export default class extends Component {
    render() {
        return (
            <Fragment>
                <Header />
                <Posts />
                <Footer />
            </Fragment>
        );
    }
}