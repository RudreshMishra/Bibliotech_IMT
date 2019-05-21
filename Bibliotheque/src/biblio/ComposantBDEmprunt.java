package biblio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Composant logiciel assurant la gestion des emprunts d'exemplaires
 * de livre par les abonnés.
 */
public class ComposantBDEmprunt {

  /**
   * Retourne le nombre total d'emprunts en cours référencés dans la base.
   * 
   * @return le nombre d'emprunts.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbEmpruntsEnCours() throws SQLException {
    
	  int cnt=0;

	  Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "select count(*) as count from emprunts where status = 'Borrowed'";
	  ResultSet rset = stmt.executeQuery(sql);

	  while (rset.next()) {
		  cnt = rset.getInt("count");
	  }
	  rset.close();
	  stmt.close(); 

	  //
	  // A COMPLETER
	  //
	  return cnt;
  
  }

  /**
   * Retourne le nombre d'emprunts en cours pour un abonné donné connu
   * par son identifiant.
   * 
   * @return le nombre d'emprunts.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbEmpruntsEnCours(int idAbonne) throws SQLException {
	   int cnt=0;
	   Statement stmt = Connexion.getConnection().createStatement();
	   String sql = "select count(*) as count from emprunts where status = 'Borrowed' and id_abonne = '"+ idAbonne+"'" ;
	   ResultSet rset = stmt.executeQuery(sql);

	   while (rset.next()) {
		   cnt = rset.getInt("count");
	   }
	   rset.close();
	   stmt.close(); 

	
	   return cnt;
   
  }

  
  /**
   * Récupération de la liste complète des emprunts en cours.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt en cours.<br/>
   * Il doit contenir 8 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : id de l'abonné</li>
   *   <li>5 : nom de l'abonné</li>
   *   <li>6 : son prénom</li>
   *   <li>7 : la date de l'emprunt</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   * 
   * 
   * 
   */
  public static ArrayList<String[]> listeEmpruntsEnCours() throws SQLException {
    ArrayList<String[]> emprunts = new ArrayList<String[]>();

    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select e.idExemp, l.id,  l.titre, l.auteur, a.id_abonne ,a.Nom, a.Prenom, e.Date_emprunt from emprunts e  JOIN"
    		+ " Abonne a on e.id_abonne = a.id_abonne JOIN Exemplaire ex on e.idExemp = ex.idExemp  join livre l ON   l.id = ex.id  where e.status = 'Borrowed'";
    
    
    
    ResultSet rset = stmt.executeQuery(sql);

    while (rset.next()) {
      String[] emprunt = new String[8];
      emprunt[0] = rset.getString("idExemp");
      emprunt[1] = rset.getString("id");
      emprunt[2] = rset.getString("titre");
      emprunt[3] = rset.getString("auteur");
      emprunt[4] = rset.getString("id_abonne");
      emprunt[5] = rset.getString("nom");
      emprunt[6] = rset.getString("prenom");
      emprunt[7] = rset.getString("Date_emprunt");

      emprunts.add(emprunt);
    }
    rset.close();
    stmt.close();

 
    return emprunts;
  }

