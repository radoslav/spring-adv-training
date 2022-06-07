package pl.training.shop.payments.adapters.persistence.jpa;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name = "PAYMENT_PROPERTY")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
class PropertyEntity {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private String key;
    @NonNull
    private String value;

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        var that = (PropertyEntity) other;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return PropertyEntity.class.hashCode();
    }

}
