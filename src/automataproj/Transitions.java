/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automataproj;

/**
 *
 * @author Hp
 */
public class Transitions {
        int stateFrom;
        int stateTo;
        char symbol;
        Transitions(int state_from, int state_to, char symbol)
        {
            this.stateFrom=state_from;
            this.stateTo=state_to;
            this.symbol=symbol;
        }
    
}
