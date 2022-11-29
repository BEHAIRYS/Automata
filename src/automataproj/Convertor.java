/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automataproj;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Hp
 */
public class Convertor {
    ArrayList<Character> operandsHelper=new ArrayList<Character>();
    NFA regexToNFA(String re) 
    {
        Stack<Character> operators=new Stack<>();
        Stack<NFA> operands=new Stack<>();
         
        char opSym;
        int opCount;
        char current;
        NFA newSym;
        char x[]=re.toCharArray();
        for(int i=0;i<x.length;i++) {
            current = x[i];
            if(current != '(' && current != ')' && current != '*' && current != '|' && current != '.') //Must be a character
            {
                newSym = new NFA();
                newSym.setState(2);
                newSym.setTransition(0, 1, current);
                newSym.setFinalState(1);
                operands.push(newSym);//push it back
                if(!operandsHelper.contains(current))
                    operandsHelper.add(current);
            } else {
                switch (current) {
                    case '*':
                        NFA kleene = operands.pop();
                        operands.push(kleene.kleene(kleene));
                        break;
                    case '.':
                        operators.push(current);
                        break;
                    case '|':
                        operators.push(current);
                        break;
                    case '(':
                        operators.push(current);
                        break;
                    default:
                        opCount = 0;
                        char c;
                        opSym = operators.peek();//See which symbol is on top
                        if(opSym == '(')
                            continue;//Keep searching operands
                        do {
                            operators.pop();
                            opCount++;
                        } while(operators.peek() != '(');//Collect operands
                        operators.pop();
                        NFA op1;
                        NFA op2;
                        NFA result=new NFA();
                        ArrayList<NFA> selections=new ArrayList<>();
                        if(opSym == '.') {
                            for(int ii = 0; ii < opCount; ii++) {
                                op2 = operands.pop();
                                op1 = operands.pop();
                                operands.push(result.concat(op1, op2));//Concatenate and add back
                            }
                        } else if(opSym == '|'){
                            for(int j=0;j<opCount+1;j++)
                                selections.add(new NFA());
                            int tracker = opCount;
                            for(int k = 0; k < opCount + 1; k++) {
                                selections.set(tracker,operands.pop());
                                tracker--;
                            }
                            operands.push(result.orSelection(selections, opCount+1));
                        }   break;  
                }
            }
        }
        
        System.out.println("Language Symbols are:"+ operandsHelper);
        
       
        return operands.peek();
        
    }
    
}
