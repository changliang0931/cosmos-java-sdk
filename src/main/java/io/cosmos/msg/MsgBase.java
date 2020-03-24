package io.cosmos.msg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.cosmos.common.Constants;
import io.cosmos.common.EnvInstance;
import io.cosmos.common.HttpUtils;
import io.cosmos.common.Utils;
import io.cosmos.crypto.Crypto;
import io.cosmos.msg.utils.BoardcastTx;
import io.cosmos.msg.utils.Data2Sign;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.TxValue;
import io.cosmos.types.Fee;
import io.cosmos.types.Pubkey;
import io.cosmos.types.Signature;
import io.cosmos.types.Token;
import io.cosmos.util.EncodeUtils;

@SuppressWarnings("rawtypes")
public class MsgBase {

	protected String restServerUrl = EnvInstance.getEnv().GetRestServerUrl();
	protected String sequenceNum;
	protected String accountNum;
	protected String pubKeyString;
	protected String accountAddress;
	protected String consensusAddress;
	protected String validatorAddress;
	protected String priKeyString;
	protected String msgType;

	/**
	 * 设置消息类型
	 * 
	 * @param type
	 *            codec.go 中注册的消息类型
	 */
	public void setMsgType(String type) {
		this.msgType = type;
	}

	static Signature sign(Data2Sign obj, String privateKey) throws Exception {
		String sigResult = null;
		Signature signature = null;
		Pubkey pubkey = null;
		sigResult = obj2byteok(obj, privateKey);
		// sigResult = obj2byte(obj, privateKey);
		signature = new Signature();
		pubkey = new Pubkey();
		pubkey.setType(Constants.COSMOS_PUBKEY_TYPE);
		pubkey.setValue(Strings.fromByteArray(Base64.encode(Hex.decode(Crypto.generatePubKeyHexFromPriv(privateKey)))));
		signature.setPubkey(pubkey);
		signature.setSignature(sigResult);

		System.out.println("privateKey: ");
		System.out.println(privateKey);

		System.out.println("signature: ");
		System.out.println(sigResult);

		return signature;
	}

	static String obj2byte(Data2Sign data, String privateKey) {

		String sigResult = null;
		try {
			// System.out.println("===============Utils.serializer.toJson=================");

			System.out.println("row data:");
			System.out.println(data);
			System.out.println("json data:");

			String signDataJson = Utils.serializer.toJson(data);
			System.out.println(signDataJson);

			// 序列化
			byte[] byteSignData = signDataJson.getBytes();

			System.out.println("byte data length:");
			System.out.println(byteSignData.length);

			byte[] sig = Crypto.sign(byteSignData, privateKey);
			sigResult = Strings.fromByteArray(Base64.encode(sig));

			System.out.println("result:");
			System.out.println(sigResult);
			// System.out.println("================================");

		} catch (Exception e) {
			System.out.println("serialize msg failed");
		}
		return sigResult;
	}

	static String obj2byteok(Data2Sign data, String privateKey) {
		byte[] byteSignData = null;
		String sigResult = null;
		byte[] tmp = null;
		byte[] sig = null;
		try {

			System.out.println("===============EncodeUtils=================");
			System.out.println("row data:");
			System.out.println(data);
			System.out.println("json data:");
			System.out.println(EncodeUtils.toJsonStringSortKeys(data));

			tmp = EncodeUtils.toJsonEncodeBytes(data);
			byteSignData = EncodeUtils.hexStringToByteArray(EncodeUtils.bytesToHex(tmp));

			System.out.println("byte data length:");
			System.out.println(byteSignData.length);

			sig = Crypto.sign(byteSignData, privateKey);
			sigResult = Strings.fromByteArray(Base64.encode(sig));

			System.out.println("result:");
			System.out.println(sigResult);
			System.out.println("================================");

		} catch (Exception e) {
			System.out.println("serialize msg failed");
		}

		return sigResult;
	}

	/**
	 * 获取签名交易
	 * 
	 * @param message
	 *            msg
	 * @param feeAmount
	 *            转账金额
	 * @param gas
	 *            默认200000
	 * @param memo
	 *            备注
	 * @param mode
	 *            "block" after tx commit ;"sync" after CheckTx ; "async" right
	 *            away ;
	 * @return
	 */
	public String getSignTx(Message message, String feeAmount, String gas, String memo, String mode) {
		List<Token> amountList = null;
		Token amount = null;
		Fee fee = null;
		Message[] msgs = null;
		Data2Sign data = null;
		Signature signature = null;
		BoardcastTx cosmosTransaction = null;
		TxValue cosmosTx = null;
		List<Signature> signatureList = null;
		try {
			amountList = new ArrayList<Token>();
			if (feeAmount.length() > 0) {
				amount = new Token();
				amount.setDenom(EnvInstance.getEnv().GetDenom());
				amount.setAmount(feeAmount);
				amountList.add(amount);
			}

			// 组装待签名交易结构
			fee = new Fee();
			fee.setAmount(amountList);
			fee.setGas(gas);

			msgs = new Message[1];
			msgs[0] = message;

			data = new Data2Sign(accountNum, EnvInstance.getEnv().GetChainid(), fee, memo, msgs, sequenceNum);

			signature = MsgBase.sign(data, priKeyString);

			cosmosTransaction = new BoardcastTx();

			if (StringUtils.isBlank(mode)) {
				cosmosTransaction.setMode(EnvInstance.getEnv().GetTxModeBlock());
			}

			cosmosTx = new TxValue();

			cosmosTx.setType(EnvInstance.getEnv().GetTxTypeStdTx());

			cosmosTx.setMsgs(msgs);

			if (EnvInstance.getEnv().HasFee()) {
				cosmosTx.setFee(fee);
			}

			cosmosTx.setMemo(memo);

			signatureList = new ArrayList<Signature>();
			signatureList.add(signature);
			cosmosTx.setSignatures(signatureList);

			cosmosTransaction.setTx(cosmosTx);
			return cosmosTransaction.toJson();
		} catch (Exception e) {
			System.out.println("serialize transfer msg failed");
		}
		return "";
	}

