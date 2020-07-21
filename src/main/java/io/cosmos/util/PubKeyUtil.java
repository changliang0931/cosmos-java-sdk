package io.cosmos.util;

import org.bouncycastle.util.encoders.Hex;

import io.cosmos.common.EnvInstance;
import io.cosmos.crypto.Crypto;

public class PubKeyUtil {
	/**
	 * https://docs.tendermint.com/master/spec/blockchain/encoding.html
	 * @param pubKeyType
	 *  PubKeyEd25519	        tendermint/PubKeyEd25519	0x1624DE64	0x20	
	 *	PubKeySr25519	        tendermint/PubKeySr25519	0x0DFB1005	0x20	
	 *	PubKeySecp256k1	        tendermint/PubKeySecp256k1	0xEB5AE987	0x21	
	 *	PrivKeyEd25519	        tendermint/PrivKeyEd25519	0xA3288910	0x40	
	 *	PrivKeySr25519	        tendermint/PrivKeySr25519	0x2F82D78B	0x20	
	 *	PrivKeySecp256k1	    tendermint/PrivKeySecp256k1	0xE1B0F79B	0x20	
	 *	PubKeyMultisigThreshold	tendermint/PubKeyMultisigThreshold	0x22C1F7E2	variable
	 * @return
	 */
	public static String PubKeyEd25519 = "PubKeyEd25519";
	public static String PubKeySr25519 = "PubKeySr25519";
	public static String PubKeySecp256k1 = "PubKeySecp256k1";
	public static String PubKeyMultisigThreshold = "PubKeyMultisigThreshold";
	public static String getPubKeyPrefix(String pubKeyType,String length){  
		String pubKeyPrefix ="eb5ae98721";
        if(PubKeyEd25519.equals(pubKeyType)){
        	pubKeyPrefix ="1624de6420";
        } else if(PubKeySr25519.equals(pubKeyType)) {
        	pubKeyPrefix ="0dfb100520";
        } else if(PubKeyMultisigThreshold.equals(pubKeyType)) {
        	pubKeyPrefix ="e1b0r79b" + length;
        } else {
        	pubKeyPrefix ="eb5ae98721" ;
        }
        return pubKeyPrefix;  
    } 
	/**
	 * 
	 * @param pubKeyType 公钥类型
	 * @param pubKeyByte 公钥字节
	 * @return
	 */
	public static String decodePubKey(String pubKeyType, byte[] pubKeyByte ){  
		String pubKeyResult = null;
		try {
			byte[] pub= byteMerger(Hex.decode(getPubKeyPrefix(pubKeyType,"0")),pubKeyByte);
			byte[] bytes = AddressUtil.encode(0, pub);
			pubKeyResult = io.cosmos.crypto.encode.Bech32.encode(EnvInstance.getEnv().GetAccountPubKeyPrefix(), bytes);
			System.out.println("Pubkey:"+pubKeyResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pubKeyResult;
	}	
	
	public static byte[] byteMerger(byte[] bt1, byte[] bt2){  
        byte[] bt3 = new byte[bt1.length+bt2.length];  
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);  
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);  
        return bt3;  
    } 
	
	public static void main(String[] args) {
		String mnemonic = "false drastic fade economy correct hockey fault bird track sweet spawn wreck around risk appear artefact news zoo high knife tumble sick explain little";
		String prikey = Crypto.generatePrivateKeyFromMnemonic(mnemonic,"");
//		System.out.println("mnemonic");
//		System.out.println(mnemonic);
		System.out.println("prikey");
		System.out.println(prikey);
		System.out.println("pubkey");
		System.out.println(Crypto.generatePubKeyHexFromPriv(prikey));
		String pubkey= PubKeyUtil.decodePubKey(PubKeyUtil.PubKeySecp256k1, Crypto.generatePubKeyByteFromPriv(prikey));
		System.out.println(pubkey);
		System.out.println("address");
		System.out.println(Crypto.generateAccountAddressFromPriv(prikey));
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("address");
		System.out.println("ftsafe16nwscg3lnpgj9v9j4jygle3gp4sw3yrp4h89m0");
		System.out.println("pubkey");
		System.out.println("ftsafepub1addwnpepq2guvkmuqzzjjls748j75j0u9seps6xwcj00lynucqlexfc8j29hvjkm3c7");
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}
}
