package guestbook;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class GuestEntry extends PanacheEntity {
    public String nickname;
    public String message;
}
