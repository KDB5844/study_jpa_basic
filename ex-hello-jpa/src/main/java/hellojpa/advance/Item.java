package hellojpa.advance;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private Dtype dtype;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Dtype getDtype() {
        return dtype;
    }

    public void setDtype(Dtype dtype) {
        this.dtype = dtype;
    }
}
