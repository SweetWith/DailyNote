import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import PostList from "../components/Posts/PostList";
import PostDetail from "../components/Posts/PostDetail";

const PostRouterComponent = () => {
    return (
      <div>
          <Router>
            <Switch>
                <Route exact path="/" component = {PostList}></Route>
                <Route path = "/postDetail" component = {PostDetail}></Route>
            </Switch>
          </Router>
      </div>
    );
}

export default PostRouterComponent;