package com.cloudfun.base.tool.object.compare;

import com.cloudfun.base.tool.object.compare.anno.Id;
import com.cloudfun.base.tool.object.compare.anno.Name;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/31 13:37
 */
public class ParentUser {

    @Id
    private Long parentId;


    @Name("parentName")
    private String parentName;


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
