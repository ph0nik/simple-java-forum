package com.phonik.simpleforum.privileges;

import com.phonik.simpleforum.elements.AbstractForumElement;

public interface PrivilegesService {

    // return set of privileges for given element identifier
    UserPrivileges getPrivilegesForSpecificElement(int el);

    // removes user privileges for given forum element id
    void removePrivileges(AbstractForumElement forumElement);
}
