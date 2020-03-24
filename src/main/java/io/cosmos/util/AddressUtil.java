package io.cosmos.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.crypto.tink.subtle.Bech32;

import io.cosmos.crypto.encode.ConvertBits;
import io.cosmos.crypto.hash.Ripemd;
import io.cosmos.exception.AddressFormatException;

public class AddressUtil {

	public static String createNewAddressSecp256k1(String mainPrefix, byte[] publickKey) throws Exception {
		String addressResult = null;
		try {
			byte[] pubKeyHash = sha256Hash(publickKey, 0, publickKey.length);
			byte[] address = Ripemd.ripemd160(pubKeyHash);
			byte[] bytes = encode(0, address);
			addressResult = io.cosmos.crypto.encode.Bech32.encode(mainPrefix, bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return addressResult;

	}

	public static byte[] getPubkeyValue(byte[] publickKey) throws Exception {
		try {
			byte[] pubKeyHash = sha256Hash(publickKey, 0, publickKey.length);
			byte[] value = Ripemd.ripemd160(pubKeyHash);
			return value;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static byte[] decodeAddress(String address) {
		byte[] dec = Bech32.decode(address).getData();
		return ConvertBits.convertBits(dec, 0, dec.length, 5, 8, false);
	}

	public static byte[] sha256Hash(byte[] input, int offset, int length) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(input, offset, length);
		return digest.digest();
	}

	public static byte[] encode(int witnessVersion, byte[] witnessProgram) throws AddressFormatException {
		byte[] convertedProgram = ConvertBits.convertBits(witnessProgram, 0, witnessProgram.length, 8, 5, true);
		return convertedProgram;
	}
}
