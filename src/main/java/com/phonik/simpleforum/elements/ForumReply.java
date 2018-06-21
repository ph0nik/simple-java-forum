package com.phonik.simpleforum.elements;

import com.phonik.simpleforum.users.GeneralUser;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "ForumReply")
@Table(name = "post_reply")
public class ForumReply extends AbstractForumElement {

    @Column(name = "reply_content")
    private String content;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private ForumPost parentPost;

    public ForumReply() {
        super();
    }

    public ForumReply(ForumPost parentPost, String content, GeneralUser author) {
        this();
        setAuthor(author);
        setElementType(ElementType.REPLY);
        this.content = content;
        this.parentPost = parentPost;
        parentPost.addPostReply(this);
    }

    @Override
    public Set<Long> getParents() {
        Set<Long> parents = new HashSet<>();
        parents.addAll(this.parentPost.getParents());
        parents.add(this.parentPost.getId());
        return parents;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ForumPost getParentPost() {
        return parentPost;
    }

    public void setParentPost(ForumPost parentPost) {
        this.parentPost = parentPost;
        parentPost.addPostReply(this);
    }

    // parent topic, author, create date, edit date, content
    // all replies within post should be sorted by creation date

}
