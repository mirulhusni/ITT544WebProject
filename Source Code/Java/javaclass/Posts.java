package javaclass;

public class Posts {
    
    private Integer postid;
    private String posttitle;
    private String postcontent;
    private String username;

    public Posts() {
    }
    
    public Posts(Integer postid, String posttitle, String postcontent, String username) {
        this.postid = postid;
        this.posttitle = posttitle;
        this.postcontent = postcontent;
        this.username = username;
    }

    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public String getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }

    public String getPostcontent() {
        return postcontent;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}