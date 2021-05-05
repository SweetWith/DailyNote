import React, {Component} from "react";
import Post from "./Post"
import PostService from "../../service/PostService";
import Button from '@material-ui/core/Button';

const style = {
    marginTop : '20px',
    marginRight : '10px',
}

const buttonStyle = {
    marginTop : '20px',
    marginRight : '5px',
}

class PostList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            Posts:[]
        }
        this.registerPost = this.registerPost.bind(this);
    };

    componentDidMount() {
        PostService.getPostList().then((res) => {
            this.setState({ Posts: res.data});
        });
    }

    getPostList() {
        PostService.getPostList().then((res) => {
            this.setState({ Posts: res.data});
        });
    }

    registerPost() {
        this.props.history.push('/postDetail');
    }

    render() {
        return (
            <div>
                <div align="right" style={style}>
                    <Button variant="outlined" color="primary" size="large" style={buttonStyle} onClick={this.getPostList}>Search</Button>
                    <Button variant="contained" color="primary" size="large" style={buttonStyle} onClick={this.registerPost}>Register</Button>
                </div>
                <ul>
                    {
                        this.state.Posts && this.state.Posts.map(item => {
                            return (
                                <Post key={item.id} id={item.id} title={item.title} content={item.content} username={item.userName} createdDate={item.createdDate}/>
                            );
                        })
                    }
                </ul>
            </div>
        );
    }
}

export default PostList;