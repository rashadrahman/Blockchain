package BlockChain;

public class Transaction{
	private String sender;
	private String receiver;
	private int amount;

	//Constructer for Transaction
	public Transaction(String sender,String receiver,int amount) {
		this.sender=sender;
		this.receiver=receiver;
		this.amount=amount;
	}

	/**
	 * returns name of Sender
	 * @return String of the Sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * returns name of Receiver
	 * @return String of the reciever
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * returns amount of Bitcoin
	 * @return int of Amount of Bitcoin
	 */
	public int getAmount() {
		return amount;
	}



	/**
	 * returns String of sender receiver and amount of Bitcoin
	 * @return String of sender, receiver and amount
	 */
	public String toString() {
		return sender + ":"+receiver +"="+ amount;
	}
}