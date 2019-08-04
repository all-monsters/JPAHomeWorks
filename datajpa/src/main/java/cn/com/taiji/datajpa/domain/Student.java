package cn.com.taiji.datajpa.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: datajpa
 * @description:
 * @author: lgppppppp
 * @create: 2019-08-04 20:31
 **/
@Entity
@Table(name = "student_info")
@NamedQuery(name="student.findall",query = "select a from student a")
public class Student implements Serializable {
    private static final long serialVersionUID = 5119673746393145493L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;// id
    @Column(name = "stu_id")
    private Integer stuId;//学号
    @Column(name = "stu_name")
    private String stuName;//姓名
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", stuId=" + stuId +
                ", stuName='" + stuName + '\'' +
                '}';
    }
}
