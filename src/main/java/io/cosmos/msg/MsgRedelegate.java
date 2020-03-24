package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgBeginRedelegateValue;
import io.cosmos.types.Token;

@SuppressWarnings("rawtypes")
public class MsgRedelegate extends MsgDelegate {

	public static void main(String[] args) {
		MsgRedelegate msg = new MsgRedelegate();
		msg.setMsgType("cosmos-sdk/MsgBeginRedelegate");
		msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

		Message messages = msg.produceDelegateMsg(EnvInstance.getEnv().GetDenom(), "100");

		msg.submit(messages, "3", Constants.COSMOS_DEFAULT_GAS, "Delegate memo","");
	}

	protected Message produceDelegateMsg(String delegateDenom, String delegateAmount) {

		MsgRedelegate tmp = new MsgRedelegate();
		tmp.initMnemonic(EnvInstance.getEnv().GetNode1Mnmonic());
		String validatorDstAddress = tmp.validatorAddress;
		String validatorSrcAddress = this.validatorAddress;

		MsgBeginRedelegateValue delegateValue = new MsgBeginRedelegateValue();
		delegateValue.setValidatorSrcAddress(validatorSrcAddress);
		delegateValue.setValidatorDstAddress(validatorDstAddress);
		delegateValue.setDelegatorAddress(accountAddress);
		Token token = new Token();
		token.setDenom(delegateDenom);
		token.setAmount(delegateAmount);
		delegateValue.setAmount(token);
		Message<MsgBeginRedelegateValue> messageDelegateMulti = new Message<MsgBeginRedelegateValue>();
		messageDelegateMulti.setType(msgType);
		messageDelegateMulti.setValue(delegateValue);
		return messageDelegateMulti;
	}

}