  /**
   * Récupération de la liste des emprunts en cours pour un abonné donné.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt en cours pour l'abonné.<br/>
   * Il doit contenir 5 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : la date de l'emprunt</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsEnCours(int idAbonne) throws SQLException {
    ArrayList<String[]> emprunts = new ArrayList<String[]>();

    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select e.idExemp, l.id,  l.titre, l.auteur, e.Date_emprunt from emprunts e  left JOIN Exemplaire ex on"
    		+ " e.idExemp = ex.idExemp  left join livre l ON   l.id = ex.id  where e.status = 'Borrowed' and e.id_abonne = '"+idAbonne+"'" ;
    
    ResultSet rset = stmt.executeQuery(sql);

    while (rset.next()) {
      String[] emprunt = new String[5];
      emprunt[0] = rset.getString("idExemp");
      emprunt[1] = rset.getString("id");
      emprunt[2] = rset.getString("titre");
      emprunt[3] = rset.getString("auteur");
      emprunt[4] = rset.getString("Date_emprunt");

      emprunts.add(emprunt);
    }
    rset.close();
    stmt.close();
    return emprunts;
  }

  /**,'"+isbn13+"'
   * Récupération de la liste complète des emprunts passés.
   * 
   * @return un <code>ArrayList<String[]></code>nt[. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt passé.<br/>
   * Il doit contenir 9 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : id de l'abonné</li>
   *   <li>5 : nom de l'abonné</li>
   *   <li>6 : son prénom</li>
   *   <li>7 : la date de l'emprunt</li>
   *   <li>8 : la date de retour</li>
   * </ul>
   * @return un <code>ArrayList</code> contenant autant de tableaux de String (5 chaînes de caractères) que d'emprunts dans la base.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsHistorique() throws SQLException {
    ArrayList<String[]> emprunts = new ArrayList<String[]>();
    
    
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select e.idExemp, l.id,  l.titre, l.auteur, a.id_abonne ,a.Nom, a.Prenom, e.Date_emprunt,e.Date_retourne from emprunts e  JOIN Abonne a "
    		+ "on e.id_abonne = a.id_abonne JOIN Exemplaire ex on e.idExemp = ex.idExemp  join livre l ON   l.id = ex.id  where e.status = 'Returned' ";
    
    
    
    ResultSet rset = stmt.executeQuery(sql);

    while (rset.next()) {
      String[] emprunt = new String[9];
      emprunt[0] = rset.getString("idExemp");
      emprunt[1] = rset.getString("id");
      emprunt[2] = rset.getString("titre");
      emprunt[3] = rset.getString("auteur");
      emprunt[4] = rset.getString("id_abonne");
      emprunt[5] = rset.getString("nom");
      emprunt[6] = rset.getString("prenom");
      emprunt[7] = rset.getString("Date_emprunt");
      emprunt[8] = rset.getString("Date_retourne");

      emprunts.add(emprunt);
    }
    rset.close();
    stmt.close();
    
    return emprunts;
  }
  public static boolean borrowed(int idAbonne, int idExemplaire) throws SQLException {
	  boolean borrowed = false;
	  
	  Statement stmt = Connexion.getConnection().createStatement();
		String sql = "select count(*) from emprunts where idExemp ='"+idExemplaire+"' and  id_abonne = '"+idAbonne+"' and date_retourne is Null";
		ResultSet rset = stmt.executeQuery(sql);
		rset.next();
		int countResult = rset.getInt("count");
		
		if(countResult != 0){
			
			borrowed = true;
		}
		
		return borrowed ; 
	  
	  
  }
  /**
   * Emprunter un exemplaire à partir de l'identifiant de l'abonné et de
   * l'identifiant de l'exemplaire.
   * 
   * @param idAbonne : id de l'abonné emprunteur.
   * @param idExemplaire id de l'exemplaire emprunté.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void emprunter(int idAbonne, int idExemplaire) throws SQLException {

	 
	  Statement stmt = Connexion.getConnection().createStatement();
			
			String insert = "insert into emprunts values( '"+idExemplaire+"','"+idAbonne+"')";
			stmt.executeUpdate(insert);
		
		stmt.close();
  }

  /**
   * Retourner un exemplaire à partir de son identifiant.
   * 
   * @param idExemplaire id de l'exemplaire à rendre.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void rendre(int idExemplaire) throws SQLException {
	  
	  
	  Statement stmt = Connexion.getConnection().createStatement();
		
		String update = "update emprunts  set  Date_retourne = now(), status = 'Returned'  where status = 'Borrowed' and idExemp ='"+idExemplaire+"'";
		stmt.executeUpdate(update);
		stmt.close();

  }
  
  /**
   * Détermine si un exemplaire sonné connu par son identifiant est
   * actuellement emprunté.
   * 
   * @param idExemplaire
   * @return <code>true</code> si l'exemplaire est emprunté, <code>false</code> sinon
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static boolean estEmprunte(int idExemplaire) throws SQLException {
    boolean estEmprunte = false;
    
    
    
    
    Statement stmt = Connexion.getConnection().createStatement();
	  String sql = "select count(*) as count from emprunts where status = 'Borrowed'  and  idExemp ='"+idExemplaire+"'";
	  ResultSet rset = stmt.executeQuery(sql);

	  while (rset.next()) {
		  int cnt = 0;
		  cnt = rset.getInt("count");
		  if (cnt != 0){ estEmprunte = true;}
	  }
	  rset.close();
	  stmt.close();
    return estEmprunte;
  }

  /**
   * Récupération des statistiques sur les emprunts (nombre d'emprunts et de
   * retours par jour).
   * 
   * @return un <code>HashMap<String, int[]></code>. Chaque enregistrement de la
   * structure de données est identifiée par la date (la clé) exprimée sous la forme
   * d'une chaîne de caractères. La valeur est un tableau de 2 entiers qui représentent :
   * <ul>
   *   <li>0 : le nombre d'emprunts</li>
   *   <li>1 : le nombre de retours</li>
   * </ul>
   * Exemple :
   * <pre>
   * +-------------------------+
   * | "2017-04-01" --> [3, 1] |
   * | "2017-04-02" --> [0, 1] |
   * | "2017-04-07" --> [5, 9] |
   * +-------------------------+
   * </pre>
   *   
   * @throws SQLException
   */
  public static HashMap<String, int[]> statsEmprunts() throws SQLException
  {
	  HashMap<String, int[]> stats = new HashMap<String, int[]>();

	  ArrayList<String[]> emprunts = new ArrayList<String[]>();
	  ArrayList<String[]> retournes = new ArrayList<String[]>();

	  Statement stmt1 = Connexion.getConnection().createStatement();

	  String sql1 = "select date_emprunt:: date,count(*) as count from emprunts where status = 'Borrowed' group by date_emprunt:: date";
	  ResultSet rset1 = stmt1.executeQuery(sql1);

	  while (rset1.next()) {
		  String[] emprunt = new String[2];
		  emprunt[0] = rset1.getString("date_emprunt");
		  emprunt[1] = rset1.getString("count");	
		  emprunts.add(emprunt);
	  }
	  rset1.close();

	  stmt1.close();

	  Statement stmt2 = Connexion.getConnection().createStatement();

	  String sql2 = "select date_retourne:: date,count(*) as count from emprunts where status = 'Returned' group by date_retourne:: date";
	  ResultSet rset2 = stmt2.executeQuery(sql2);

	  while (rset2.next()) {
		  String[] retourne = new String[2];
		  retourne[0] = rset2.getString("date_retourne");
		  retourne[1] = rset2.getString("count");	
		  retournes.add(retourne);
	  }
	  rset2.close();
	  stmt2.close();
	 
	  for (String[] s_b : emprunts){ 
		  int[] track = new int[2];
		  track[0] = Integer.valueOf(s_b[1]);
		  for (String[] s_r : retournes){
			  System.out.print(s_b[0]);
			  System.out.print(s_b[0].compareTo(s_r[0]));
			  if (s_b[0].compareTo(s_r[0])==0){
				  
				  track[1] = Integer.valueOf(s_r[1]);
				  stats.put(s_b[0], track);
			  }
			  else{
				  track[1] = 0;
				  stats.put(s_b[0], track);
			  }
		  }
	  }	  
	  for (String[] s_r : retournes){ 
		  int[] track = new int[2];
		  track[1] = Integer.valueOf(s_r[1]);
		  boolean trigger =  false;
		  for (String[] s_b : emprunts){
			  if (s_b[0].compareTo(s_r[0])==0){
				  trigger = true;	  
			  }
		  }
		  if (trigger == false){
		  track[0] = 0;
		  stats.put(s_r[0], track);
		  }
	  }
	  return stats;
  }
}