	/**
	 * 签名并发送交易
	 * 
	 * @param message
	 *            msg
	 * @param feeAmount
	 *            转账金额
	 * @param gas
	 *            默认200000
	 * @param memo
	 *            备注
	 * @param mode
	 *            "block" after tx commit ;"sync" after CheckTx ; "async" right
	 *            away ;
	 * @return
	 */
	public void submit(Message message, String feeAmount, String gas, String memo, String mode) {
		List<Token> amountList = null;
		Token amount = null;
		Fee fee = null;
		Message[] msgs = null;
		Data2Sign data = null;
		Signature signature = null;
		BoardcastTx cosmosTransaction = null;
		TxValue cosmosTx = null;
		List<Signature> signatureList = null;
		try {
			amountList = new ArrayList<Token>();
			if (feeAmount.length() > 0) {
				amount = new Token();
				amount.setDenom(EnvInstance.getEnv().GetDenom());
				amount.setAmount(feeAmount);
				amountList.add(amount);
			}

			// 组装待签名交易结构
			fee = new Fee();
			fee.setAmount(amountList);
			fee.setGas(gas);

			msgs = new Message[1];
			msgs[0] = message;

			data = new Data2Sign(accountNum, EnvInstance.getEnv().GetChainid(), fee, memo, msgs, sequenceNum);

			signature = MsgBase.sign(data, priKeyString);

			cosmosTransaction = new BoardcastTx();
			if (StringUtils.isBlank(mode)) {
				cosmosTransaction.setMode(EnvInstance.getEnv().GetTxModeBlock());
			}

			cosmosTx = new TxValue();
			cosmosTx.setType(EnvInstance.getEnv().GetTxTypeStdTx());
			cosmosTx.setMsgs(msgs);

			if (EnvInstance.getEnv().HasFee()) {
				cosmosTx.setFee(fee);
			}

			cosmosTx.setMemo(memo);

			signatureList = new ArrayList<Signature>();
			signatureList.add(signature);
			cosmosTx.setSignatures(signatureList);

			cosmosTransaction.setTx(cosmosTx);

			boardcast(cosmosTransaction.toJson());
		} catch (Exception e) {
			System.out.println("serialize transfer msg failed");
		}
	}

	/**
	 * 助记码派生用户
	 * 
	 * @param mnemonic
	 */
	public void initMnemonic(String mnemonic) {
		String prikey = Crypto.generatePrivateKeyFromMnemonic(mnemonic, EnvInstance.getEnv().GetPassphrase());
		init(prikey);
	}

	/**
	 * 助记码派生用户
	 * 
	 * @param mnemonic
	 *            助记码派生用户
	 * @param passphrase
	 *            密码
	 */
	public void initMnemonicpPassphrase(String mnemonic, String passphrase) {
		String prikey = Crypto.generatePrivateKeyFromMnemonic(mnemonic, passphrase);
		init(prikey);
	}

	/**
	 * 私钥派生用户
	 * 
	 * @param privateKey
	 *            私钥
	 */
	public void initPrivateKey(String privateKey) {
		init(privateKey);
	}

	void init(String privateKey) {
		pubKeyString = Hex.toHexString(Crypto.generatePubKeyFromPriv(privateKey));
		accountAddress = Crypto.generateAccountAddressFromPriv(privateKey);
		JSONObject accountJson = JSON.parseObject(getAccountPrivate(accountAddress));
		sequenceNum = getSequance(accountJson);
		accountNum = getAccountNumber(accountJson);
		priKeyString = privateKey;
		validatorAddress = Crypto.generateValidatorAddressFromPub(pubKeyString);
		consensusAddress = Crypto.generateConsensusAddressFromPub(pubKeyString);

	}

	/**
	 * 获取Validator地址
	 * 
	 * @return
	 */
	public String getValidatorAddress() {
		return validatorAddress;
	}

	/**
	 * 获取Account地址
	 * 
	 * @return
	 */
	public String getAccountAddress() {
		return accountAddress;
	}

	/**
	 * 获取consensus地址
	 * 
	 * @return
	 */
	public String getConsensusAddress() {
		return consensusAddress;
	}

	private String getAccountPrivate(String userAddress) {
		StringBuilder url = null;
		url = new StringBuilder();
		url.append(restServerUrl);
		url.append(EnvInstance.getEnv().GetRestPathPrefix());
		url.append(Constants.COSMOS_ACCOUNT_URL_PATH);
		url.append(userAddress);
		System.out.println(url.toString());
		return HttpUtils.httpGet(url.toString());
	}

	private String getSequance(JSONObject account) {
		Integer res = account.getJSONObject("result").getJSONObject("value").getInteger("sequence");
		return res.toString();
	}

	private String getAccountNumber(JSONObject account) {
		Integer res = account.getJSONObject("result").getJSONObject("value").getInteger("account_number");
		return res.toString();
	}

	protected JSONObject boardcast(String tx) {
		System.out.println("Boardcast tx:");
		System.out.println(tx);

		System.out.println("Response:");
		String res = HttpUtils.httpPost(restServerUrl + EnvInstance.getEnv().GetTxUrlPath(), tx);
		JSONObject result = JSON.parseObject(res);

		System.out.println(result);
		System.out.println("------------------------------------------------------");
		return result;
	}

}
