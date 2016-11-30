package hibernate.test.entity.annotation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="department")
public class Department implements java.io.Serializable {
    private Integer deptNo;
    private String deptName;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_dep")
    public Integer getDeptNo() {
        return this.deptNo;
    }

    public void setDeptNo(Integer deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}
