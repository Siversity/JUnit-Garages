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
                }
	}

	/**
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
                // On crée une liste vide ne pouvant pas contenir de doublons
                Set<Garage> garages = new HashSet<>();
                // On rajoute les garages sauvegardes dans myStationnements
		for (int i = 0; i < myStationnements.size() - 1; i++) {
                    garages.add(myStationnements.get(i).getGarage());
                }
                return garages;
	}

	/**
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
                // On cherche le dernier stationnement
                int taille = myStationnements.size();
                // On verifie si le dernier stationnement est en cours ou si la liste de stationnements est vide
                if ((myStationnements.get(taille - 1).getFin() == null) || (taille == 0)) {
                    return true;
                }
                else {
                    return false;
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
                int taille1 = myStationnements.size();
                int taille2 = this.garagesVisites().size();
                // On parcours la liste des garages visites
                for (int i = 0; i < taille2 - 1; i++) {
                Iterator<Garage> it = this.garagesVisites().iterator();
                while (it.hasNext()) {
                    Garage g = it.next();
                    System.out.println("Garage " + g.getName() + ":");
                    // On parcours la liste des stationnements visites et on verifie si c'est le garage que l'on souhaite afficher
                    for (int j = 0; i < taille1 - 1; i++) {
                        Stationnement s = myStationnements.get(j);
                        String sortie = null;
                        if (g.getName().equals(s.getGarage())) {
                            // On detecte si ce staionnement est en cours ou non
                            if (s.getFin() == null) {
                                sortie = "en cours";
                            }
                            else {
                                sortie = "sortie=" + s.getFin().toString();
                            }
                        String entree = "entree=" + s.getEntree();
                        // On affiche les infos des stationnements pour le garage en question
                        System.out.println("\t \t Stationnement{ " + entree + ", " + sortie + " }");
                        }
                    }
                }
            }
        }
}
