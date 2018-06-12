package com.phonik.simpleforum.dao;

import com.phonik.simpleforum.elements.AbstractForumElement;
import com.phonik.simpleforum.elements.ForumPost;
import com.phonik.simpleforum.elements.ForumReply;
import com.phonik.simpleforum.elements.ForumSection;

public interface ForumElementDao {

    ForumSection getRoot();

    int addForumElement(AbstractForumElement forumElement);

    void updateForumElement(AbstractForumElement forumElement);

    void deleteForumElement(AbstractForumElement forumElement);

    ForumSection getForumSection(int id);

    ForumPost getForumPost(int id);

    ForumReply getForumReply(int id);

}
