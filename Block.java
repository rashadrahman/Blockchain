package BlockChain;

import java.util.*;
import java.sql.Timestamp;

//Andrew Nguyen
//300019561
//Block Class
public class Block {
	private int index;
	private java.sql.Timestamp timestamp;
	private Transaction transaction; // the transaction object
	private String nonce; // random string (for proof of work)
	private String previousHash; // previous hash (set to "" in first block)
	// (in first block, set to string of zeroes of size of complexity "00000")
	private String hash;

	// constructor given with a timestamp

	/**
	 * Constructor for the type Block
	 * 
	 * @param index of the BlockChain
	 * @param timestamp of the Time Added to the BlockChain
	 * @param nonce code
	 * @param previousHash previous hash code
	 * @param hash code
	 * @param transaction Transaction
	 */
	public Block(int index, long timestamp, String nonce, String previousHash, String hash, Transaction transaction) {
		//Instantiates the variables
		this.timestamp = new Timestamp(timestamp);
		this.index = index;
		this.nonce = nonce;
		this.previousHash = previousHash;
		this.hash = hash;
		this.transaction = transaction;

	}
	/**
	 * Returns the index of the block in the BlockChain
	 * @return int of the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * Returns the time the transaction was made
	 * @return Timestamp of the block
	 */
	public Timestamp gettimestamp() {
		return timestamp;
	}
	/**
	 * Returns sender's name
	 * @return String of the sender
	 */
	public String getSender() {
		return transaction.getSender();
	}
	/**
	 * Returns receiver's name
	 * @return String of the receiver
	 */
	public String getReceiver() {
		return transaction.getReceiver();
	}
	/**
	 * Returns the amount of the transaction
	 * @return int of the amount
	 */
	public int getAmount() {
		return transaction.getAmount();
	}

	/**
	 * Returns the nonce code
	 * @return String of the nonce
	 */
	public String getNonce() {
		return nonce;
	}
	/**
	 * Returns the previous block's hash code
	 * @return String of previous hash
	 */
	public String getPreviousHash() {
		return previousHash;
	}

	/**
	 * Returns hash code of current block
	 * @return String of hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * Returns a String containing Timestamp, Transaction, nonce and previous hash
	 * @return String 
	 */
	public String toString() {
		return timestamp.toString() + ":" + transaction.toString() + "." + nonce + previousHash;
	}
}