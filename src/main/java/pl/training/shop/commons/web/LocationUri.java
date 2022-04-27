package pl.training.shop.commons.web;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class LocationUri {

    private static final String ID_SEGMENT = "/{id}";

    private LocationUri() {}

    public static URI withId(Object id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ID_SEGMENT)
                .buildAndExpand(id)
                .toUri();
    }

}
