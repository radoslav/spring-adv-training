package pl.training.shop.payments.adapters.persistence.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.training.shop.commons.jpa.SearchCriteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Set;

@RequiredArgsConstructor
public class PaymentsSpecification implements Specification<PaymentEntity> {

    private final Set<SearchCriteria> searchCriteria;

    @Override
    public Predicate toPredicate(Root<PaymentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(searchCriteria.stream()
                .map(criteria -> map(criteria, root, criteriaBuilder))
                .toArray(Predicate[]::new));
    }

    private Predicate map(SearchCriteria searchCriteria, Root<PaymentEntity> root, CriteriaBuilder builder) {
        var property = searchCriteria.getProperty();
        var value = searchCriteria.getValue();
        return switch (searchCriteria.getOperator()) {
            case EQUAL -> builder.equal(root.get(property), value);
            case NOT_EQUAL -> builder.notEqual(root.get(property), value);
            case MATCH_START -> builder.like(root.get(property), value + "%");
        };
    }

}
