package pl.training.payments.ports.model;

import lombok.Value;

import java.util.List;

@Value
public class ResultPage<T> {

    List<T> data;
    int pageNumber;
    long totalPages;

}
