package net.nickac.lithium.frontend;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.val;
import org.apache.commons.lang3.Validate;

import java.nio.charset.StandardCharsets;

/**
 * Created by NickAc for Lithium!
 */
public class LithiumUtils {

	private static int readVarInt(ByteBuf buf, int maxSize) {
		Validate.isTrue(maxSize < 6 && maxSize > 0, "Varint length is between 1 and 5, not %d", maxSize);
		int i = 0;
		int j = 0;
		byte b0;

		do {
			b0 = buf.readByte();
			i |= (b0 & 127) << j++ * 7;

			if (j > maxSize) {
				throw new RuntimeException("VarInt too big");
			}
		}
		while ((b0 & 128) == 128);

		return i;
	}

	public static String readUTF8String(byte[] f) {
		val from = Unpooled.copiedBuffer(f);
		from.readByte();
		int len = readVarInt(from, 2);
		//from.readerIndex(from.readerIndex() + len);
		return from.toString(StandardCharsets.UTF_8);
	}

	private static int varIntByteCount(int toCount) {
		return (toCount & 0xFFFFFF80) == 0 ? 1 : ((toCount & 0xFFFFC000) == 0 ? 2 : ((toCount & 0xFFE00000) == 0 ? 3 : ((toCount & 0xF0000000) == 0 ? 4 : 5)));
	}

	private static void writeVarInt(ByteBuf to, int toWrite, int maxSize) {
		Validate.isTrue(varIntByteCount(toWrite) <= maxSize, "Integer is too big for %d bytes", maxSize);
		while ((toWrite & -128) != 0) {
			to.writeByte(toWrite & 127 | 128);
			toWrite >>>= 7;
		}

		to.writeByte(toWrite);
	}


	public static byte[] writeUTF8String(String string) {
		string = '\n' + string;
		val to = Unpooled.buffer();
		to.writeByte(0);
		byte[] utf8Bytes = string.getBytes(StandardCharsets.UTF_8);
		Validate.isTrue(varIntByteCount(utf8Bytes.length) < 3, "The string is too long for this encoding.");
		writeVarInt(to, utf8Bytes.length, 2);
		to.writeBytes(utf8Bytes);
		return to.array();
	}


}
