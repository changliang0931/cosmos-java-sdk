package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgWithdrawDelegatorRewardValue;

@SuppressWarnings("rawtypes")
public class MsgWithdrawDelegatorReward extends MsgBase {

	public static void main(String[] args) {
		MsgWithdrawDelegatorReward msg = new MsgWithdrawDelegatorReward();
		msg.setMsgType("cosmos-sdk/MsgWithdrawDelegationReward");
		msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

		Message messages = msg.produceMsg();

		msg.submit(messages, "6", Constants.COSMOS_DEFAULT_GAS, "cosmos set withdrawAddr","");
	}

	public Message produceMsg() {
		String validatorAddr = this.validatorAddress;
		MsgWithdrawDelegatorRewardValue value = new MsgWithdrawDelegatorRewardValue();
		value.setValidatorAddress(validatorAddr);
		value.setDelegatorAddress(this.accountAddress);

		Message<MsgWithdrawDelegatorRewardValue> msg = new Message<MsgWithdrawDelegatorRewardValue>();
		msg.setType(msgType);
		msg.setValue(value);
		return msg;
	}

}
