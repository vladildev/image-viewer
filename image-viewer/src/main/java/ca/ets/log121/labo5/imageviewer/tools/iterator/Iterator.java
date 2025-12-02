package ca.ets.log121.labo5.imageviewer.tools.iterator;

/**
 * Interface pour le patron Iterator bidirectionnel.
 * <p>
 * Cette interface définit le contrat pour un itérateur qui peut parcourir
 * une collection dans les deux sens (avant et arrière). Elle est utilisée
 * notamment pour parcourir l'historique des commandes.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 */
public interface Iterator {
    /**
     * Vérifie s'il existe un élément suivant dans la collection.
     * 
     * @return {@code true} s'il existe un élément suivant
     */
    boolean hasNext();

    /**
     * Vérifie s'il existe un élément précédent dans la collection.
     * 
     * @return {@code true} s'il existe un élément précédent
     */
    boolean hasPrevious();

    /**
     * Retourne l'élément précédent et déplace l'itérateur vers l'arrière.
     * 
     * @return l'élément précédent, ou {@code null} s'il n'y en a pas
     */
    Object previous();

    /**
     * Retourne l'élément suivant et déplace l'itérateur vers l'avant.
     * 
     * @return l'élément suivant, ou {@code null} s'il n'y en a pas
     */
    Object next();

    /**
     * Retourne l'index actuel de l'itérateur.
     * 
     * @return l'index actuel
     */
    int getIndex();
}
