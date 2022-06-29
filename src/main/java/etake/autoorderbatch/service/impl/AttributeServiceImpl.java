package etake.autoorderbatch.service.impl;

import etake.autoorderbatch.dto.NameValuePair;
import etake.autoorderbatch.service.AttributeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class AttributeServiceImpl implements AttributeService {
    private Set<NameValuePair<String, String>> attributes;

    @Override
    public String getAttributeValue(String attribute) {
        return attributes.stream()
                .filter(a -> Objects.equals(a.getName(), attribute))
                .findFirst()
                .map(NameValuePair::getValue)
                .map(value -> value.isBlank() ? Strings.EMPTY : value)
                .orElseThrow(() -> new RuntimeException("Cannot get attribute " + attribute));
    }

    @Override
    public void init(Set<NameValuePair<String, String>> attributes) {
        this.attributes = attributes;
    }
}
