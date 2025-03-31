package com.cloudfun.base.tool.object.compare;

import com.cloudfun.base.tool.object.compare.anno.Id;
import com.cloudfun.base.tool.object.compare.anno.Name;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/28 16:51
 */
public class User extends ParentUser{

    // @Id
    private Long id;

    @Name("name")
    private String name;

    private Integer age;

    private String address;

    private String phone;

    private byte type;

    private int type2;

    private double type3;

    private boolean type4;

    private Double type5;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getType2() {
        return type2;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    public double getType3() {
        return type3;
    }

    public void setType3(double type3) {
        this.type3 = type3;
    }

    public boolean isType4() {
        return type4;
    }

    public void setType4(boolean type4) {
        this.type4 = type4;
    }

    public Double getType5() {
        return type5;
    }

    public void setType5(Double type5) {
        this.type5 = type5;
    }
}
