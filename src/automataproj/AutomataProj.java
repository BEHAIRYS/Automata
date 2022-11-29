/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automataproj;

import java.util.Scanner;

/**
 *
 * @author Hp
 */
public class AutomataProj {

    public AutomataProj() {
    }
    

     public void go()
    {
        System.out.println("FORMAT : \n"
            +">concatenation is done with a '.' operator \n"
            +"-> Enclose every concatenation and or section by parantheses in order to obtain functioning project \n"
            +"-> Enclose the entire regular expression with parantheses like (a*.b*)\n\n"); 
        String re;
        System.out.println("Enter the regular expression in the mentioned format");
        re=new Scanner(System.in).next();   
        NFA requiredNFA;
        Convertor convertor=new Convertor();
        requiredNFA =convertor.regexToNFA(re);
        requiredNFA.formalDescription();
         
       
    }
    public static void main(String[] args) {
        // TODO code application logic here
        AutomataProj proj=new AutomataProj();
        proj.go();
        
    }
    
}
