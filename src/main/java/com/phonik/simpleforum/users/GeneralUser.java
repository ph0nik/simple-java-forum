package com.phonik.simpleforum.users;

import com.phonik.simpleforum.elements.AbstractForumElement;
import com.phonik.simpleforum.privileges.PrivilegesService;
import com.phonik.simpleforum.privileges.UserPrivileges;

import javax.persistence.*;
import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

//@MappedSuperclass
@Entity
@Table(name = "user")
// check if inheritance needed
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GeneralUser implements Serializable, PrivilegesService  {

    @Column(name = "user_type")
    private UserType userType;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int userId;
    @Column(name = "user_name", unique = true)
    private String userName;
    @Column(name = "user_mail", unique = true)
    private String userMail;
    @Column(name = "user_pass")
    private String userPassword;
    @Column(name = "user_perm_ban")
    private boolean permanentlyBanned;
    @Column(name = "user_temp_ban")
    private boolean temporarilyBanned;
    @Column(name = "a_user_created")
    private LocalDateTime userCreated;
    @Column(name = "ban_lift_date")
    private LocalDateTime banLiftDate;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles;

    // TODO problem z EAGER LAZY
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @MapKey(name = "privilegesScope")
    private Map<Integer, UserPrivileges> userPrivilegesMap;

    @Transient
    private String childClassName;

    public GeneralUser() {
        this.userPrivilegesMap = new TreeMap<>();
        this.setUserCreated(LocalDateTime.now());
    }

    public GeneralUser(String name, String password, String email) {
        this();
        this.userName = name;
        this.userPassword = password;
        this.userMail = email;
    }

    public GeneralUser(GeneralUser user) {
        this.userId = user.getUserId();
        this.userType = user.getUserType();
        this.userName = user.getUserName();
        this.userPassword = user.getUserPassword();
        this.userMail = user.getUserMail();
        this.permanentlyBanned = user.isPermanentlyBanned();
        this.temporarilyBanned = user.isTemporarilyBanned();
        this.banLiftDate = user.getBanLiftDate();
        this.userPrivilegesMap = user.getUserPrivilegesMap();
    }

    public String getChildClassName() {
        return childClassName;
    }

    public void setChildClassName(String childClassName) {
        this.childClassName = childClassName;
    }

    public LocalDateTime getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(LocalDateTime userCreated) {
        this.userCreated = userCreated;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Map<Integer, UserPrivileges> getUserPrivilegesMap() {
        return userPrivilegesMap;
    }

    public UserPrivileges getPrivilegesForSpecificElement(int el) {
        return getUserPrivilegesMap().get(el);
    }

    public void removePrivileges(AbstractForumElement element) {
        this.userPrivilegesMap.remove(element.getId());
        manageUserType();
    }

    public void removePrivileges(int elementId) {
        this.userPrivilegesMap.remove(elementId);
        manageUserType();
    }

    protected void manageUserType() {
        if (userType != UserType.ADMIN) {
            userType = (getUserPrivilegesMap().size() > 1) ? UserType.MODERATOR : UserType.USER;
        }
    }

    public void setUserPrivilegesMap(Map<Integer, UserPrivileges> userPrivilegesMap) {
        this.userPrivilegesMap = userPrivilegesMap;
    }

    public void setUserPrivileges(UserPrivileges userPrivileges) {
        userPrivilegesMap.put(userPrivileges.getPrivilegesScope(), userPrivileges);
        manageUserType();
        userPrivileges.setUser(this);
    }

    public boolean isPermanentlyBanned() {
        return permanentlyBanned;
    }

    public void setPermanentlyBanned(boolean permanentlyBanned) {
        this.permanentlyBanned = permanentlyBanned;
    }

    public boolean isTemporarilyBanned() {
        return temporarilyBanned;
    }

    public void setTemporarilyBanned(boolean temporarilyBanned) {
        this.temporarilyBanned = temporarilyBanned;
    }

    public LocalDateTime getBanLiftDate() {
        return banLiftDate;
    }

    public void setBanLiftDate(LocalDateTime banLiftDate) {
        this.banLiftDate = banLiftDate;
    }

    @Override
    public String toString() {
        return "GeneralUser{" +
                "userType=" + userType +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", permanentlyBanned=" + permanentlyBanned +
                ", temporarilyBanned=" + temporarilyBanned +
                ", banLiftDate=" + banLiftDate +
                ", userPrivilegesMap=" + userPrivilegesMap +
                ", userCreated=" + userCreated +
                ", childClassName='" + childClassName + '\'' +
                '}';
    }


}
