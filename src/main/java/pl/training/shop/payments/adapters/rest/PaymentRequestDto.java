package pl.training.shop.payments.adapters.rest;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class PaymentRequestDto {

    @Min(1)
    private Long requestId;
    @Pattern(regexp = "\\d+ [A-Z]+")
    @NotNull
    private String value;

}
