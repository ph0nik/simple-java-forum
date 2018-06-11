package com.phonik.simpleforum.elements;

import com.phonik.simpleforum.users.GeneralUser;

import javax.persistence.*;
import java.util.*;

@Entity(name = "ForumSection")
@Table(name = "forum_section")
public class ForumSection extends AbstractForumElement {

    @Transient
    private Map<Integer, String> elementPath;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "sections_list", joinColumns = @JoinColumn(name = "section_parent_id"), inverseJoinColumns = @JoinColumn(name = "section_child_id"))
    private List<ForumSection> sectionsList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "post_list", joinColumns = @JoinColumn(name = "section_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<ForumPost> postsList;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private ForumSection parentElement;

    @Column(name = "section_title")
    private String title;
    @Column(name = "section_desc")
    private String description;

    // title, description, author, parent, create time, edit time

    public ForumSection() {
        super();
        elementPath = new TreeMap<>();
        sectionsList = new ArrayList<>();
        postsList = new ArrayList<>();
    }

    public ForumSection(String title, String description, GeneralUser author) {
        this();
        setAuthor(author);
        setId(ForumRoot.FORUM_ROOT_ID);
        this.title = title;
        this.description = description;
        this.parentElement = this;
    }

    public ForumSection(String title, String description, GeneralUser author, ForumSection parentElement) {
        this();
        setAuthor(author);
        this.title = title;
        this.description = description;
        this.parentElement = parentElement;
        parentElement.addSection(this);
    }

    /**
     * Returns collection of integers that represent identifiers of all parent elements related to this element
     * <p>
     * Recursively adds to collection parent identifier as long as parent element doesn't point to itself,
     * which means that root element has been reached. When it does root identifier is also added to collection.
     * </p>
     *
     * @return HashSet of identifiers
     */
    public Set<Integer> getParents() {
        Set<Integer> parents = new HashSet<>();
        ForumSection parentSection = this.parentElement;
        while (parentSection.getParentElement().getId() != parentSection.getId()) {
            parents.add(parentSection.getId());
            parentSection = parentSection.getParentElement();
        }
        parents.add(parentSection.getId());
        return parents;
    }

    /**
     * adds new forum post to list of posts that belong to this element
     *
     * @param fp ForumPost element
     * @return true if post is added, false if post already exists
     */
    public boolean addPost(ForumPost fp) {
        return postsList.add(fp);
    }

    /**
     * deletes post from list
     *
     * @param fp ForumPost element
     * @return true if element was found on list and removed, false if no such element found
     */
    public boolean deletePost(ForumPost fp) {
        return postsList.remove(fp);
    }

    /**
     * adds new ForumSection element to list
     *
     * @param fs ForumSection element
     * @return true if element is properly added, false if element already exsits
     */
    public boolean addSection(ForumSection fs) {
        return sectionsList.add(fs);
    }

    /**
     * deletes section element from list
     *
     * @param fs ForumSection element
     * @return true if element has been deleted, false if no such element was found
     */
    public boolean deleteSection(ForumSection fs) {
        return sectionsList.remove(fs);
    }

    // TODO
    // after creating element db generates id number that is assign to object
    // this id needs to be put inside this collection
    public void addToElementPath(ForumSection forumElement) {
        this.elementPath.put(forumElement.getId(), forumElement.getTitle());
    }

    public Map<Integer, String> getElementPath() {
        return elementPath;
    }

    public void setElementPath(Map<Integer, String> elementPath) {
        this.elementPath = elementPath;
    }

    public List<ForumSection> getSectionsList() {
        return sectionsList;
    }

    public ForumSection getSection(int id) {
        return sectionsList.get(id);
    }

    public void setSectionsList(List<ForumSection> sectionsList) {
        this.sectionsList = sectionsList;
    }

    public List<ForumPost> getPostsList() {
        return postsList;
    }

    public void setPostsList(List<ForumPost> postsList) {
        this.postsList = postsList;
    }

    public ForumSection getParentElement() {
        return parentElement;
    }

    public void setParentElement(ForumSection parentElement) {
        this.parentElement = parentElement;
        parentElement.addSection(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ForumSection{" +
                "sectionsList=" + sectionsList +
                ", id=" + getId() +
                ", postsList=" + postsList +
                ", parentElement=" + parentElement.getId() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", elementPath=" + elementPath +
                ", pinned=" + isPinned() +
                ", author=" + getAuthor() +
                ", editor=" + getEditor() +
                ", creationDate=" + getCreationDate() +
                ", editDate=" + getEditDate() +
                '}';
    }
}
