package io.cosmos.msg;

import java.util.ArrayList;
import java.util.List;

import io.cosmos.common.Constants;
import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgSendValue;
import io.cosmos.types.Token;

@SuppressWarnings("rawtypes")
public class MsgSend extends MsgBase {

	public static void main(String[] args) {
		EnvInstance.setEnv("okl");
		MsgSend msg = new MsgSend();

		msg.setMsgType("cosmos-sdk/MsgSend");

		msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

		Message messages = msg.produceSendMsg(EnvInstance.getEnv().GetDenom(), EnvInstance.getEnv().GetTransferAmount(),
				EnvInstance.getEnv().GetNode5Addr());

		msg.submit(messages, "6", Constants.COSMOS_DEFAULT_GAS, "cosmos transfer!","");
	}

	public Message produceSendMsg(String denom, String amountDenom, String to) {

		List<Token> amountList = new ArrayList<Token>();
		Token amount = new Token();
		amount.setDenom(denom);
		amount.setAmount(amountDenom);
		amountList.add(amount);

		MsgSendValue value = new MsgSendValue();
		value.setFromAddress(this.accountAddress);
		value.setToAddress(to);
		value.setAmount(amountList);

		Message<MsgSendValue> msg = new Message<MsgSendValue>();
		msg.setType(msgType);
		msg.setValue(value);
		return msg;
	}

}
