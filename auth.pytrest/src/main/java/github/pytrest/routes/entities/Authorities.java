package github.pytrest.routes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Authorities {
    @Id
    private String owner;
    private String authority;

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAuthority() {
        return authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
