import axios from 'axios';

const POST_API_BASE_URL = "http://localhost:8080/api/post";

class PostService {

    getPostList() {
        return axios.get(POST_API_BASE_URL);
    }

    registerPost(post) {
        return axios.post(POST_API_BASE_URL, post);
    }

    deletePost(id) {
        return axios.delete(POST_API_BASE_URL + "/" + id);
    }
}

export default new PostService();