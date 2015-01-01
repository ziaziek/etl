/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations.expressions.onp;

import com.przemo.etl.interfaces.IEvaluator;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Przemo
 */
public class ONPConverter {
    
    private static final Pattern p = Pattern.compile("(\\+)|(\\-)|(\\^)|(\\*)|(\\/)|(\\(|\\))|((sin|ln|cos|tan|substr)\\()|(\\,)");
        
    public static String code(String expr){
        expr = expr.replaceAll(" ", "");
        String ret = "";
        Stack<String> s = new Stack<>();
        Matcher m = p.matcher(expr);
        int currentPosition = 0;
        while(m.find()){
            ret+=expr.substring(currentPosition, m.start())+" ";
            currentPosition=m.end();
            //the symbol is a function, put it on the stack withiut the initial bracket
            if(m.group().length()>1){
                String w = m.group();
                if(w.contains("(")){
                    s.push(w.replaceAll("\\(", ""));
                }               
                s.push("(");
            } else {
                //the symbol is an operator
                switch (m.group()) {
                    case ",":if (!s.isEmpty()) {
                            while (!s.isEmpty() && !(s.peek()).equals("(")) {
                                ret += s.pop() + " ";
                            }
                        }
                        break;
                    case ")": 
                        if (!s.isEmpty()) {
                            String op;
                            while (!s.isEmpty() && !(op = s.pop()).equals("(")) {
                                ret += op + " ";
                            }
                        }
                        ;
                        break;
                    case "(":
                        s.push(m.group());
                        break;
                    default:
                        while (!s.isEmpty() && operatorImportance(m.group()) <= operatorImportance(s.peek())) {
                            String op = s.pop();
                            ret += op + " ";
                        }
                        s.push(m.group());
                        break;
                }
            }
                
        }
        //take what's left in the expresssion string
        if(currentPosition<expr.length()){
            ret+=expr.substring(currentPosition)+" ";
        }
        //clear the stack
        String stackString="";
        while(!s.isEmpty()){
        stackString=s.pop();
                ret+=stackString+" ";
        }
        return ret.replaceAll("\\s{2,}", " ").trim();
    }
    
        public static final Map<String, Integer> funcNoArgs = new HashMap<String, Integer>(){{
        put("sin", 1);
        put("cos", 1);
        put("tan", 1);
        put("ln", 1);
        put("substr", 3);
    }};
    
    public static Object decode(String expr, IEvaluator eval){
         String[] elements = expr.split(" ");
         Stack<Object> s = new Stack();
         for(int i=0; i<elements.length;i++){
             Double p;
             if(eval.isValue(elements[i])){
                 s.push(eval.getValue(elements[i]));
             } else {
                    //the element is either a function or operator
                    if(eval.isOperator(elements[i])){
                        //this is an operator
                        Object x=s.pop();
                        Object y = s.pop();
                        if(x instanceof Number && y instanceof Number){
                            s.push( evaluateNumbers(elements[i], (Double)y, (Double)x));
                        } else {
                            s.push( evaluateString(elements[i], String.valueOf(y), String.valueOf(x)));
                        }                        
                    } else if(eval.isFunction(elements[i])){
                        //this is a function
                        if(funcNoArgs.containsKey(elements[i])){
                           Object[] args = new Object[funcNoArgs.get(elements[i])];
                           for(int k=0; k< args.length;k++){
                               args[k]=s.pop();
                           }
                        s.push(evaluateFunction (elements[i], args));
                        } else {
                            return null;
                        }                       
                    }
                } 
             }
           return s.pop();
    }
    
    private static Object evaluateNumbers(String element, Double x, Double y) {
        switch(element){
                            case "+": return x+y;
                            case "-": return x-y;
                            case "*": return x*y;
                            case "/": return x/y;
                            case "^": return Math.pow(x, y);
                            default: return x;                              
                        }
    }

    private static Object evaluateString(String element, String a, String b) {
        switch(element){
            case "+": return a.concat(b);
            case "-": return a.replaceAll(b, "");
            default: return a;
        }
    }

    private static Object evaluateFunction(String element, Object[] args) {
        switch(element){
            case "sin": return Math.sin((double) args[0]);
            case "cos": return Math.cos((double) args[0]);
            case "tan": return Math.tan((double) args[0]);
            case "ln":return Math.log((double) args[0]);
            case "substr": return (String.valueOf(args[2]).substring(((Double)args[1]).intValue(), ((Double)args[0]).intValue()));
            default:
                return null;
        }
    }
       

    private static int operatorImportance(String o){
        int r=-1;
        switch(o){
            case "+": r=1;
                break;
            case "-": r=1;
            break;
            case "(": r=0;
                break;
            case ")": r=1;
                break;
            case "*": r=2;
                break;
            case "/": r=2;
                break;
            case "^": r=3;
                break;
        }
        return r;
    }
    
    
}
