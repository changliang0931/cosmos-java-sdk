package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;

@SuppressWarnings("rawtypes")
public class MsgUnbond extends MsgDelegate {

	public static void main(String[] args) {
		MsgUnbond msg = new MsgUnbond();
		msg.setMsgType("cosmos-sdk/MsgUndelegate");
		msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

		Message messages = msg.produceDelegateMsg(EnvInstance.getEnv().GetDenom(), "100");

		msg.submit(messages, "3", Constants.COSMOS_DEFAULT_GAS, "Delegate memo","");
	}

}
