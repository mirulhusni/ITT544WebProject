package javaclass;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Likes {
    public Integer likeid;
    public Integer postid;
    public String username;
    
    public Likes(){
    }

    public Likes(Integer likeid, Integer postid, String username) {
        this.likeid = likeid;
        this.postid = postid;
        this.username = username;
    }

    public Integer getLikeid() {
        return likeid;
    }

    public void setLikeid(Integer likeid) {
        this.likeid = likeid;
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
