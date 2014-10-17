package com.kryptnostic.crypto.v1.ciphers;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.StringUtils;

import com.kryptnostic.crypto.v1.keys.SecretKeyFactoryType;

public class CryptoService {
    private static final int DEFAULT_PASSWORD_ITERATIONS = 65536;
    private final char[] password;
    private final Cypher cypher;
    private final int iterations;
    private static final int INTEGER_BYTES = Integer.SIZE / Byte.SIZE;
    private final SecretKeyFactoryType secretKeyFactoryType;

    private transient SecretKeyFactory factory;

    public CryptoService(Cypher cypher, char[] password) {
        this(cypher, DEFAULT_PASSWORD_ITERATIONS, password, SecretKeyFactoryType.PBKDF2WithHmacSHA1);
    }

    public CryptoService(Cypher cypher, int iterations, char[] password, SecretKeyFactoryType secretKeyFactoryType) {
        this.cypher = cypher;
        this.password = password;
        this.iterations = iterations;
        this.secretKeyFactoryType = secretKeyFactoryType;
    }

    public BlockCiphertext encrypt(String plaintext) throws InvalidKeySpecException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            InvalidParameterSpecException {
        byte[] salt = Cyphers.generateSalt();
        SecretKeySpec secretKeySpec = getKeyspec(salt);
        Cipher cipher = cypher.getInstance();
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        AlgorithmParameters params = cipher.getParameters();
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] lenBytes = new byte[INTEGER_BYTES];

        ByteBuffer lenBuf = ByteBuffer.wrap(lenBytes);
        lenBuf.putInt(plaintext.length());

        byte[] encryptedLength = cipher.update(lenBytes);
        byte[] encryptedBytes = cipher.doFinal(StringUtils.getBytesUtf8(plaintext));

        return new BlockCiphertext(iv, salt, encryptedBytes, encryptedLength);
    }

    public String decrypt(BlockCiphertext ciphertext) throws InvalidKeyException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException,
            BadPaddingException {
        SecretKeySpec spec = getKeyspec(ciphertext.getSalt());
        Cipher cipher = cypher.getInstance();
        cipher.init(Cipher.DECRYPT_MODE, spec, new IvParameterSpec(ciphertext.getIv()));
        int length = ByteBuffer.wrap(cipher.update(ciphertext.getEncryptedLength())).getInt();
        String plaintext = StringUtils.newStringUtf8(cipher.doFinal(ciphertext.getContents()));
        return plaintext.substring(0, length);
    }

    private SecretKeySpec getKeyspec(byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        factory = secretKeyFactoryType.getInstance();
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, cypher.getKeySize());
        SecretKey key = factory.generateSecret(spec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getEncoded(), cypher.getCipherDescription().getAlgorithm()
                .getValue());
        return secretKeySpec;
    }

    @Override
    protected void finalize() throws Throwable {
        // Zero out the password when GC.
        Arrays.fill(password, (char) 0);
        super.finalize();
    }
    
    private static final int AES_BLOCK_SIZE = 16;

    public BlockCiphertext accessBytes(BlockCiphertext blockCiphertext, final long offset, final long length)
            throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException,
            ShortBufferException, InvalidParameterSpecException {
        
        if (!cypher.getCipherDescription().getAlgorithm().getValue().equals("AES") || !cypher.getCipherDescription().getMode().equals(Mode.CTR)) {
            throw new IllegalArgumentException("Invalid algorithm, only AES/CTR mode supported");
        }

        if (offset < 0) {
            throw new IllegalArgumentException("Invalid offset");
        }

        final IvParameterSpec iv = new IvParameterSpec(blockCiphertext.getIv());

        final int skip = (int) ( offset % AES_BLOCK_SIZE );
        final IvParameterSpec calculatedIvForOffset = calculateIVForOffset(iv, offset - skip);

        Cipher cipher = cypher.getInstance();
        SecretKeySpec spec = getKeyspec(blockCiphertext.getSalt());
        cipher.init(Cipher.ENCRYPT_MODE, spec, calculatedIvForOffset);
//        final byte[] skipBuffer = new byte[skip];
//        
//        cipher.update(skipBuffer, 0, skip, skipBuffer);
//        Arrays.fill(skipBuffer, (byte) 0);
        
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] lenBytes = new byte[INTEGER_BYTES];

        ByteBuffer lenBuf = ByteBuffer.wrap(lenBytes);
        lenBuf.putInt((int) length/8);

        byte[] encryptedLength = cipher.update(lenBytes);
        byte[] contentBuffer = blockCiphertext.getContents();
        
        final byte[] encryptedBytes = new byte[cipher.getOutputSize((int)length/8)];
        int tail = (int) (length % AES_BLOCK_SIZE);
        cipher.update(contentBuffer, (int)(offset - skip)/8, (int)(length + tail)/8, encryptedBytes);

        return new BlockCiphertext(ivBytes, blockCiphertext.getSalt(), encryptedBytes, encryptedLength);
    }

    private static IvParameterSpec calculateIVForOffset(final IvParameterSpec iv, final long blockOffset) {
        final BigInteger ivBI = new BigInteger(1, iv.getIV());
        final BigInteger ivForOffsetBI = ivBI.add(BigInteger.valueOf(blockOffset / AES_BLOCK_SIZE));

        final byte[] ivForOffsetBA = ivForOffsetBI.toByteArray();
        final IvParameterSpec ivForOffset;
        if (ivForOffsetBA.length >= AES_BLOCK_SIZE) {
            ivForOffset = new IvParameterSpec(ivForOffsetBA, ivForOffsetBA.length - AES_BLOCK_SIZE, AES_BLOCK_SIZE);
        } else {
            final byte[] ivForOffsetBASized = new byte[AES_BLOCK_SIZE];
            System.arraycopy(ivForOffsetBA, 0, ivForOffsetBASized, AES_BLOCK_SIZE - ivForOffsetBA.length,
                    ivForOffsetBA.length);
            ivForOffset = new IvParameterSpec(ivForOffsetBASized);
        }

        return ivForOffset;
    }
}
