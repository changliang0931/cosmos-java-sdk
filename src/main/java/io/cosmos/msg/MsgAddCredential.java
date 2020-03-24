package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgAddCredentialValue;

public class MsgAddCredential extends MsgBase {
	public static final String MsgType_AddCredentialMsg = "/credential/AddCredential";

	public static void main(String[] args) {
		String mnmonic = "position goat estate hamster wash lunar alpha sword pledge basic wool special sand peanut father cactus adjust theme gossip laptop lift chef increase position";
		MsgAddCredential msg = new MsgAddCredential();
		msg.setMsgType(MsgType_AddCredentialMsg);
		msg.initMnemonic(mnmonic);
		String hash = "credential.111hash111";
		String path = "credential.path";
		String time = "credential.time";
		String name = "credential.name";
		String owner = "cosmos1mr95mmyu02z9envmzty5q9u4kc4qmv73e5jr8v";
		Message<MsgAddCredentialValue> messages = msg.addCredentialMsg(hash, path, time, name, owner);
		msg.getSignTx(messages, "", Constants.COSMOS_DEFAULT_GAS, "","");
//		msg.submit(messages, "", Constants.COSMOS_DEFAULT_GAS, "","");
	}

	public Message<MsgAddCredentialValue> addCredentialMsg(String hash, String path, String time, String name,
			String owner) {
		MsgAddCredentialValue msgValue = null;
		Message<MsgAddCredentialValue> msg = null;
		if (org.apache.commons.lang3.StringUtils.isBlank(hash) || org.apache.commons.lang3.StringUtils.isBlank(path)
				|| org.apache.commons.lang3.StringUtils.isBlank(time)
				|| org.apache.commons.lang3.StringUtils.isBlank(name)
				|| org.apache.commons.lang3.StringUtils.isBlank(owner)) {
			return null;
		}
		msgValue = new MsgAddCredentialValue();
		msgValue.setHash(hash);
		msgValue.setPath(path);
		msgValue.setTime(time);
		msgValue.setName(name);
		msgValue.setOwner(owner);
		msg = new Message<MsgAddCredentialValue>();
		msg.setType(msgType);
		msg.setValue(msgValue);
		return msg;
	}
}
