package io.cosmos.common;

public class EnvBase {

	public String GetMainPrefix() {
		return Constants.Bech32MainPrefix;
	};

	public String GetDenom() {
		return Constants.COSMOS_DEFAULT_DENOM;
	}

	public String GetChainid() {
		return Constants.COSMOS_CHAIN_ID;
	}

	public String GetRestServerUrl() {
		return Constants.COSMOS_REST_SERVER_RRL;
	}

	public String GetHDPath() {
		return Constants.FullFundraiserPath;
	}

	public String GetValidatorAddrPrefix() {
		return Constants.Bech32PrefixValAddr;
	}

	public String GetAccountAddrPrefix() {
		return Constants.Bech32PrefixAccAddr;
	}
	
	public String GetAccountPubKeyPrefix() {
		return Constants.Bech32PrefixAccPub;
	}

	public String GetConsensusAddrPrefix() {
		return Constants.Bech32PrefixConsAddr;
	}

	public String GetRestPathPrefix() {
		return "";
	};

	public boolean HasFee() {
		return true;
	};

	public String GetPassphrase() {
		return Constants.COSMOS_DEFAULT_PASSPHRASE;
	};

	public String GetTxUrlPath() {
		return Constants.COSMOS_TRANSACTION_URL_PATH;
	};

	public String GetTxModeBlock() {
		return Constants.COSMOS_BOARDCAST_TX_MODE_BLOCK;
	};

	public String GetTxTypeStdTx() {
		return Constants.COSMOS_TX_TYPE_STDTX;
	};

	public String GetNode0Mnmonic() {
		return "depart neither they audit pen exile fire smart tongue express blanket burden culture shove curve address together pottery suggest lady sell clap seek whisper";
	};

	public String GetNode1Mnmonic() {
		return "country live width exotic behind mad belt bachelor later outside forget rude pudding material orbit shoot kind curve endless prosper make exotic welcome maple";
	};

	public String GetNode5Mnmonic() {
		return "clerk universe city game fortune kitchen arrive regret donor wide borrow typical hold harbor actor raise inside truly nation ethics rally layer arena clump";
	};

	public String GetNode5Addr() {
		return "eva1hxtpykp4x8u99hqkq5mayea5tgdpehcxy4v2k5";
	};

	public String GetNode1Addr() {
		return "eva1geyy4wtak2q9effnfhze9u4htd8yxxma0jmgl6";
	};

	public String GetTransferAmount() {
		return "160" + "000000000";
	};

	public String GetTendermintConsensusPubkey() {
		return "evavalconspub1zcjduepqc4z8pwqsvdh0pectjzuh5s0jaata266x8cf7ka2styqzkqrmq8qsyzctcg";
	};

}
