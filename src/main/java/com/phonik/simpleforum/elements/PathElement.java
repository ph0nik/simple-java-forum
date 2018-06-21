package com.phonik.simpleforum.elements;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

@Entity(name = "PathElement")
@Table(name = "path_element")
public class PathElement implements Serializable, Comparator<PathElement> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "path_id", unique = true, nullable = false)
    private long id;

    @Column(name = "path_el_id")
    private long pathElementId;

    @Column(name = "path_el_name")
    private String pathElementName;

    @Column(name = "path_el_date")
    private LocalDateTime pathElementCreationDate;

    public PathElement(){}

    public PathElement(long id, String name, LocalDateTime creationDate) {
        this.pathElementId = id;
        this.pathElementName = name;
        this.pathElementCreationDate = creationDate;
    }

    public long getPathElementId() {
        return pathElementId;
    }

    public void setPathElementId(long pathElementId) {
        this.pathElementId = pathElementId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPathElementName() {
        return pathElementName;
    }

    public void setPathElementName(String pathElementName) {
        this.pathElementName = pathElementName;
    }

    public LocalDateTime getPathElementCreationDate() {
        return pathElementCreationDate;
    }

    public void setPathElementCreationDate(LocalDateTime pathElementCreationDate) {
        this.pathElementCreationDate = pathElementCreationDate;
    }

    @Override
    public String toString() {
        return "PathElement{" +
                "id=" + id +
                ", pathElementName='" + pathElementName + '\'' +
                ", pathElementCreationDate=" + pathElementCreationDate +
                '}';
    }

    @Override
    public int compare(PathElement o1, PathElement o2) {
        return (o1.pathElementCreationDate.equals(o2.pathElementCreationDate))
                ? o1.pathElementName.compareTo(o2.pathElementName)
                : o1.pathElementCreationDate.compareTo(o2.pathElementCreationDate);
    }
}
