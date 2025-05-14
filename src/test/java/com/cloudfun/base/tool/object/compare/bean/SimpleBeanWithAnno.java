package com.cloudfun.base.tool.object.compare.bean;

import com.cloudfun.base.tool.object.compare.anno.Name;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/5/13 10:22
 */
public class SimpleBeanWithAnno {

    @Name(value = "nameId")
    private Long id;

    @Name(value = "nameName")
    private String name;

    @Name(value = "nameAge")
    private Integer age;

    @Name(value = "nameAddress")
    private String address;

    @Name(value = "namePhone")
    private String phone;


    @Name(value = "nameType0")
    private byte type0;

    @Name(value = "nameType1")
    private short type1;

    @Name(value = "nameType2")
    private int type2;

    @Name(value = "nameType3")
    private double type3;

    @Name(value = "nameType4")
    private long type4;

    @Name(value = "nameType5")
    private float type5;

    @Name(value = "nameType6")
    private boolean type6;

    @Name(value = "nameType7")
    private char type7;

    @Name(value = "nameType8")
    private Byte type8;

    @Name(value = "nameType9")
    private Short type9;

    @Name(value = "nameType10")
    private Integer type10;

    @Name(value = "nameType11")
    private Long type11;

    @Name(value = "nameType12")
    private Float type12;

    @Name(value = "nameType13")
    private Double type13;

    @Name(value = "nameType14")
    private Boolean type14;

    @Name(value = "nameType15")
    private Character type15;

    @Name(value = "nameType16")
    private String[] type16;

    @Name(value = "nameType17")
    private byte[] type17;

    @Name(value = "nameType18")
    private short[] type18;

    @Name(value = "nameType19")
    private int[] type19;

    @Name(value = "nameType20")
    private long[] type20;

    @Name(value = "nameType21")
    private float[] type21;

    @Name(value = "nameType22")
    private double[] type22;

    @Name(value = "nameType23")
    private boolean[] type23;

    @Name(value = "nameType24")
    private char[] type24;

    @Name(value = "nameType25")
    private Byte[] type25;

    @Name(value = "nameType26")
    private Short[] type26;

    @Name(value = "nameType27")
    private Integer[] type27;

    @Name(value = "nameType28")
    private Long[] type28;

    @Name(value = "nameType29")
    private Float[] type29;

    @Name(value = "nameType30")
    private Double[] type30;

    @Name(value = "nameType31")
    private Boolean[] type31;

    @Name(value = "nameType32")
    private Character[] type32;

    @Name(value = "nameType33")
    private Map<Integer, String> type33;

    @Name(value = "nameType34")
    private List<Integer> type34;

    @Name(value = "nameType35")
    private Set<String> type35;

    @Name(value = "nameType36")
    private Collection<String> type36;

    @Name(value = "nameType37")
    private Void type37;

    @Name(value = "nameType38")
    private Date type38;

    @Name(value = "nameType39")
    private java.sql.Date  type39;

    @Name(value = "nameType40")
    private LocalDate type40;

    @Name(value = "nameType41")
    private LocalTime type41;

    @Name(value = "nameType42")
    private LocalDateTime type42;

    @Name(value = "nameType43")
    private BigDecimal type43;

    @Name(value = "nameType44")
    private Timestamp type44;


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

    public byte getType0() {
        return type0;
    }

    public void setType0(byte type0) {
        this.type0 = type0;
    }

    public short getType1() {
        return type1;
    }

