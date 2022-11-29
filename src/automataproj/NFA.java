/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automataproj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Hp
 */
public class NFA {
        ArrayList<Integer> states=new ArrayList<>();
        ArrayList<Transitions> transitions=new ArrayList<>();
        int finalState;
        int getStateCount() { //Number of states
            return states.size();
        }
        void setState(int totalStates) { //Add state to the states array
            for(int i = 0; i < totalStates; i++) {
                states.add(i);
            }
        }
        void setTransition(int stateFrom, int stateTo, char symbol) { // Add new transition
            Transitions transition=new Transitions(stateFrom,stateTo,symbol);
            transitions.add(transition);
        }
        void setFinalState(int fs) { //assign state as final state
            finalState = fs;
        }
        int getFinalState() { // retrieve the final state
            return finalState;
        }
        void formalDescription() { // formal description to define the NFA as required in the project
        System.out.println("A DFA is a 5-tuple, M = (Q, ∑, S, q0\n" +
        ", F):");
        System.out.println("set of States 'Qs' in our system:\n");
        for(int i=0; i<this.getStateCount();i++){
            System.out.println("State: Q"+i);
        }
        int startState = this.states.get(0);
        System.out.println("Start state (q0) is state "+ startState);
        System.out.println("\nThe required NFA has the transitions: ");
            for(int i = 0; i < transitions.size(); i++) {
                Transitions temp = transitions.get(i);
                System.out.println("transition from state "+temp.stateFrom+" to state "
                                   +temp.stateTo+" with Symbol: "+temp.symbol);
                if(i == (transitions.size()-1))
                    System.out.println("The final state is state "+finalState);
                    
            }
            
        }
        NFA concat(NFA a, NFA b) // concatination of 2 symbols
    {
        NFA result=new NFA();
        result.setState(a.getStateCount() + b.getStateCount());//No new state added in concatenation
        int i;
        Transitions newT;
        for(i = 0; i < a.transitions.size(); i++) {
            newT = a.transitions.get(i);
            result.setTransition(newT.stateFrom, newT.stateTo, newT.symbol);//Copy old transitions
        }
        result.setTransition(a.getFinalState(), a.getStateCount(), '^');//Creating the link; final state of `a` will link to initial state of `b`
        for(i = 0; i < b.transitions.size(); i++) {
            newT = b.transitions.get(i);
            result.setTransition(newT.stateFrom + a.getStateCount(), newT.stateTo + a.getStateCount(), newT.symbol);//Copy old transitions wit offset as a's vertices have already been added
        }
        result.setFinalState(a.getStateCount() + b.getStateCount() - 1);//Mark b's final as final in new one too
        return result;
    }
    NFA kleene(NFA a) // implement the kleene star for NFA state
    {
        NFA result=new NFA();
        int i;
        Transitions new_trans;
        result.setState(a.getStateCount() + 2);
        result.setTransition(0, 1, '^');//Epsilon transition from S0 to S1
        for(i = 0; i < a.transitions.size(); i++) {
            new_trans = a.transitions.get(i);
            result.setTransition(new_trans.stateFrom + 1, new_trans.stateTo + 1, new_trans.symbol);//Copy old transitions
        }
        result.setTransition(a.getStateCount(), a.getStateCount() + 1, '^');//Epsilon transition to new final state
        result.setTransition(a.getStateCount(), 1, '^');//Reverese epsilon transition
        result.setTransition(0, a.getStateCount() + 1, '^');//Forward total epsilon transition
        result.setFinalState(a.getStateCount() + 1);//Mark final state
        return result;
    }
    NFA orSelection(ArrayList<NFA> options, int noOfOptions) //  selection between different options
    {
        NFA result=new NFA();
        int stateCount = 2;
        int i, j;
        NFA option;
        Transitions newT;
        for(i = 0; i < noOfOptions; i++) {
            stateCount += options.get(i).getStateCount();//Find total states by summing all NFAs
        }
        result.setState(stateCount);
        int linkingState = 1;
        for(i = 0; i < noOfOptions; i++) 
        {
            result.setTransition(0, linkingState, '^');//Initial epsilon transition to the first block of 'OR'
            option = options.get(i);
            for(j = 0; j < option.transitions.size(); j++) {
                newT = option.transitions.get(j);
                result.setTransition(newT.stateFrom + linkingState, newT.stateTo + linkingState, newT.symbol);//Copy all transitions in first NFA
            }
            linkingState += option.getStateCount();//Find how many states has been added
            result.setTransition(linkingState - 1, stateCount - 1, '^');//Add epsilon transition to final state
        }   
        result.setFinalState(stateCount - 1);//Mark final state
        return result;
    }
    }

    

