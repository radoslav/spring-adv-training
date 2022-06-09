package pl.training.shop.commons.mail;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class MailMessage implements Serializable {

    String sender;
    List<String> recipients;
    String title;
    String text;

}
