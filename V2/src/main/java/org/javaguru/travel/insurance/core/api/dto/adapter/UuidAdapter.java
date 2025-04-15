package org.javaguru.travel.insurance.core.api.dto.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.UUID;

public class UuidAdapter extends XmlAdapter<String, UUID> {
    @Override
    public UUID unmarshal(String s) throws Exception {
        return UUID.fromString(s);
    }

    @Override
    public String marshal(UUID uuid) throws Exception {
        return uuid.toString();
    }
}
