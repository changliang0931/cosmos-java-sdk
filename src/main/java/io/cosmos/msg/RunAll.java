package io.cosmos.msg;

import io.cosmos.common.Constants;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgAddCredentialAccountValue;
import io.cosmos.msg.utils.type.MsgAddCredentialValue;

public class RunAll {

	public static void main(String[] args) {
		try {
			int interval = 10000;

			// RunMsgSend();
			// Thread.sleep(interval);
			// RunMsgDelegate() ;
			// Thread.sleep(interval);
			// RunMsgRedelegate();
			// Thread.sleep(interval);
			// RunMsgUnbond() ;
			// Thread.sleep(interval);
			// RunMsgSetWithdrawAddress();
			// Thread.sleep(interval);
			// RunMsgWithdrawDelegatorReward() ;
			// Thread.sleep(interval);
			// RunMsgWithdrawValidatorCommission();
			Thread.sleep(interval);
			RunMsgAddCredential();
			Thread.sleep(interval);
			RunMsgAddCredentialAccount();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// static void RunMsgSend() {
	//
	// MsgSend msg = new MsgSend();
	// msg.setMsgType("cosmos-sdk/MsgSend");
	// msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());
	//
	// Message messages = msg.produceSendMsg(
	// EnvInstance.getEnv().GetDenom(),
	// EnvInstance.getEnv().GetTransferAmount(),
	// EnvInstance.getEnv().GetNode1Addr());
	//
	// msg.submit(messages, "6", Constants.COSMOS_DEFAULT_GAS, "cosmos transfer");
	// }
	//
	// static void RunMsgDelegate() {
	//
	// MsgDelegate msg = new MsgDelegate();
	// msg.setMsgType("cosmos-sdk/MsgDelegate");
	// msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());
	//
	// Message message = msg.produceDelegateMsg(EnvInstance.getEnv().GetDenom(),
	// "100");
	//
	// msg.submit(message, "3", Constants.COSMOS_DEFAULT_GAS, "Delegate memo");
	// }
	//
	// static void RunMsgRedelegate() {
	//
	// MsgRedelegate msg = new MsgRedelegate();
	// msg.setMsgType("cosmos-sdk/MsgBeginRedelegate");
	// msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());
	//
	// Message messages = msg.produceDelegateMsg(
	// EnvInstance.getEnv().GetDenom(), "100");
	//
	// msg.submit(messages,
	// "3",
	// Constants.COSMOS_DEFAULT_GAS,
	// "Delegate memo");
	// }
	//
	// static void RunMsgUnbond() {
	//
	// MsgUnbond msg = new MsgUnbond();
	// msg.setMsgType("cosmos-sdk/MsgUndelegate");
	// msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());
	//
	// Message messages = msg.produceDelegateMsg(
	// EnvInstance.getEnv().GetDenom(), "100");
	//
	// msg.submit(messages,
	// "3",
	// Constants.COSMOS_DEFAULT_GAS,
	// "Delegate memo");
	// }
	//
	// static void RunMsgSetWithdrawAddress() {
	//
	// MsgSetWithdrawAddress msg = new MsgSetWithdrawAddress();
	// msg.setMsgType("cosmos-sdk/MsgModifyWithdrawAddress");
	// msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());
	//
	// Message messages = msg.produceMsg();
	//
	// msg.submit(messages,
	// "6",
	// Constants.COSMOS_DEFAULT_GAS,
	// "cosmos set withdrawAddr");
	// }
	//
	// static void RunMsgWithdrawDelegatorReward() {
	//
	// MsgWithdrawDelegatorReward msg = new MsgWithdrawDelegatorReward();
	// msg.setMsgType("cosmos-sdk/MsgWithdrawDelegationReward");
	// msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());
	//
	// Message messages = msg.produceMsg();
	//
	// msg.submit(messages,
	// "6",
	// Constants.COSMOS_DEFAULT_GAS,
	// "cosmos set withdrawAddr");
	// }
	//
	// static void RunMsgWithdrawValidatorCommission() {
	//
	// MsgWithdrawValidatorCommission msg = new
	// MsgWithdrawValidatorCommission();
	// msg.setMsgType("cosmos-sdk/MsgWithdrawValidatorCommission");
	// msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());
	//
	// Message messages = msg.produceMsg();
	//
	// msg.submit(messages,
	// "6",
	// Constants.COSMOS_DEFAULT_GAS,
	// "cosmos withdraw");
	// }

	static void RunMsgAddCredential() {
		String mnmonic = "position goat estate hamster wash lunar alpha sword pledge basic wool special sand peanut father cactus adjust theme gossip laptop lift chef increase position";
		MsgAddCredential msg = new MsgAddCredential();
		msg.setMsgType(MsgAddCredential.MsgType_AddCredentialMsg);
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

	static void RunMsgAddCredentialAccount() {
		String mnmonic = "position goat estate hamster wash lunar alpha sword pledge basic wool special sand peanut father cactus adjust theme gossip laptop lift chef increase position";
		MsgAddCredentialAccount msg = new MsgAddCredentialAccount();
		msg.setMsgType(MsgAddCredentialAccount.MsgType_AddCredentialAccountMsg);
		msg.initMnemonic(mnmonic);
		String pubkey = "cosmospub1addwnpepq0pq44g2rc25lthwmx629cztakn7f9st307zu9l7234mjdsxwfz4u5qme0s";
		String creator = "cosmos1mr95mmyu02z9envmzty5q9u4kc4qmv73e5jr8v";
		Message<MsgAddCredentialAccountValue> messages = msg.addCredentialMsg(pubkey, creator);
//		msg.getSignTx(messages, "", Constants.COSMOS_DEFAULT_GAS, "","");
		msg.submit(messages, "", Constants.COSMOS_DEFAULT_GAS, "","");
	}
}