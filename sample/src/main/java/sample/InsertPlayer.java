package sample;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class InsertPlayer {
	
	static EntityManager em = Persistence.createEntityManagerFactory("user").createEntityManager();
	static EntityTransaction et = em.getTransaction();
	static Scanner sc = new Scanner(System.in);
	
	public static void insertPlayer() {
		
		//object creation for Player to create the table and intializing the proporties to make 
		// the feilds as coluumns in the database table and taking the values from enduser
		
		Player p = new Player();
		System.out.println("Enter Name");
		p.setName(sc.next());
		
		System.out.println("Enter JerseyNo");
		p.setJerseyNo(sc.nextInt());
		
		System.out.println("Enter FranchiseName");
		p.setFranchiseName(sc.next());
		
		System.out.println("Enter halfcenturies scored by the player");
		p.setHalfCenturies(sc.nextInt());
		
		System.out.println("Enter centuries scored by the player");
		p.setCenturies(sc.nextInt());
		
		et.begin();
		em.persist(p);
		et.commit();
		
	}
	
	// updating player info by using their id
	
	public static void updatePlayerFranchise() {
		
		System.out.println("Enter Id of the Player you want to update");
		int id =sc.nextInt();
		Player p = em.find(Player.class, id);
		
		if(p!=null) {
			System.out.println("Enter franchise name you wanted to update");
			p.setFranchiseName(sc.next());
			et.begin();
			em.merge(p);
			et.commit();
		}
		else {
			System.out.println("Player does not exists.........");
			
		}
	}
	
	// removing player data from db by thier name as input from end user
	public static void removePlayerByName() {
		System.out.println("Enter Player Name");
		String name = sc.next();
		Query q = em.createQuery("select p from Player p where p.name = : name");
		q.setParameter("name", name);
		
		List<Player> pl = q.getResultList();
		
		
		if(pl.isEmpty()) {
			System.out.println("player not exist");
		}
		else {
			et.begin();
			for(Player p :pl) {
				em.remove(p);
			}
			et.commit();
		}
	}
	
	
	public static void showPlayersData() {
		Query q = em.createQuery("from Player");
		
		List<Player> pl = q.getResultList();
		
		for(Player p : pl) {
			System.out.println(p.getName() + " " + p.getJerseyNo()+" "+p.getCenturies());
		}
	}
	
	public static void main(String[] args) {
		
		do {
			
			System.out.println("Enter 1 for adding a player \n 2 for deleting \n 3 for updating \n  4 for all players \n 5 for exit..");
			
			switch(sc.nextInt()) {
			
			case 1 : insertPlayer();
			break;
			
			case 2 : removePlayerByName();
			break;
			
			case 3 : updatePlayerFranchise();
			break;
			
			case 4 : showPlayersData();
			break;
			
			case 5 : System.exit(5);
			}
			
			
			
		}
		while(true);
		
		
		
		
		
	}

}
