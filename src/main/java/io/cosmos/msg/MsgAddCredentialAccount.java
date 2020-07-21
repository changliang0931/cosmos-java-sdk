package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgAddCredentialAccountValue;

public class MsgAddCredentialAccount extends MsgBase {
	public static final String MsgType_AddCredentialAccountMsg = "/credential/AddCredentialAccount";

	public static void main(String[] args) {
		String mnmonic = "steak wave round rapid tobacco thrive mixture reopen teach priority horse fire leader enact tunnel south crane execute any satisfy merge obscure coach sort";
		MsgAddCredentialAccount msg = new MsgAddCredentialAccount();
		msg.setMsgType(MsgType_AddCredentialAccountMsg);
		msg.initMnemonic(mnmonic);
		String pubkey = "cosmospub1addwnpepqfwzcuyexm53tlrgz5cj36s90q8426r8fvra430klpsljy4lmeg3yfakce9";
		String creator = "cosmos16sp5jh9arl579q7hzcct24er7qy2qy9pu6c2ax";
		Message<MsgAddCredentialAccountValue> messages = msg.addCredentialMsg(pubkey, creator);
//		msg.getSignTx(messages, "", Constants.COSMOS_DEFAULT_GAS, "","");
		msg.submit(messages, "", Constants.COSMOS_DEFAULT_GAS, "","");
	}

	public Message<MsgAddCredentialAccountValue> addCredentialMsg(String pubkey, String creator) {
		MsgAddCredentialAccountValue msgValue = null;
		Message<MsgAddCredentialAccountValue> msg = null;
		if (org.apache.commons.lang3.StringUtils.isBlank(pubkey)
				|| org.apache.commons.lang3.StringUtils.isBlank(creator)) {
			return null;
		}
		msgValue = new MsgAddCredentialAccountValue();
		msgValue.setPubkey(pubkey);
		msgValue.setCreator(creator);

		msg = new Message<MsgAddCredentialAccountValue>();
		msg.setType(msgType);
		msg.setValue(msgValue);
		return msg;
	}
}
