import React, {Component} from "react";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import {Grid, TextField} from "@material-ui/core";
import PostService from "../../service/PostService";

const buttonStyle = {
    marginTop : '20px',
    marginRight : '5px',
}

class PostDetail extends Component {
    constructor(props) {
        super(props);

        this.state = {
            title: '',
            content: ''
        }

        this.changeTitleHandler = this.changeTitleHandler.bind(this);
        this.changeContentHandler = this.changeContentHandler.bind(this);
    }

    changeTitleHandler = (event) => {
        this.setState({title: event.target.value});
    }
    changeContentHandler = (event) => {
        this.setState({content: event.target.value});
    }

    registerPost = (event) => {
        event.preventDefault()

        if(window.confirm("저장하시겠습니까?")) {
            let post = {
                title: this.state.title,
                content: this.state.content,
                userId: 1
            };
            console.log("post => "+ JSON.stringify(post));
            PostService.registerPost(post).then(res => {
                this.props.history.push('/');
            });
        }
    }

    cancel() {
        this.props.history.push('/');
    }

    render() {
        return (
            <div>
                <div align="right">
                    <Button variant="contained" color="primary" size="large" style={buttonStyle} onClick={this.registerPost}>Save</Button>
                    <Button variant="contained" color="secondary" size="large" style={buttonStyle} onClick={this.cancel.bind(this)}>Cancel</Button>
                </div>
                <Typography align="center" variant="h5" gutterBottom>새글을 작성해주세요.</Typography>
                <form>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                required
                                id="title"
                                name="title"
                                label="title"
                                variant="outlined"
                                fullWidth
                                value={this.state.title}
                                onChange={this.changeTitleHandler}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                id="content"
                                name="content"
                                label="content"
                                variant="outlined"
                                multiline
                                rows={10}
                                fullWidth
                                value={this.state.content}
                                onChange={this.changeContentHandler}
                            />
                        </Grid>
                    </Grid>
                </form>
            </div>
        );
    }
}

export default PostDetail;