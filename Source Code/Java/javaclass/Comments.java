package javaclass;

public class Comments {
    
    public Integer commentid;
    public String commentcontent;
    public Integer postid;
    public String username;
    
    public Comments(){
    }

    public Comments(Integer commentid, String commentcontent, Integer postid, String username) {
        this.commentid = commentid;
        this.commentcontent = commentcontent;
        this.postid = postid;
        this.username = username;
    }

    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
