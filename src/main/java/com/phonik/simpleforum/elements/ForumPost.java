package com.phonik.simpleforum.elements;

import com.phonik.simpleforum.users.GeneralUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity(name = "ForumPost")
@Table(name = "forum_post")
public class ForumPost extends AbstractForumElement implements Serializable, Comparable<ForumPost> {

    @Column(name = "post_title")
    private String title;
    @Column(name = "post_content")
    private String content;

    @ManyToOne
    private ForumSection parentElement;

    // change to fit parent-child relation similar to forum section
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentPost", orphanRemoval = true)
    private List<ForumReply> postReplysList;

    // author, title, content, parent category, creation date, edit date
    // who can edit post -> user / author, admin, moderator of post scope
    // who can delete post -> admin / moderator of post scope
    // who can add reply -> any user

    public ForumPost() {
        super();
        postReplysList = new ArrayList<>();
    }

    public ForumPost(ForumSection parentElement, String title, String content, GeneralUser author) {
        this();
        setAuthor(author);
        setElementType(ElementType.TOPIC);
        this.title = title;
        this.content = content;
        this.parentElement = parentElement;
        parentElement.addPost(this);
    }

    public Set<Long> getParents() {
        ForumSection parent = this.parentElement;
        Set<Long> parentSet = new TreeSet<>();
        parentSet.addAll(parent.getParents());
        parentSet.add(parent.getId());
        return parentSet;
    }

    public void addPostReply(ForumReply fr) {
        postReplysList.add(fr);
    }

    /**
     * deletes child element of ForumReply object from list
     *
     * @param   fr  ForumReply object
     * @return      true if object was deleted false if not found
     * */
    public boolean deletePostReply(ForumReply fr) {
        return postReplysList.remove(fr);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ForumSection getParentElement() {
        return parentElement;
    }

    public void setParentElement(ForumSection parentElement) {
        this.parentElement = parentElement;
        parentElement.addPost(this);
    }

    public List<ForumReply> getPostReplysList() {
        return postReplysList;
    }

    public void setPostReplysList(List<ForumReply> postReplysList) {
        this.postReplysList = postReplysList;
    }

    //TODO
    @Override
    public int compareTo(ForumPost o) {
        return 0;
    }
}
