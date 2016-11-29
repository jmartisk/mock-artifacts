import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * @author Jan Martiska
 */
@Entity
@Cacheable
@NamedQuery(
        name= "allDumbEntities",
        query = "from DumbEntity d"
)
public class DumbEntity {

    @Id
    private String name;

    public DumbEntity() {
    }

    public DumbEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
