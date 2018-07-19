package com.phonik.simpleforum.elements;

import com.phonik.simpleforum.users.GeneralUser;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "ForumSection")
@Table(name = "forum_section")
public class ForumSection extends AbstractForumElement {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentElement", orphanRemoval = true)
    private List<ForumSection> sectionsList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "path_list", joinColumns = @JoinColumn(name = "target_element_id"), inverseJoinColumns = @JoinColumn(name = "path_element_id"))
    private List<PathElement> elementPath;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "post_list", joinColumns = @JoinColumn(name = "section_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentElement", orphanRemoval = true)
    private List<ForumPost> postsList;

    @ManyToOne
    private ForumSection parentElement;

    @Column(name = "section_title")
    private String title;
    @Column(name = "section_desc")
    private String description;

    public ForumSection() {
        super();
        this.elementPath = new ArrayList<>();
        this.sectionsList = new ArrayList<>();
        this.postsList = new ArrayList<>();
    }

    public ForumSection(String title, String description, GeneralUser author, ForumSection parentElement) {
        this();
        setAuthor(author);
        setElementType(ElementType.SECTION);
        this.title = title;
        this.description = description;
        this.parentElement = parentElement;
        this.elementPath.addAll(parentElement.getElementPath());
        this.elementPath.add(
                new PathElement(
                        parentElement.getId(),
                        parentElement.getTitle(),
                        parentElement.getCreationDate()
                ));
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
    public Set<Long> getParents() {
        Set<Long> parents = new HashSet<>();
        for (PathElement pe : this.elementPath) {
            parents.add(pe.getId());
        }
//        ForumSection parentSection = this.parentElement;
//        while (parentSection.getParentElement().getId() != parentSection.getId()) {
//            parents.add(parentSection.getId());
//            parentSection = parentSection.getParentElement();
//        }
//        parents.add(parentSection.getId());
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

    /**
     * Adds current element to element path
     */
    public void addCurrentToElementPath() {
        PathElement pathElement = new PathElement();
        pathElement.setId(this.getId());
        pathElement.setPathElementName(this.title);
        pathElement.setPathElementCreationDate(this.getCreationDate());
        this.elementPath.add(pathElement);
    }

    public List<PathElement> getElementPath() {
        return elementPath;
    }

    public List<PathElement> getElementPathAsc() {
        return elementPath.stream().sorted(
                Comparator.comparing(
                        PathElement::getPathElementCreationDate)
                        .thenComparing(PathElement::getPathElementName))
                .collect(Collectors.toList());
    }

    public void setElementPath(List<PathElement> elementPath) {
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

    /**
     * Adds parent element and path to this element
     */
    public void setParentElement(ForumSection parentElement) {
        if (this.getId() != parentElement.getId()) {
            this.elementPath.clear();
            this.elementPath.addAll(parentElement.getElementPath());
            this.elementPath.add(
                    new PathElement(
                            parentElement.getId(),
                            parentElement.getTitle(),
                            parentElement.getCreationDate()
                    ));
            parentElement.addSection(this);
        }
        this.parentElement = parentElement;
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