    public void setType1(short type1) {
        this.type1 = type1;
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

    public long getType4() {
        return type4;
    }

    public void setType4(long type4) {
        this.type4 = type4;
    }

    public float getType5() {
        return type5;
    }

    public void setType5(float type5) {
        this.type5 = type5;
    }

    public boolean isType6() {
        return type6;
    }

    public void setType6(boolean type6) {
        this.type6 = type6;
    }

    public char getType7() {
        return type7;
    }

    public void setType7(char type7) {
        this.type7 = type7;
    }

    public Byte getType8() {
        return type8;
    }

    public void setType8(Byte type8) {
        this.type8 = type8;
    }

    public Short getType9() {
        return type9;
    }

    public void setType9(Short type9) {
        this.type9 = type9;
    }

    public Integer getType10() {
        return type10;
    }

    public void setType10(Integer type10) {
        this.type10 = type10;
    }

    public Long getType11() {
        return type11;
    }

    public void setType11(Long type11) {
        this.type11 = type11;
    }

    public Float getType12() {
        return type12;
    }

    public void setType12(Float type12) {
        this.type12 = type12;
    }

    public Double getType13() {
        return type13;
    }

    public void setType13(Double type13) {
        this.type13 = type13;
    }

    public Boolean getType14() {
        return type14;
    }

    public void setType14(Boolean type14) {
        this.type14 = type14;
    }

    public Character getType15() {
        return type15;
    }

    public void setType15(Character type15) {
        this.type15 = type15;
    }

    public String[] getType16() {
        return type16;
    }

    public void setType16(String[] type16) {
        this.type16 = type16;
    }

    public byte[] getType17() {
        return type17;
    }

    public void setType17(byte[] type17) {
        this.type17 = type17;
    }

    public short[] getType18() {
        return type18;
    }

    public void setType18(short[] type18) {
        this.type18 = type18;
    }

    public int[] getType19() {
        return type19;
    }

    public void setType19(int[] type19) {
        this.type19 = type19;
    }

    public long[] getType20() {
        return type20;
    }

    public void setType20(long[] type20) {
        this.type20 = type20;
    }

    public float[] getType21() {
        return type21;
    }

    public void setType21(float[] type21) {
        this.type21 = type21;
    }

    public double[] getType22() {
        return type22;
    }

    public void setType22(double[] type22) {
        this.type22 = type22;
    }

    public boolean[] getType23() {
        return type23;
    }

    public void setType23(boolean[] type23) {
        this.type23 = type23;
    }

    public char[] getType24() {
        return type24;
    }

    public void setType24(char[] type24) {
        this.type24 = type24;
    }

    public Byte[] getType25() {
        return type25;
    }

    public void setType25(Byte[] type25) {
        this.type25 = type25;
    }

    public Short[] getType26() {
        return type26;
    }

    public void setType26(Short[] type26) {
        this.type26 = type26;
    }

    public Integer[] getType27() {
        return type27;
    }

    public void setType27(Integer[] type27) {
        this.type27 = type27;
    }

    public Long[] getType28() {
        return type28;
    }

    public void setType28(Long[] type28) {
        this.type28 = type28;
    }

    public Float[] getType29() {
        return type29;
    }

    public void setType29(Float[] type29) {
        this.type29 = type29;
    }

    public Double[] getType30() {
        return type30;
    }

    public void setType30(Double[] type30) {
        this.type30 = type30;
    }

    public Boolean[] getType31() {
        return type31;
    }

    public void setType31(Boolean[] type31) {
        this.type31 = type31;
    }

    public Character[] getType32() {
        return type32;
    }

    public void setType32(Character[] type32) {
        this.type32 = type32;
    }

    public Map<Integer, String> getType33() {
        return type33;
    }

    public void setType33(Map<Integer, String> type33) {
        this.type33 = type33;
    }

    public List<Integer> getType34() {
        return type34;
    }

    public void setType34(List<Integer> type34) {
        this.type34 = type34;
    }

    public Set<String> getType35() {
        return type35;
    }

    public void setType35(Set<String> type35) {
        this.type35 = type35;
    }

    public Collection<String> getType36() {
        return type36;
    }

    public void setType36(Collection<String> type36) {
        this.type36 = type36;
    }

    public Void getType37() {
        return type37;
    }

    public void setType37(Void type37) {
        this.type37 = type37;
    }

    public Date getType38() {
        return type38;
    }

    public void setType38(Date type38) {
        this.type38 = type38;
    }

    public java.sql.Date getType39() {
        return type39;
    }

    public void setType39(java.sql.Date type39) {
        this.type39 = type39;
    }

    public LocalDate getType40() {
        return type40;
    }

    public void setType40(LocalDate type40) {
        this.type40 = type40;
    }

    public LocalTime getType41() {
        return type41;
    }

    public void setType41(LocalTime type41) {
        this.type41 = type41;
    }

    public LocalDateTime getType42() {
        return type42;
    }

    public void setType42(LocalDateTime type42) {
        this.type42 = type42;
    }

    public BigDecimal getType43() {
        return type43;
    }

    public void setType43(BigDecimal type43) {
        this.type43 = type43;
    }

    public Timestamp getType44() {
        return type44;
    }

    public void setType44(Timestamp type44) {
        this.type44 = type44;
    }

    public static SimpleBeanWithAnno createTargetBean() {

        SimpleBeanWithAnno simpleBean = new SimpleBeanWithAnno();
        simpleBean.setId(2L);
        simpleBean.setName("cloudgc123");
        simpleBean.setAge(19);
        simpleBean.setAddress("US");
        simpleBean.setPhone("123456789011");
        simpleBean.setType0((byte) 2);
        simpleBean.setType1((short) 2);
        simpleBean.setType2(2);
        simpleBean.setType3(2.0);
        simpleBean.setType4(2L);
        simpleBean.setType5(2.0f);
        simpleBean.setType6(false);
        simpleBean.setType7('b');
        simpleBean.setType8((byte) 2);
        simpleBean.setType9((short) 2);
        simpleBean.setType10(2);
        simpleBean.setType11(2L);
        simpleBean.setType12(2.0f);
        simpleBean.setType13(2.0);
        simpleBean.setType14(false);
        simpleBean.setType15('b');
        simpleBean.setType16(new String[]{"4", "5", "6"});
        simpleBean.setType17(new byte[]{4, 5, 6});
        simpleBean.setType18(new short[]{4, 5, 6});
        simpleBean.setType19(new int[]{4, 5, 6});
        simpleBean.setType20(new long[]{4, 5, 6});
        simpleBean.setType21(new float[]{4.0f, 5.0f, 6.0f});
        simpleBean.setType22(new double[]{4.0, 5.0, 6.0});
        simpleBean.setType23(new boolean[]{false, true, false});
        simpleBean.setType24(new char[]{'e', 'f', 'g'});
        simpleBean.setType25(new Byte[]{4, 5, 6});
        simpleBean.setType26(new Short[]{4, 5, 6});
        simpleBean.setType27(new Integer[]{4, 5, 6});
        simpleBean.setType28(new Long[]{4L, 5L, 6L});
        simpleBean.setType29(new Float[]{4.0f, 5.0f, 6.0f});
        simpleBean.setType30(new Double[]{4.0, 5.0, 6.0});
        simpleBean.setType31(new Boolean[]{false, true, false});
        simpleBean.setType32(new Character[]{'e', 'f', 'g'});
        simpleBean.setType33(new java.util.HashMap<Integer, String>() {{
            put(4, "4");
            put(5, "5");
            put(6, "6");
        }});
        simpleBean.setType34(new java.util.ArrayList<Integer>() {{
            add(4);
            add(5);
            add(6);
        }});
        simpleBean.setType35(new java.util.HashSet<String>() {{
            add("4");
            add("5");
            add("6");
        }});
        simpleBean.setType36(new java.util.ArrayList<String>() {{
            add("4");
            add("5");
            add("6");
        }});

        simpleBean.setType37(null);
        simpleBean.setType38(new Date());
        simpleBean.setType39(new java.sql.Date(new Date().getTime()));
        simpleBean.setType40(LocalDate.now());
        simpleBean.setType41(LocalTime.now());
        simpleBean.setType42(LocalDateTime.now());
        simpleBean.setType43(new BigDecimal("12345678901234567890.12345678901234567890"));
        simpleBean.setType44(new Timestamp(new Date().getTime()));

        return simpleBean;
    }


    public static SimpleBeanWithAnno createOriginBean() {
        SimpleBeanWithAnno simpleBean = new SimpleBeanWithAnno();
        simpleBean.setId(1L);
        simpleBean.setName("cloudgc");
        simpleBean.setAge(18);
        simpleBean.setAddress("china");
        simpleBean.setPhone("12345678901");
        simpleBean.setType0((byte) 1);
        simpleBean.setType1((short) 1);
        simpleBean.setType2(1);
        simpleBean.setType3(1.0);
        simpleBean.setType4(1L);
        simpleBean.setType5(1.0f);
        simpleBean.setType6(true);
        simpleBean.setType7('a');
        simpleBean.setType8((byte) 1);
        simpleBean.setType9((short) 1);
        simpleBean.setType10(1);
        simpleBean.setType11(1L);
        simpleBean.setType12(1.0f);
        simpleBean.setType13(1.0);
        simpleBean.setType14(true);
        simpleBean.setType15('a');
        simpleBean.setType16(new String[]{"1", "2", "3"});
        simpleBean.setType17(new byte[]{1, 2, 3});
        simpleBean.setType18(new short[]{1, 2, 3});
        simpleBean.setType19(new int[]{1, 2, 3});
        simpleBean.setType20(new long[]{1, 2, 3});
        simpleBean.setType21(new float[]{1.0f, 2.0f, 3.0f});
        simpleBean.setType22(new double[]{1.0, 2.0, 3.0});
        simpleBean.setType23(new boolean[]{true, false, true});
        simpleBean.setType24(new char[]{'a', 'b', 'c'});
        simpleBean.setType25(new Byte[]{1, 2, 3});
        simpleBean.setType26(new Short[]{1, 2, 3});
        simpleBean.setType27(new Integer[]{1, 2, 3});
        simpleBean.setType28(new Long[]{1L, 2L, 3L});
        simpleBean.setType29(new Float[]{1.0f, 2.0f, 3.0f});
        simpleBean.setType30(new Double[]{1.0, 2.0, 3.0});
        simpleBean.setType31(new Boolean[]{true, false, true});
        simpleBean.setType32(new Character[]{'a', 'b', 'c'});
        simpleBean.setType33(new java.util.HashMap<Integer, String>() {{
            put(1, "1");
            put(2, "2");
            put(3, "3");
        }});
        simpleBean.setType34(new java.util.ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }});
        simpleBean.setType35(new java.util.HashSet<String>() {{
            add("1");
            add("2");
            add("3");
        }});
        simpleBean.setType36(new java.util.ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
        }});

        simpleBean.setType37(null);
        simpleBean.setType38(new Date(System.currentTimeMillis() +1000));
        simpleBean.setType39(new java.sql.Date(new Date(System.currentTimeMillis() +1000).getTime()));
        simpleBean.setType40(LocalDate.now());
        simpleBean.setType41(LocalTime.now());
        simpleBean.setType42(LocalDateTime.now());
        simpleBean.setType43(new BigDecimal("12345678901234567890.12345678901234567891"));
        simpleBean.setType44(new Timestamp(new Date().getTime()));


        return simpleBean;
    }

}
