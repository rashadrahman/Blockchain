package BlockChain;

import java.util.*;

import javax.print.DocFlavor.URL;

import java.io.*;
import java.lang.*;
import java.net.URISyntaxException;
import java.sql.Timestamp;

public class BlockChain {

	//Instantiates an empty BlockChain of type ArrayList
	private static ArrayList<Block> blockchain = new ArrayList();

	/**
	 * Constructor for new BlockChain
	 * @param blockchain of type ArrayList
	 */
	public BlockChain(ArrayList<Block> blockchain) {
		this.blockchain = blockchain;
	}

	/**
	 * Returns the size of the BlockChain
	 * @return int of size
	 */
	public int getSize() {
		return blockchain.size();
	}

	/**
	 * Returns the last block's hash code
	 * @return String of previous hash code
	 */
	public String getLastHash() {
		return blockchain.get(blockchain.size()-1).getHash();
	}

	/**
	 * Generates a boolean value if the user exists in the Blockchain
	 * @param username of name
	 * @return boolean value of computation
	 */
	public boolean isUsername(String username) {
		boolean flag = false;
		for (Block x : blockchain) {
			//Checking if the username is a sender
			if (username.toLowerCase().equals(x.getSender())) {
				flag = true;
			} 
			//Checking if the username is a receiver
			else if (username.toLowerCase().equals(x.getReceiver())) {

				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Returns a BlockChain from given file
	 * @param fileName of File
	 * @return BlockChain of new read file
	 */
	public static BlockChain fromFile(String fileName) {
		//Creates empty BlockChain
		BlockChain fin = null;

		//Creates scanner and empty file
		Scanner scan;
		File file;

		//Creates empty variables
		String previousHash = "";
		int index = 0;
		long timestamp = -1;
		String sender = "";
		String receiver = "";
		int amount = 0;
		String nonce = "";
		String hash = "";
		int i = 0;


		try {

			file = new File(fileName);
			scan = new Scanner(file);
			//Searches for text in the file
			while (scan.hasNextLine()) {
				//seven variables to look for
				for (int j = 0; j < 7; j++) {
					String x = scan.nextLine();
					//index line
					if (j == 0) {
						index = Integer.parseInt(x);
					} 
					//Timestamp line
					else if (j == 1) {
						timestamp = Long.parseLong(x);
					} 
					//sender line
					else if (j == 2) {
						sender = x;
					} 
					//receiver line
					else if (j == 3) {
						receiver = x;
					} 
					//amount line
					else if (j == 4) {
						amount = Integer.parseInt(x);
					} 
					//nonce line
					else if (j == 5) {
						nonce = x;
					} 
					//hash line
					else if (j == 6) {
						hash = x;
					}

				}
				//All variables are filled out by this point

				//Creates a Block and populates Block and Transaction
				Block tempb;
				Transaction tempt = new Transaction(sender, receiver, amount);
				//If it is the first Block in the BlockChain
				if (index == 0) {
					tempb = new Block(index, timestamp, nonce, "00000", hash, tempt);
					blockchain.add(tempb);
				} 
				//If it is any other Block
				else {
					tempb = new Block(index, timestamp, nonce, blockchain.get(i).getHash(), hash, tempt);
					blockchain.add(tempb);
					i++;
				}
			}
			//Adds newly created block into BlockChain
			fin = new BlockChain(blockchain);

		}

		//Catches exceptions
		catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception");
		} 

		catch (IOException e) {
			System.out.println("IOException");
		}

		//Returns BlockChain
		return fin;
	}




	/**
	 * Writes the current BlockChain into a new file
	 * @param fileName of File
	 */
	public void toFile(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			//For loop from the firs to the last Block in the BlockChain
			for (Block s : blockchain) {
				//Writes the file the same way fromFile(String fileName) reads a file
				writer.println(s.getIndex());
				writer.println(s.gettimestamp().getTime());
				writer.println(s.getSender());
				writer.println(s.getReceiver());
				writer.println(s.getAmount());
				writer.println(s.getNonce());
				writer.println(s.getHash());
			}
			writer.close();
		} 
		//Catches exceptions
		catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported Encoding Exception");
		} 
		catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception");
		}
	}

	
	
	/**
	 * Returns the balance of the given username
	 * @param username of name
	 * @return int of balance
	 */
	public int getBalance(String username) {
		//Initially creates a balance of 0
		int balance = 0;
		for (Block x : blockchain) {
			//Makes sure that bitcoin isn't accounted for balance (Hypothetically speaking, bitcoin should have infinite money)
			//System.out.println(x.getAmount());
			if (!(username.toLowerCase().equals("bitcoin"))) {
				//Checks whether the user is a sender
				if (x.getSender().equals(username.toLowerCase())) {
					//Subtracts the amount at that given block from balance
					
					balance =balance- x.getAmount();
				} 
				//Checks whether the user is a receiver
				else if (x.getReceiver().equals(username.toLowerCase())) {
					//Adds the amount at the given block from balance
					balance =balance+ x.getAmount();
				}
			}
			
		}
		return balance;
	}




	/**
	 * Returns the boolean value of whether or not the BlockChain is valid
	 * @return boolean value
	 * @throws UnsupportedEncodingException error
	 */
	public boolean validateBlockchain() throws UnsupportedEncodingException{
		//Assumes the BlockChain is valid
		boolean flag = true;
		Sha1 test=new Sha1();

		//Checks if the hash and the hash generation of the Block's toString are consistent
		for (Block x:blockchain) {
			//Compares if the hash and the hash generated by the Block's toString aren't equal at the current block
			if(!x.getHash().equals(test.hash(x.toString(),0))) {
				flag=false;
			}
		}



		// Validates hash codes
		for (int i = 1; i < blockchain.size(); i++) {
			//Checks if the previous hash of the Block isn't equal to the hash of the next Block
			if ((blockchain.get(i - 1)).getHash() != (blockchain.get(i)).getPreviousHash()) {
				flag = false;
				System.out.println("hashes:"+blockchain.get(i - 1).getHash()!=(blockchain.get(i)).getPreviousHash());
			}
		}
		// Validates index
		for (int i = 0; i < blockchain.size(); i++) {
			//Checks if the index's aren't equal
			if (i != blockchain.get(i).getIndex()) {
				flag = false;
				System.out.println("index:"+(i!= blockchain.get(i).getIndex()));
			}
		}
		//Validates the user's balance
		for (Block x : blockchain) {
			//Checks if the balance of every user is above 0
			if (getBalance(x.getSender()) < 0) {
				flag = false;
				System.out.println("balance:"+getBalance(x.getSender())+x.getSender());
			}
		}

		return flag;
	}

	/**
	 * Generates a random nonce string
	 * @return String of nonce
	 */
	public String generateNonce() {
		Random random = new Random();
		String result = "";
		boolean flag=true;
		//Chooses the length of the string
		int rand2 = random.nextInt((15 - 3) + 1) + 3;

		for (int i = 0; i < rand2; i++) {
			//Chooses the ascii value for the character at the given index
			int rand = random.nextInt((126 - 33) + 1) + 33;
			//Adds the character to the String
			result = result + (char) rand;
		}
		return result;
	}


	/**
	 * Adds the given Block to the BlockChain
	 * @param block of block
	 */
	public void addBlock(Block block) {
		blockchain.add(block);

	}



	public static void main(String[] args) throws UnsupportedEncodingException, URISyntaxException {


		Scanner scan = new Scanner(System.in);

		BlockChain blockchain;

		//Sets the Average list for later
		int[] avg=new int[11];

		//Gets the file Name
		System.out.println("Enter the file you wish to read");
		String fileName = scan.nextLine();



		try {
			//Creates a BlockChain from the File
			blockchain = BlockChain.fromFile(fileName);



			System.out.println("Validating Blockchain...");

			//Validates BlockChain
			blockchain.validateBlockchain();

			if(blockchain.validateBlockchain()==true) {
				System.out.println("Validated Blockchain");}
			else {
				System.out.println("Blockchain Invalid");}

			//System.out.println(blockchain.getBalance("satoshi"));

			//Asks user for the option of adding a Transaction
			boolean repeatTran=true;
			boolean flag1=false;
			String confirm="";
			while(flag1==false) {
				System.out.println("Would you like to do a transaction?(Y or N)");
				String input = scan.nextLine();
				if(input.toUpperCase().equals("Y")) {
					confirm=input.toUpperCase();
					flag1=true;
				}
				else if(input.toUpperCase().equals("N")) {
					flag1=true;
					System.out.println("Code Exuction Terminated");
					repeatTran=false;

				}
				else {
					System.out.println("Wrong input,try again Y for yes N for no");
				}
			}

			//Starts the addition of a new Block
			while(repeatTran==true&&flag1==true&&blockchain.validateBlockchain()==true) {
				//Asks for input for all needed variables
				if(confirm.equals("Y")) {
					boolean flag2=false;
					String user="";
					while(flag2==false) {

						System.out.println("Enter the sender: ");
						String input = scan.next();
						input.toLowerCase();
						if(blockchain.isUsername(input.toLowerCase())) {
							user=input;
							flag2=true;
						}
						else {
							System.out.println("Error, User doesn't exist. Try again");
						}
					}
					user.toLowerCase();

					boolean flag3=false;
					String receiver="";
					System.out.println("Enter receiver: ");
					String input2 = scan.next();
					receiver=input2;
					receiver.toLowerCase();

					boolean flag4=false;
					int amount=-1;
					while(flag4==false) {
						System.out.println("Enter amount: ");
						int input=scan.nextInt();
						if(blockchain.getBalance(user)-input>=0||user.toLowerCase().equals("bitcoin")) {
							amount=input;
							flag4=true;
						}
						else {
							System.out.println("User doesn't have enough funds to send that amount, \nuser balance:"+blockchain.getBalance(user)+"\ntry another amount");
						}
					}


					//Adds a Block the BlockChain with given information

					Sha1 hash=new Sha1();

					Timestamp timestamp= new Timestamp(System.currentTimeMillis());
					long time=timestamp.getTime();
					String nonce=blockchain.generateNonce();
					Transaction transaction=new Transaction(user,receiver,amount);
					String hashcode=hash.hash(nonce);
					Block temp=new Block(blockchain.getSize(),time,nonce,blockchain.getLastHash(),hashcode,transaction);

					//Numbers of attempts at getting a hash generation that starts with '00000'
					int numoftries=0;
					boolean flag=true;

					//Attempts to make this hash start with '00000'
					while(flag==true) {
						temp=new Block(blockchain.getSize(),time,nonce,blockchain.getLastHash(),hashcode,transaction);
						if(hash.hash(temp.toString()).substring(0,5).equals("00000")) {
							hashcode=hash.hash(temp.toString());
							temp=new Block(blockchain.getSize(),time,nonce,blockchain.getLastHash(),hashcode,transaction);
							flag=false;
						}
						else {
							nonce=blockchain.generateNonce();
						}
						numoftries++;
					}
					//Prints the number of attemps
					System.out.println(numoftries);
					int i=0;
					avg[i]=numoftries;

					//Adds the block to the BlockChain
					blockchain.addBlock(temp);



					//Asking for another transaction
					boolean flag5=false;

					while(flag5==false) {
						System.out.println("Would you like to make another transaction?(Y or N)");
						String repeat=scan.next();
						if(repeat.toUpperCase().equals("Y")) {
							flag5=true;
						}
						else if(repeat.toUpperCase().equals("N")) {
							flag5=true;
							repeatTran=false;
						}
						else {
							System.out.println("Invalid response, try again");
						}
					}


				}

			}

			System.out.println("Program Terminated");

			//ToFile creation of new BlockChain
			String minerid="_anguy116";
			blockchain.toFile("blockchain_"+minerid+".txt");
			System.out.println("File saved as: "+"blockchain_"+minerid+".txt");

			//Computes average of attempts to satisfy the prior hash generation condition
			int x=0;
			for(int i=0;i<avg.length;i++) {
				x+=avg[i];
			}
			System.out.println("Average time"+x/14);

		}
		catch(NullPointerException e) {
			System.out.println("File was entered wrong so, Program Terminated");
		}

	}


}