package org.javaguru.travel.insurance.core.util;

import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Component
public class ConvertBytesToUuidUtil {
    public List<UUID> convertBytesToUUID(List<byte[]> bytes) {
        return bytes.stream().map(b -> {
            ByteBuffer bb = ByteBuffer.wrap(b);
            return new UUID(bb.getLong(), bb.getLong());
        }).toList();
    }
}
