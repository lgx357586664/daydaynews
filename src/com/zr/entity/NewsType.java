package com.zr.entity;

/**
 * @author: LiGX
 * @Date: 2019/3/11 0011
 */
public class NewsType {
    private int typeId;
    private String typeName;

    public int getTypeId() {
        return typeId;
    }

    public void setType_id(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setType_name(String typeName) {
        this.typeName = typeName;
    }

    public NewsType() {
    }

    public NewsType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public NewsType(String typeName) {
        this.typeName = typeName;
    }
}
