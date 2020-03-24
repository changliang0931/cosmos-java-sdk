package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgWithdrawValidatorCommissionValue;

@SuppressWarnings("rawtypes")
public class MsgWithdrawValidatorCommission extends MsgBase {

	public static void main(String[] args) {
		MsgWithdrawValidatorCommission msg = new MsgWithdrawValidatorCommission();
		msg.setMsgType("cosmos-sdk/MsgWithdrawValidatorCommission");
		msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

		Message messages = msg.produceMsg();

		msg.submit(messages, "6", Constants.COSMOS_DEFAULT_GAS, "cosmos withdraw","");
	}

	public Message produceMsg() {
		String validatorAddr = this.validatorAddress;
		MsgWithdrawValidatorCommissionValue value = new MsgWithdrawValidatorCommissionValue();
		value.setValidatorAddress(validatorAddr);

		Message<MsgWithdrawValidatorCommissionValue> msg = new Message<MsgWithdrawValidatorCommissionValue>();
		msg.setType(msgType);
		msg.setValue(value);
		return msg;
	}

}
