import React, {Component} from "react";
import {Card, CardHeader, CardContent, CardActions} from "@material-ui/core";

import FavoriteIcon from '@material-ui/icons/Favorite';
import ShareIcon from '@material-ui/icons/Share';

import Avatar from '@material-ui/core/Avatar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import DeleteIcon from "@material-ui/icons/Delete";
import PostService from "../../service/PostService";

const style = {
    marginTop : '20px',
    widths : '400px'
}

const avatarStyle = {
    backgroundColor : '#3f51b5'
}

class Post extends Component {

    constructor(props) {
        super(props);

        this.state = {
            id : this.props.id
        }
    }
    deletePost = (event) => {
        if(window.confirm("삭제하시겠습니까?")) {
            PostService.deletePost(this.state.id).then( res => {
                console.log("delete result => "+ JSON.stringify(res));
                if (res.status == 200) {
                    alert("글 삭제를 성공했습니다.");
                } else {
                    alert("글 삭제가 실패했습니다.");
                }
            });
        }
    }

    render() {
        return (
            <Card style={style}>
                <CardHeader
                    avatar={
                        <Avatar aria-label="recipe" style={avatarStyle}>
                            T
                        </Avatar>
                    }
                    action={
                        <IconButton aria-label="settings">
                            <MoreVertIcon />
                        </IconButton>
                    }
                    title={this.props.title}
                    subheader={this.props.createdDate}
                />
                <CardContent>
                    <Typography variant="body1" color="textSecondary" component="p">
                        {this.props.content}
                    </Typography>
                </CardContent>
                <CardActions disableSpacing>
                    <IconButton aria-label="add to favorites">
                        <FavoriteIcon color="secondary"/>
                    </IconButton>
                    <IconButton aria-label="share">
                        <ShareIcon />
                    </IconButton>
                    <IconButton aria-label="share">
                        <DeleteIcon onClick={this.deletePost} />
                    </IconButton>
                </CardActions>
            </Card>
        );
    }
}

export default Post;
