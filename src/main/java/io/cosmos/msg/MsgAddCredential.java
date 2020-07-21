package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgAddCredentialValue;

public class MsgAddCredential extends MsgBase {
	public static final String MsgType_AddCredentialMsg = "/credential/AddCredential";

	public static void main(String[] args) {
		String mnmonic = "wolf thumb intact fantasy cave lonely barely setup dress life invest kingdom potato apple iron sentence sense paddle then ability minimum attract pottery glue";
		MsgAddCredential msg = new MsgAddCredential();
		msg.setMsgType(MsgType_AddCredentialMsg);
		msg.initMnemonic(mnmonic);
		String hash = "c46532aa8d83a453ab0cfc4c247c3f75c1d7a9724bf3db034c9ddca5cf732cc6";
		String path = "url is null";
		String time = "1594706001510";
		String name = "c46532aa8d83a453ab0cfc4c247c3f75c1d7a9724bf3db034c9ddca5cf732cc6";
		String owner = "ftsafe1d3wjggvjhf79q72m92qyaddh0fhjhjvpyzcwgy";
		Message<MsgAddCredentialValue> messages = msg.addCredentialMsg(hash, path, time, name, owner);
//		msg.getSignTx(messages, "", Constants.COSMOS_DEFAULT_GAS, "","");
		msg.submit(messages, "", "200000", "","");
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
