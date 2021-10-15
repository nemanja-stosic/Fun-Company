package model;


import javax.persistence.*;

@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private int staffId;
    private String name;
    private int age;
    private String address;
    private int paycheck;

    public Staff() {
    }

    public Staff(int staffId, String name, int age, String address, int paycheck) {
        this.staffId = staffId;
        this.name = name;
        this.age = age;
        this.address = address;
        this.paycheck = paycheck;
    }

    public Staff(String name, int age, String address, int paycheck) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.paycheck = paycheck;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPaycheck() {
        return paycheck;
    }

    public void setPaycheck(int paycheck) {
        this.paycheck = paycheck;
    }

    @Override
    public String toString() {
        return staffId + "\t" + name + "\t" + age + "\t" + address + "\t" + paycheck;
    }
}
