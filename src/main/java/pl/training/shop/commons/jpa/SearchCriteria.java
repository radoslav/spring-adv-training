package pl.training.shop.commons.jpa;

import lombok.Value;

@Value
public class SearchCriteria {

    String property;
    Object value;
    Operator operator;

}
