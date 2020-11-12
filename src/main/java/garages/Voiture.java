package garages;

import java.io.PrintStream;
import java.util.*;

public class Voiture {

	private final String immatriculation;
	private final List<Stationnement> myStationnements = new LinkedList<>();

	public Voiture(String i) {
		if (null == i) {
			throw new IllegalArgumentException("Une voiture doit avoir une immatriculation");
		}

		immatriculation = i;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	/**
	 * Fait rentrer la voiture au garage 
         * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws java.lang.Exception Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws Exception {
                // On detecte si la voiture est dans un garage
		if (this.estDansUnGarage() == true) {
                    throw new UnsupportedOperationException("La voiture est déjà dans un autre garage");
                }
                else {
                    Stationnement s = new Stationnement(this, g);
                    myStationnements.add(s);
                }
	}

	/**
	 * Fait sortir la voiture du garage 
         * Précondition : la voiture doit être dans un garage
	 *
	 * @throws java.lang.Exception si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws Exception {
                // On verifie si la voiture est dans un garage
		if (this.estDansUnGarage() == false) {
                    throw new UnsupportedOperationException("La voiture n'est pas dans un garage");
                }
                else {
                    // On recupere le dernier stationnement en cours
                    int taille = myStationnements.size();
                    Stationnement s = myStationnements.get(taille - 1);
                    // On modifie le statut du dernier stationnement
                    s.terminer();
                    System.out.println(s.getFin());
                }
	}

	/**
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
                // On crée une liste vide ne pouvant pas contenir de doublons
                Set<Garage> garages = new HashSet<>();
                // On rajoute les garages sauvegardes dans myStationnements
		for (int i = 0; i < myStationnements.size(); i++) {
                    garages.add(myStationnements.get(i).getGarage());
                }
                return garages;
	}

	/**
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
                // On cherche le dernier stationnement
                int taille = 0 + myStationnements.size();
                if ((taille ==0) || (myStationnements.get(taille - 1).getFin() != null)) {
                    return false;
                }
                else {
                    return true;
                }
	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des dates d'entrée / sortie dans ce
	 * garage
	 * <br>Exemple :
	 * <pre>
	 * Garage Castres:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 *		Stationnement{ entree=28/01/2019, en cours }
	 *  Garage Albi:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out)
	 */
	public void imprimeStationnements(PrintStream out) {
                int nbrStationnements = myStationnements.size();
                // On parcours la liste des garages visites
                Iterator<Garage> it = this.garagesVisites().iterator();
                while (it.hasNext()) {
                    Garage g = it.next();
                    System.out.println(g.toString() + " :");
                    for (int i = 0; i < nbrStationnements; i++) {
                        Stationnement s = myStationnements.get(i);
                        String entree = "entree=";
                        String sortie = null;
                        if (s.getGarage().equals(g)) {
                            entree = entree + s.getEntree().toString();
                            if (s.estEnCours() == true) {
                                sortie = "en cours";
                            }
                            else {
                                sortie = "sortie=" + s.getFin();
                            }
                            System.out.println("        Stationnement{ " + entree + ", " + sortie + " }");
                        }   
                    }
                }
        }
        
}
