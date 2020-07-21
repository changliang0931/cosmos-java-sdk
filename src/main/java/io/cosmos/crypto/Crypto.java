package io.cosmos.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bouncycastle.util.encoders.DecoderException;
import org.bouncycastle.util.encoders.Hex;

import com.google.crypto.tink.subtle.Bech32;

import io.cosmos.common.Constants;
import io.cosmos.common.EnvInstance;
import io.cosmos.crypto.encode.ConvertBits;
import io.cosmos.util.AddressUtil;

public class Crypto {

	private static final byte[] SEED = null;
	private static final Long CREATIONTIMESECONDS = 0L;

	/**
	 * 签名
	 * 
	 * @param msg
	 * @param privateKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] sign(byte[] msg, String privateKey) throws NoSuchAlgorithmException {
		ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));
		return sign(msg, k);
	}

	/**
	 * 签名
	 * 
	 * @param msg
	 * @param k
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] sign(byte[] msg, ECKey k) throws NoSuchAlgorithmException {

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] msgHash = digest.digest(msg);

		ECKey.ECDSASignature signature = k.sign(Sha256Hash.wrap(msgHash));

		byte[] result = new byte[64];
		System.arraycopy(Utils.bigIntegerToBytes(signature.r, 32), 0, result, 0, 32);
		System.arraycopy(Utils.bigIntegerToBytes(signature.s, 32), 0, result, 32, 32);
		return result;
	}

	/**
	 * 随机生成私钥
	 * 
	 * @return
	 */
	public static String generatePrivateKey() {
		SecureRandom csprng = new SecureRandom();
		byte[] randomBytes = new byte[32];
		csprng.nextBytes(randomBytes);
		return Hex.toHexString(randomBytes);
	}

	/**
	 * 私钥派生公钥
	 * 
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String generatePubKeyHexFromPriv(String privateKey) {
		ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));
		return k.getPublicKeyAsHex();
	}

	public static byte[] generatePubKeyByteFromPriv(String privateKey) {
		ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));
		return k.getPubKey();
	}

	/**
	 * 随机生成助记码
	 * 
	 * @return
	 */
	public static String generateMnemonic() {
		byte[] entrophy = new byte[128 / 4];
		new SecureRandom().nextBytes(entrophy);
		try {
			List<String> parts = MnemonicCode.INSTANCE.toMnemonic(entrophy);
			return Utils.SPACE_JOINER.join(parts);
		} catch (MnemonicException.MnemonicLengthException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取私钥
	 * 
	 * @param mnemonic
	 *            助记码
	 * @param passphrase
	 *            密码
	 * @return
	 */
	public static String generatePrivateKeyFromMnemonic(String mnemonic, String passphrase) {
		DeterministicSeed deterministicSeed = null;
		DeterministicKey key = null;
		List<ChildNumber> childNumbers = null;
		try {
			deterministicSeed = new DeterministicSeed(mnemonic, SEED, passphrase, CREATIONTIMESECONDS);
		} catch (UnreadableWalletException e) {
			e.printStackTrace();
			return null;
		}
		key = HDKeyDerivation.createMasterPrivateKey(deterministicSeed.getSeedBytes());

		childNumbers = HDUtils.parsePath(EnvInstance.getEnv().GetHDPath());
		for (ChildNumber cn : childNumbers) {
			key = HDKeyDerivation.deriveChildKey(key, cn);
		}
		return key.getPrivateKeyAsHex();
	}

	public static boolean validateSig(byte[] msg, byte[] pubKey, byte[] sig) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] msgHash = digest.digest(msg);

		byte[] buf = new byte[32];
		System.arraycopy(sig, 0, buf, 0, 32);
		BigInteger r = new BigInteger(1, buf);
		System.arraycopy(sig, 32, buf, 0, 32);
		BigInteger s = new BigInteger(1, buf);
		ECKey.ECDSASignature signature = new ECKey.ECDSASignature(r, s);
		return ECKey.verify(msgHash, signature, pubKey);
	}

	public static boolean validateSig(byte[] msg, String pubKey, String sig) throws NoSuchAlgorithmException {
		return validateSig(msg, Base64.getDecoder().decode(pubKey), Base64.getDecoder().decode(sig));
	}

