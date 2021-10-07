import java.util.*;
import java.util.Random;
class Deck{
	String[] arr;
	int count;

	Deck(int len){
		arr = new String[len];
	}
	int size(){
		return this.count;
	}
	void insert(String el){
		if(arr.length == count){
			String[] newArr = new String[count+1];
			for(int i=0; i<this.count; i++){
				newArr[i] = arr[i];
			}
			arr = newArr;
		}
		arr[count++] = el;
	}
	String[] getArray(){
		return this.arr;
	}
	String get(int i) {
		return this.arr[i];
	}
	int search(String item) {
		int num = -1;
		for(int i=0; i<count; i++) {
			String str1 = arr[i].substring(0,2);
			String str2 = item.substring(0,2);
			if(str1.equals(str2)) num = i;
		}
		return num;
	}
	void removeAt(int index){
		if(index < 0 || index >= count){
			throw new IllegalArgumentException();
		}
		for(int i=index; i<count-1; i++){
			this.arr[i] = this.arr[i+1];
		}
		this.arr[count-1]=null;
		count--;
	}
	void print(){
		int counter = 0;
		for(int i=0; i<this.arr.length; i++){
			if(arr[i] != null){
				if(counter == 13 || counter == 26) System.out.println();
				System.out.print(arr[i]+" | ");
				counter++;
			}
		}
		System.out.println();
	}
	void printMenu() {
		System.out.println();
		int counter = 0;
		for(int i=0; i<this.arr.length; i++){
			if(arr[i] != null){
				if(counter == 13 || counter == 26) System.out.println();
				System.out.print("| "+arr[i] + " | ");
				counter++;
			}
		}
		System.out.println();
		for(int i=0; i<count;i++) {
			System.out.print("  ["+(i+1)+"]   ");
		}
		System.out.println();
	}
}
class MonkeyMonkey {
//declare static 2D array String of cards
//4 rows(suites) 13collumns(ranks)
	static Scanner in = new Scanner(System.in);
	static Random rand = new Random();
	static String pickedCard;
	static Deck p1OnHand = new Deck(0);
	static Deck p2OnHand = new Deck(0);
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001b[44m";
    // Declaring the background color
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	static String cards[][] =  {
		{"A \u2665","2 \u2665","3 \u2665","4 \u2665","5 \u2665","6 \u2665","7 \u2665","8 \u2665","9 \u2665","10\u2665","J \u2665","Q \u2665","K \u2665"},
		{"A \u2666","2 \u2666","3 \u2666","4 \u2666","5 \u2666","6 \u2666","7 \u2666","8 \u2666","9 \u2666","10\u2666","J \u2666","Q \u2666","K \u2666"},
		{"A \u2663","2 \u2663","3 \u2663","4 \u2663","5 \u2663","6 \u2663","7 \u2663","8 \u2663","9 \u2663","10\u2663","J \u2663","Q \u2663","K \u2663"},
		{"A \u2660","2 \u2660","3 \u2660","4 \u2660","5 \u2660","6 \u2660","7 \u2660","8 \u2660","9 \u2660","10\u2660","J \u2660","Q \u2660","K \u2660"}
	};
	public static void main(String[] args) {
		displayCards("Deck of cards");
		shuffle();
		System.out.print("Press enter to continue...");
		String enter = in.nextLine();
//		loading("Shuffling cards");
		displayCards("Shuffled cards");

		//Randomly pick a card and make it null	
		//Picking a card
		int randomRow = rand.nextInt(4);
		int randomCol = rand.nextInt(13);
		pickedCard = cards[randomRow][randomCol];
		cards[randomRow][randomCol] = null;
		System.out.print("Press enter to continue...");
		enter = in.nextLine();
		displayCards("Remaining cards:");
		System.out.println("Picked card: "+pickedCard+"\n\n");
		
		System.out.print("Press enter to continue...");
		enter = in.nextLine();
		
		//Allocate cards to p1 (rows 1 and 2)
		System.out.println(ANSI_BLUE_BACKGROUND+"Player 1 cards: "+ANSI_RESET);
		Deck p1Cards = new Deck(0);
		for(int i=0; i<=1; i++){
			for(int j=0; j<cards[i].length;j++){
				if(cards[i][j] != null)
					p1Cards.insert(cards[i][j]);
			}
		}
		p1Cards.print();
		System.out.println("\n\nCount: "+p1Cards.size());
		System.out.println("\n========================================================================================\n");
		//Allocate cards to comp (rows 3 and 4)
		System.out.println(ANSI_RED_BACKGROUND+"Player 2 cards : "+ANSI_RESET);
		Deck p2Cards =  new Deck(0);
		for (int m=2; m<=3; m++){
			for(int n=0; n<cards[m].length; n++){
				if(cards[m][n] != null)
					p2Cards.insert(cards[m][n]);
			}
		}
		p2Cards.print();
		System.out.println("\n\nCount: "+p2Cards.size());
		System.out.println("\n========================================================================================\n");
		//PLAYER 1 PAIRS
		Deck pairs1 = new Deck(0);
		String p1Array[] = sort(p1Cards.getArray());
		for(int i=0; i<p1Array.length-1;i++){
			if(p1Array[i] != null && p1Array[i+1] != null){
				String str1 = p1Array[i].substring(0,2);
				String str2 = p1Array[i+1].substring(0,2);
				if(str1.equals(str2)){
					pairs1.insert(p1Array[i]);
					pairs1.insert(p1Array[i+1]);
					p1Array[i] = null;
					p1Array[i+1] = null;
					i++;
				}
			}
		}
		
		
		System.out.println(ANSI_BLUE_BACKGROUND+"Player 1 Paired Cards: "+ANSI_RESET);
		pairs1.print();

		//PLAYER 2 PAIRS
		Deck pairs2 = new Deck(0);
		String p2Array[] = sort(p2Cards.getArray());
		for(int i=0; i<p2Array.length-1;i++){
			if(p2Array[i] != null && p2Array[i+1] != null){
				String str1 = p2Array[i].substring(0,2);
				String str2 = p2Array[i+1].substring(0,2);
				if(str1.equals(str2)){
					pairs2.insert(p2Array[i]);
					pairs2.insert(p2Array[i+1]);
					p2Array[i] = null;
					p2Array[i+1] = null;
					i++;
				}
			}
		}
		System.out.println("\n\n"+ANSI_RED_BACKGROUND+"Player 2 Paired Cards: "+ANSI_RESET);
		pairs2.print();
		
		System.out.println("\n\n");
		//Player 1 remaining cards
		for(int i=0; i<p1Array.length;i++){
			if(p1Array[i] != null) p1OnHand.insert(p1Array[i]);
		}
		System.out.println(ANSI_BLUE_BACKGROUND+"Player 1 Remaining Cards: "+ANSI_RESET);
		p1OnHand.print();
		
		
		//Player 2 remaining cards
		for(int i=0; i<p2Array.length;i++){
			if(p2Array[i] != null) p2OnHand.insert(p2Array[i]);
		}
		System.out.println("\n"+ANSI_RED_BACKGROUND+"Player 2 Remaining Cards: "+ANSI_RESET);
		p2OnHand.print();
		
		
		System.out.println("\n========================================================================================\n");
		//Toss coin
		System.out.print("Head or tails? H/T: ");
		String coinSide = in.nextLine();
		String chosenSide = coinSide.equalsIgnoreCase("H")? "Heads": coinSide.equalsIgnoreCase("T") ? "Tails":"";
		String coin[] = {"Heads","Tails"};
		String res = coin[rand.nextInt(coin.length)];
		System.out.println("Coin: "+res+"\n");
		if(chosenSide.equals(""))System.out.println("Invalid input");
		else if(chosenSide.equals(res)){
			//Player 1 draws first
			while(true) {
	//			PLAYER 1 DRAW CARD
				p1Turn();
				if(p1OnHand.count==0) {
					System.out.println("\n========================================================================================\n");
					System.out.println("\t\tWinner: "+ ANSI_BLUE_BACKGROUND+"Player 1"+ANSI_RESET);
					System.out.println("\t\tPicked card:    | "+pickedCard+" |");
					System.out.print("\t\tRemaining card: | ");
					p2OnHand.print();
					break;
				}
				if(p2OnHand.count==0) {
					System.out.println("\n========================================================================================\n");
					System.out.println("\t\tWinner: "+ ANSI_RED_BACKGROUND+"Player 2"+ANSI_RESET);
					System.out.println("\t\tPicked card:    | "+pickedCard+" |");
					System.out.print("\t\tRemaining card: | ");
					p1OnHand.print();
					break;
				}
	//			PLAYER 2 DRAW CARD
				System.out.println();
				p2Turn();
				if(p2OnHand.count==0) {
					System.out.println("\n========================================================================================\n");
					System.out.println("\t\tWinner: "+ ANSI_RED_BACKGROUND+"Player 2"+ANSI_RESET);
					System.out.println("\t\tPicked card:    | "+pickedCard+" |");
					System.out.print("\t\tRemaining card: | ");
					p1OnHand.print();
					break;
				}
				if(p1OnHand.count==0) {
					System.out.println("\n========================================================================================\n");
					System.out.println("\t\tWinner: "+ ANSI_BLUE_BACKGROUND+"Player 1"+ANSI_RESET);
					System.out.println("\t\tPicked card:    | "+pickedCard+" |");
					System.out.print("\t\tRemaining card: | ");
					p2OnHand.print();
					break;
				}
				System.out.println("\n========================================================================================\n");
			}
			
		}else{
			//Player 2 draws first
			while(true) {
	//			PLAYER 2 DRAW CARD
				p2Turn();
				if(p2OnHand.count==0) {
					System.out.println("\n========================================================================================\n");
					System.out.println("\t\tWinner: "+ ANSI_RED_BACKGROUND+"Player 2"+ANSI_RESET);
					System.out.println("\t\tPicked card:    | "+pickedCard+" |");
					System.out.print("\t\tRemaining card: | ");
					p1OnHand.print();
					
					break;
				}
				if(p1OnHand.count==0) {
					System.out.println("\n========================================================================================\n");
					System.out.println("\t\tWinner: "+ ANSI_BLUE_BACKGROUND+"Player 1"+ANSI_RESET);
					System.out.println("\t\tPicked card:    | "+pickedCard+" |");
					System.out.print("\t\tRemaining card: | ");
					p2OnHand.print();
					break;
				}
	//			PLAYER 1 DRAW CARD
				System.out.println();
				p1Turn();
				if(p1OnHand.count==0) {
					System.out.println("\n========================================================================================\n");
					System.out.println("\t\tWinner: "+ ANSI_BLUE_BACKGROUND+"Player 1"+ANSI_RESET);
					System.out.println("\t\tPicked card:    | "+pickedCard+" |");
					System.out.print("\t\tRemaining card: | ");
					p2OnHand.print();
					break;
				}
				if(p2OnHand.count==0) {
					System.out.println("\n========================================================================================\n");
					System.out.println("\t\tWinner: "+ ANSI_RED_BACKGROUND+"Player 2"+ANSI_RESET);
					System.out.println("\t\tPicked card:    | "+pickedCard+" |");
					System.out.print("\t\tRemaining card: | ");
					p1OnHand.print();
					
					break;
				}
				System.out.println("\n========================================================================================\n");
			}
		}

		// END OF MAIN METHOD
	}		


	//Method for displaying cards except for null value
	static void displayCards(String message){
		System.out.println("========================================================================================");
		System.out.println(message+"\n");
		for (int i = 0; i < cards.length; i++ ) {
			for (int l = 0; l < cards[i].length; l++){
				if (cards[i][l] !=null )
					System.out.print(cards[i][l]+" | ");
			}
			System.out.println();
		}
		System.out.println("\nCount: "+count());
		System.out.println("========================================================================================");
	}
	//Method to check the count of remaining cards
	static int count(){
		int remaining=0;
		for(int i=0; i<cards.length; i++){
			for(int j=0; j<cards[i].length; j++){
				if(cards[i][j] != null) remaining++;
			}
		}
		return remaining;
	}
	static void p1Turn() {
		System.out.println(ANSI_BLUE_BACKGROUND+"Player 1, Draw a card from Player 2"+ANSI_RESET);
		System.out.println("Player 2 Cards: ");
		p2OnHand.printMenu();
		System.out.print("Enter card index: ");
		int pick1 = Integer.parseInt(in.nextLine());
		if((pick1-1) > p2OnHand.count) System.out.println("Invalid input");
		
		String p1Picked = p2OnHand.get(pick1-1);
		System.out.println("Picked card: | "+p1Picked+" |");
		System.out.println();
		int isOnHand1 = p1OnHand.search(p1Picked);
		if(isOnHand1 == -1) {
			System.out.println("Player 1: Pass!");
			p1OnHand.insert(p1Picked);
		}else {
			System.out.println("Player 1: I have a pair!");
			System.out.println("Paired cards: "+p1OnHand.get(isOnHand1) + " | " +p2OnHand.get(pick1-1)+" |");
			p1OnHand.removeAt(isOnHand1);
		}
		p2OnHand.removeAt(pick1-1);
		System.out.print("\n\nPlayer 1 remaining cards: ");
		p1OnHand.print();
		System.out.print("Player 2 remaining cards: ");
		p2OnHand.print();
		loading("");
	}
	static void p2Turn() {
		System.out.println(ANSI_RED_BACKGROUND+"Player 2, Draw a card from Player 1"+ANSI_RESET);
		System.out.println("Player 1 Cards: ");
		p1OnHand.printMenu();
		loading("Player 2 is picking");
		int pick2 = rand.nextInt(p1OnHand.count)+1;
		System.out.println(pick2);
		if((pick2-1) > p1OnHand.count) System.out.println("Invalid input");
		
		String p2Picked = p1OnHand.get(pick2-1);
		System.out.println("Picked card: | " + p2Picked+" |");
		System.out.println();
		int isOnHand2 = p2OnHand.search(p2Picked);
		if(isOnHand2 == -1) {
			System.out.println("Player 2: Pass!");
			p2OnHand.insert(p2Picked);
		}else {
			System.out.println("Player 2: I have a pair!");
			System.out.println("Paired cards: "+p2OnHand.get(isOnHand2) + " | " +p1OnHand.get(pick2-1)+" |");
			p2OnHand.removeAt(isOnHand2);
		}
		p1OnHand.removeAt(pick2-1);
		System.out.print("\n\nPlayer 2 remaining cards: ");
		p2OnHand.print();
		System.out.print("Player 1 remaining cards: ");
		p1OnHand.print();
		loading("");
	}
// Method for shuffling the array
	static void shuffle() {

		for (int i=0; i<cards.length; i++){
			for (int l=0; l<cards[i].length; l++){
				int j = rand.nextInt(i+1);
				int k = rand.nextInt(l+1);

				String temp = cards[i][l];
				cards[i][l] = cards[j][k];
				cards[j][k] = temp;

			}
		}
	}
// Create a method for sorting cards(1D array)
// Returns sorted String array
	static String[] sort(String[] cards) { 
 		int size = cards.length;
 	 	for (int m=0; m<size-1; m++) {
  			for (int n=m+1; n<cards.length; n++){
    			if (cards[m].compareTo(cards[n])>0){
  					String temp = cards[m];
  					cards[m] = cards[n];
  					cards[n] = temp;
    			}
  			}
			//Yung sorted array dapat irereturn nito, hindi yung size
		}
		return cards;
	}
	static void loading(String message) {
//		System.out.println(message+".....");
		try{
			System.out.println();
			for(int i=0; i<10; i++) {
				System.out.print(". ");
				Thread.sleep(200);
			}
			System.out.println();
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}