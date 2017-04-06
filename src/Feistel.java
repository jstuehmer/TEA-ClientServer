import java.nio.ByteBuffer;

public class Feistel {

    static {
        System.loadLibrary("feistel");
    }

    private static native void encryptSignal(byte[] signal, long[] key);
    private static native void decryptSignal(byte[] signal, long[] key);

    public static ByteBuffer encryptData(ByteBuffer signal, long[] key) {

        ByteBuffer newBuffer = ByteBuffer.allocate(signal.limit() + Integer.BYTES +
        ((2 * Long.BYTES) - ((signal.limit() + Integer.BYTES) % (2 * Long.BYTES))));

        for (int i = 0; i < newBuffer.limit(); i++) {
            newBuffer.put((byte) 0);
        }
        newBuffer.position(0);
        newBuffer.putInt(signal.limit());
        newBuffer.put(signal.array());
        signal = newBuffer;
        encryptSignal(signal.array(), key);
        return signal;
    }

    public static ByteBuffer decryptData(ByteBuffer signal, long[] key) {
        ByteBuffer newBuffer = ByteBuffer.allocate(signal.limit());
        newBuffer.put(signal.array());
        decryptSignal(newBuffer.array(), key);
        newBuffer.position(0);
        int size = newBuffer.getInt();
        if (size > signal.limit() || size < 0) {
            return ByteBuffer.allocate(0);
        }
        byte[] mess = new byte[size];
        newBuffer.get(mess);
        ByteBuffer message = ByteBuffer.wrap(mess);
        message.position(0);
        return message;
    }
}
