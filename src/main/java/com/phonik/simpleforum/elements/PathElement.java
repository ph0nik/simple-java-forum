package com.phonik.simpleforum.elements;

import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

@Entity(name = "PathElement")
@Table(name = "path_element")
public class PathElement implements Serializable, Comparator<PathElement> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "path_el_id", unique = true, nullable = false)
    private int id;

    @Column(name = "path_el_name")
    private String name;

    @Column(name = "path_el_date")
    private LocalDateTime creationDate;

    public PathElement(){};

    public PathElement(int id, String name, LocalDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "PathElement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public int compare(PathElement o1, PathElement o2) {
        return (o1.creationDate.equals(o2.creationDate))
                ? o1.name.compareTo(o2.name)
                : o1.creationDate.compareTo(o2.creationDate);
    }
}
