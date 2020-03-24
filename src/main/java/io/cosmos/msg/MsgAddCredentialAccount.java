package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgAddCredentialAccountValue;

public class MsgAddCredentialAccount extends MsgBase {
	public static final String MsgType_AddCredentialAccountMsg = "/credential/AddCredentialAccount";

	public static void main(String[] args) {
		String mnmonic = "position goat estate hamster wash lunar alpha sword pledge basic wool special sand peanut father cactus adjust theme gossip laptop lift chef increase position";
		MsgAddCredentialAccount msg = new MsgAddCredentialAccount();
		msg.setMsgType(MsgType_AddCredentialAccountMsg);
		msg.initMnemonic(mnmonic);
		String pubkey = "cosmospub1addwnpepq0pq44g2rc25lthwmx629cztakn7f9st307zu9l7234mjdsxwfz4u5qme0s";
		String creator = "cosmos1mr95mmyu02z9envmzty5q9u4kc4qmv73e5jr8v";
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
