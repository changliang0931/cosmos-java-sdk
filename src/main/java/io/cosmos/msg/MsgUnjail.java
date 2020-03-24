package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgUnjailValue;

@SuppressWarnings("rawtypes")
public class MsgUnjail extends MsgBase {

	public static void main(String[] args) {
		EnvInstance.setEnv("okl");
		MsgUnjail msg = new MsgUnjail();
		msg.setMsgType("cosmos-sdk/MsgUnjail");
		msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());
		Message messages = msg.produceMsg();
		msg.submit(messages, "10" + "000000000", Constants.COSMOS_DEFAULT_GAS, "","");
	}

	public Message produceMsg() {
		MsgUnjailValue value = new MsgUnjailValue();
		value.setAddress(this.validatorAddress);

		Message<MsgUnjailValue> msg = new Message<MsgUnjailValue>();
		msg.setType(msgType);
		msg.setValue(value);
		return msg;
	}
}
