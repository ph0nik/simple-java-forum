package com.phonik.simpleforum.privileges.service;

import com.phonik.simpleforum.elements.ForumPost;
import com.phonik.simpleforum.elements.ForumReply;
import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.elements.AbstractForumElement;
import com.phonik.simpleforum.privileges.UserPrivileges;
import com.phonik.simpleforum.users.GeneralUser;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Component
public class ValidateUserImpl implements ValidateUser {

    private Function<UserPrivileges, Boolean> newSection = UserPrivileges::isCreateNewSection;
    private Function<UserPrivileges, Boolean> editSection = UserPrivileges::isEditSection;
    private Function<UserPrivileges, Boolean> deleteSection = UserPrivileges::isDeleteSection;
    private Function<UserPrivileges, Boolean> newPost = UserPrivileges::isCreateNewPost;
    private Function<UserPrivileges, Boolean> editOwnPost = UserPrivileges::isEditOwnPost;
    private Function<UserPrivileges, Boolean> editAnyPost = UserPrivileges::isEditAnyPost;
    private Function<UserPrivileges, Boolean> deletePost = UserPrivileges::isDeletePost;
    private Function<UserPrivileges, Boolean> newReply = UserPrivileges::isCreateNewReply;
    private Function<UserPrivileges, Boolean> editOwnReply = UserPrivileges::isEditOwnReply;
    private Function<UserPrivileges, Boolean> editAnyReply = UserPrivileges::isEditAnyReply;
    private Function<UserPrivileges, Boolean> deleteReply = UserPrivileges::isDeleteReply;

    private Set<Integer> sectionPath;


    @Override
    public boolean canCreateNewSection(GeneralUser user, ForumSection forumSection) {
        return validationWithFunction(user, forumSection, newSection);
    }

    @Override
    public boolean canEditSection(GeneralUser user, ForumSection forumSection) {
        return validationWithFunction(user, forumSection, editSection);
    }

    @Override
    public boolean canDeleteSection(GeneralUser user, ForumSection forumSection) {
        return validationWithFunction(user, forumSection, deleteSection);
    }

    @Override
    public boolean canCreateNewPost(GeneralUser user, ForumSection forumSection) {
        return validationWithFunction(user, forumSection, newPost);
    }

    @Override
    public boolean canDeletePost(GeneralUser user, ForumPost forumPost) {
        return validationWithFunction(user, forumPost, deletePost);
    }

    @Override
    public boolean canEditPost(GeneralUser user, ForumPost forumPost) {
        return user.getUserId() == forumPost.getAuthor().getUserId() || validationWithFunction(user, forumPost, editAnyPost);
    }

    @Override
    public boolean canCreateNewReply(GeneralUser user, ForumPost forumPost) {
        return validationWithFunction(user, forumPost, newReply);
    }

    @Override
    public boolean canDeleteReply(GeneralUser user, ForumReply forumReply) {
        return validationWithFunction(user, forumReply, deleteReply);
    }

    @Override
    public boolean canEditReply(GeneralUser user, ForumReply forumReply) {
        return user.getUserId() == forumReply.getAuthor().getUserId() || validationWithFunction(user, forumReply, editAnyReply);
    }

    /**
     * Determines if given user is allowed to perform given action on given forum element
     *
     * @param   user        user that performs action
     * @param   forumElement  forum element
     * @param   function    functional interface that with given UserPrivileges returns boolean value
     *
     * @return  true if user is allowed to perform action, false otherwise
     * */
    protected boolean validationWithFunction(GeneralUser user,
                                           AbstractForumElement forumElement,
                                           Function<UserPrivileges, Boolean> function) {
        sectionPath = new HashSet<>(forumElement.getParents());
        sectionPath.add(forumElement.getId());
        return sectionPath.stream()
                .filter(id -> user.getUserPrivilegesMap().containsKey(id))
                .anyMatch(id -> function.apply(user.getPrivilegesForSpecificElement(id)));
    }

}
