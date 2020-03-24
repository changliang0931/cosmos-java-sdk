package io.cosmos.common;

public class ConstantsLocal {
	/**
	 * 查询账户 rest path
	 */
	public static String COSMOS_ACCOUNT_URL_PATH = "/auth/accounts/";
	/**
	 * 广播交易 rest path
	 */
	public static String COSMOS_TRANSACTION_URL_PATH = "/txs";
	/**
	 * 默认gas
	 */
	public static String COSMOS_DEFAULT_GAS = "200000";
	/**
	 * 默认passphrase
	 */
	public static String COSMOS_DEFAULT_PASSPHRASE = "";
	/**
	 * return after tx commit
	 */
	public static String COSMOS_BOARDCAST_TX_MODE_BLOCK = "block";
	/**
	 * return after CheckTx
	 */
	public static String COSMOS_BOARDCAST_TX_MODE_SYNC = "sync";
	/**
	 * return right away
	 */
	public static String COSMOS_BOARDCAST_TX_MODE_ASYNC = "async";
	/**
	 * 交易类型 cosmos-sdk/StdTx
	 */
	public static String COSMOS_TX_TYPE_STDTX = "cosmos-sdk/StdTx";
	/**
	 * rest 服务url
	 */
	public static String COSMOS_REST_SERVER_RRL = "http://192.168.17.84:3002/swagger";
	/**
	 * pubkey type
	 */
	public static String COSMOS_PUBKEY_TYPE = "tendermint/PubKeySecp256k1";
	/**
	 * chain id
	 */
	public static String COSMOS_CHAIN_ID = "changliangNb";

	/**
	 * 默认代币符号 denom
	 */
	public static String COSMOS_DEFAULT_DENOM = "denom";

	// BIP44Prefix is the parts of the BIP44 HD path that are fixed by
	// what we used during the fundraiser.
	public static String FullFundraiserPath = "M/44H/118H/0H/0/0";

	public static String Bech32MainPrefix = "cosmos";
	public static String Bech32PubKeyTypeAccPub = "accpub";
	public static String Bech32PubKeyTypeValPub = "valpub";
	public static String Bech32PubKeyTypeConsPub = "conspub";
	// PrefixAccount is the prefix for account keys
	public static String PrefixAccount = "acc";
	// PrefixValidator is the prefix for validator keys
	public static String PrefixValidator = "val";
	// PrefixConsensus is the prefix for consensus keys
	public static String PrefixConsensus = "cons";
	// PrefixPublic is the prefix for public keys
	public static String PrefixPublic = "pub";
	// PrefixOperator is the prefix for operator keys
	public static String PrefixOperator = "oper";
	// PrefixAddress is the prefix for addresses
	public static String PrefixAddress = "addr";
	// Bech32PrefixAccAddr defines the Bech32 prefix of an account's address
	public static String Bech32PrefixAccAddr = Bech32MainPrefix;
	// Bech32PrefixAccPub defines the Bech32 prefix of an account's public key
	public static String Bech32PrefixAccPub = Bech32MainPrefix + PrefixPublic;
	// Bech32PrefixValAddr defines the Bech32 prefix of a validator's operator
	// address
	public static String Bech32PrefixValAddr = Bech32MainPrefix + PrefixValidator + PrefixOperator;
	// Bech32PrefixValPub defines the Bech32 prefix of a validator's operator
	// public key
	public static String Bech32PrefixValPub = Bech32MainPrefix + PrefixValidator + PrefixOperator + PrefixPublic;
	// Bech32PrefixConsAddr defines the Bech32 prefix of a consensus node
	// address
	public static String Bech32PrefixConsAddr = Bech32MainPrefix + PrefixValidator + PrefixConsensus;
	// Bech32PrefixConsPub defines the Bech32 prefix of a consensus node public
	// key
	public static String Bech32PrefixConsPub = Bech32MainPrefix + PrefixValidator + PrefixConsensus + PrefixPublic;

}
