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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private ForumSection parentSection;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "reply_list", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "reply_id"))
    private List<ForumReply> postReplysList;

    // author, title, content, parent category, creation date, edit date
    // who can edit post -> user / author, admin, moderator of post scope
    // who can delete post -> admin / moderator of post scope
    // who can add reply -> any user

    public ForumPost() {
        super();
        postReplysList = new ArrayList<>();
    }

    public ForumPost(ForumSection parentSection, String title, String content, GeneralUser author) {
        this();
        setAuthor(author);
        this.title = title;
        this.content = content;
        this.parentSection = parentSection;
        parentSection.addPost(this);
    }

    public Set<Integer> getParents() {
        ForumSection parent = this.parentSection;
        Set<Integer> parentSet = new TreeSet<>();
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

    public ForumSection getParentSection() {
        return parentSection;
    }

    public void setParentSection(ForumSection parentSection) {
        this.parentSection = parentSection;
        parentSection.addPost(this);
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
