package pl.training.shop.payments.adapters.persistence.jpa;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@NamedEntityGraph(name = PaymentEntity.WITH_PROPERTIES, attributeNodes = @NamedAttributeNode("properties"))
@NamedQuery(name = PaymentEntity.GET_BY_STATUS, query = "select p from Payment p where p.status = :status")
@NamedQuery(name = PaymentEntity.COUNT_BY_STATUS, query = "select count(p) from Payment p where p.status = :status")
@NamedQuery(name = PaymentEntity.GET_WITH_VALUE_GREATER_THAN, query = "select p from Payment p where p.value > :value")
@Entity(name = "Payment")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString(of = "id")
public class PaymentEntity {

    public static final String GET_BY_STATUS = "paymentsByStatus";
    public static final String GET_WITH_VALUE_GREATER_THAN = "paymentsByValueGreaterThan";
    public static final String COUNT_BY_STATUS = "paymentsCountByStatus";
    public static final String WITH_PROPERTIES = "paymentsWithProperties";

    @Id
    private String id;
    private BigDecimal value;
    private String currency;
    private Instant timestamp;
    private String status;
    @JoinColumn
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PropertyEntity> properties;

}
