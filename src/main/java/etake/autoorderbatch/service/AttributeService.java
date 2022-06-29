package etake.autoorderbatch.service;

import etake.autoorderbatch.dto.NameValuePair;

import java.util.Set;

public interface AttributeService {
    String getAttributeValue(String attribute);

    void init(Set<NameValuePair<String, String>> attributes);
}
