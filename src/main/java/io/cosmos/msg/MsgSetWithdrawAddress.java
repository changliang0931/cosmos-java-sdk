package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgSetWithdrawAddrValue;

@SuppressWarnings("rawtypes")
public class MsgSetWithdrawAddress extends MsgBase {

	public static void main(String[] args) {
		MsgSetWithdrawAddress msg = new MsgSetWithdrawAddress();
		msg.setMsgType("cosmos-sdk/MsgModifyWithdrawAddress");
		msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

		Message messages = msg.produceMsg();

		msg.submit(messages, "6", Constants.COSMOS_DEFAULT_GAS, "cosmos set withdrawAddr","");
	}

	public Message produceMsg() {
		String withdrawAddr = this.accountAddress;
		MsgSetWithdrawAddrValue value = new MsgSetWithdrawAddrValue();
		value.setWithdrawAddress(withdrawAddr);
		value.setDelegatorAddress(this.accountAddress);

		Message<MsgSetWithdrawAddrValue> msg = new Message<MsgSetWithdrawAddrValue>();
		msg.setType(msgType);
		msg.setValue(value);
		return msg;
	}

}
