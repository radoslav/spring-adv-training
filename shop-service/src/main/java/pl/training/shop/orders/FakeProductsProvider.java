package pl.training.shop.orders;

import pl.training.orders.ports.Product;
import pl.training.orders.ports.ProductsProvider;

import java.util.Optional;

public class FakeProductsProvider implements ProductsProvider {

    @Override
    public Optional<Product> getById(Long id) {
        return Optional.of(new Product(1L, "Fake product", "200 PLN"));
    }

}
