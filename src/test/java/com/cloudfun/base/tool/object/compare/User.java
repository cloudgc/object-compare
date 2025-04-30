package com.cloudfun.base.tool.object.compare;

import com.cloudfun.base.tool.object.compare.anno.Name;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private String[] type6;

    private int[] type61;

    private Map<Integer, String> type7;

    private List<Integer> type8;

    private Set<String> type9;

    private Collection<String> type10;


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

    public String[] getType6() {
        return type6;
    }

    public void setType6(String[] type6) {
        this.type6 = type6;
    }

    public Map<Integer, String> getType7() {
        return type7;
    }

    public void setType7(Map<Integer, String> type7) {
        this.type7 = type7;
    }

    public List<Integer> getType8() {
        return type8;
    }

    public void setType8(List<Integer> type8) {
        this.type8 = type8;
    }

    public Set<String> getType9() {
        return type9;
    }

    public void setType9(Set<String> type9) {
        this.type9 = type9;
    }

    public Collection<String> getType10() {
        return type10;
    }

    public void setType10(Collection<String> type10) {
        this.type10 = type10;
    }

    public int[] getType61() {
        return type61;
    }

    public void setType61(int[] type61) {
        this.type61 = type61;
    }
}
