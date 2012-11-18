package com.thisismartin.directcollections.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.UUID;

/**
 * https://github.com/Cascading/meat-locker/blob/master/src/jvm/com/twitter/meatlocker/kryo/UUIDSerializer.java
 * 
 * @author martin.spasovski
 *
 */
public class UUIDSerializer extends Serializer<UUID> {

    @Override
    public void write(Kryo kryo, Output output, UUID uuid) {
        output.writeLong(uuid.getMostSignificantBits(), false);
        output.writeLong(uuid.getLeastSignificantBits(), false);
    }

    @Override
    public UUID read(Kryo kryo, Input input, Class<UUID> uuidClass) {
        return new UUID(input.readLong(false), input.readLong(false));
    }
}