	public static byte[] generatePubKeyFromPriv(String privateKey) {
		ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));
		return k.getPubKey();
	}

	/**
	 * 私钥获取 account 地址
	 * 
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String generateAccountAddressFromPriv(String privateKey) {
		String pub = generatePubKeyHexFromPriv(privateKey);
		return generateAccountAddressFromPub(pub);
	}

	/**
	 * 私钥获取 consensus 地址
	 * 
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String generateConsensusAddressFromPriv(String privateKey) {
		String pub = generatePubKeyHexFromPriv(privateKey);
		return generateConsensusAddressFromPub(pub);
	}

	/**
	 * 私钥获取 validator 地址
	 * 
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String generateValidatorAddressFromPriv(String privateKey) {
		String pub = generatePubKeyHexFromPriv(privateKey);
		return generateValidatorAddressFromPub(pub);
	}

	public static boolean validPubKey(String pubKey) {
		if (pubKey == null || pubKey.length() != 66) {
			return false;
		}
		try {
			Hex.decode(pubKey);
		} catch (DecoderException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 公钥获取Consensus 地址
	 * 
	 * @param pubKey
	 *            公钥
	 * @return
	 */
	public static String generateConsensusAddressFromPub(String pubKey) {

		try {
			String addr = AddressUtil.createNewAddressSecp256k1(EnvInstance.getEnv().GetConsensusAddrPrefix(),
					Hex.decode(pubKey));
			return addr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 公钥获取Validator 地址
	 * 
	 * @param pubKey
	 *            公钥
	 * @return
	 */
	public static String generateValidatorAddressFromPub(String pubKey) {

		try {
			String addr = AddressUtil.createNewAddressSecp256k1(EnvInstance.getEnv().GetValidatorAddrPrefix(),
					Hex.decode(pubKey));
			return addr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 公钥获取account 地址
	 * 
	 * @param pubKey
	 *            公钥
	 * @return
	 */
	public static String generateAccountAddressFromPub(String pubKey) {

		try {
			String addr = AddressUtil.createNewAddressSecp256k1(EnvInstance.getEnv().GetAccountAddrPrefix(),
					Hex.decode(pubKey));
			return addr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String generatePubkeyBech32FromPubKey(String pubkeyType, String pubKey) {
		String bech32Pubkey = "";
		try {
			byte[] pubkeyByte = ConvertBits.convertBits(Bech32.decode(pubKey).getData(), 0,
					Bech32.decode(pubKey).getData().length, 8, 5, true);

			String bech32PubkeyPrefix = Constants.Bech32PrefixAccAddr;
			if (Constants.Bech32PubKeyTypeAccPub.equals(pubkeyType)) {
				bech32PubkeyPrefix = Constants.Bech32PrefixAccPub;
			} else if (Constants.Bech32PubKeyTypeValPub.equals(pubkeyType)) {
				bech32PubkeyPrefix = Constants.Bech32PrefixValPub;
			} else if (Constants.Bech32PubKeyTypeConsPub.equals(pubkeyType)) {
				bech32PubkeyPrefix = Constants.Bech32PrefixConsPub;
			}

			bech32Pubkey = io.cosmos.crypto.encode.Bech32.encode(bech32PubkeyPrefix, pubkeyByte);
			return bech32Pubkey;

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String generatePubkeyBech32FromPubKey(String pubkeyType, byte[] publickKey) {
		String bech32Pubkey = "";
		try {
			String bech32PubkeyPrefix = Constants.Bech32PrefixAccAddr;
			if (Constants.Bech32PubKeyTypeAccPub.equals(pubkeyType)) {
				bech32PubkeyPrefix = Constants.Bech32PrefixAccPub;
			} else if (Constants.Bech32PubKeyTypeValPub.equals(pubkeyType)) {
				bech32PubkeyPrefix = Constants.Bech32PrefixValPub;
			} else if (Constants.Bech32PubKeyTypeConsPub.equals(pubkeyType)) {
				bech32PubkeyPrefix = Constants.Bech32PrefixConsPub;
			}

			bech32Pubkey = io.cosmos.crypto.encode.Bech32.encode(bech32PubkeyPrefix, publickKey);
			return bech32Pubkey;

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
