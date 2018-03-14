/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author Arne
 */
public interface iGroepsBewerking {
    String geefBewerkingToString();
    boolean uitkomstIsGeldig(Oefening oefening, double getal);
}
