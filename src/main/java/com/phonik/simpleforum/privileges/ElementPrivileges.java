package com.phonik.simpleforum.privileges;

import com.phonik.simpleforum.users.UserType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_privileges")
public class ElementPrivileges implements Serializable, Comparable<ElementPrivileges> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priv_id")
    private long id;

    @Column(name = "priv_usertype")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "priv_scopeid")
    private long scopeId;

    public ElementPrivileges() {}

    public ElementPrivileges(UserType userType, long scopeId) {
        this.userType = userType;
        this.scopeId = scopeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public long getScopeId() {
        return scopeId;
    }

    public void setScopeId(long scopeId) {
        this.scopeId = scopeId;
    }

    @Override
    public int compareTo(ElementPrivileges o) {
        if (this.scopeId == o.scopeId) return this.userType.compareTo(o.userType);
        else return Long.compare(this.scopeId, o.scopeId);

    }
}
