/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Razvan-Alexandru Nicu 323CB
 */
public class Main {
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {  
               
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line;
        
        FileWriter fileWriter1 = new FileWriter(args[1]);
        PrintWriter outputWriter = new PrintWriter(fileWriter1);
    
        FileWriter fileWriter2 = new FileWriter(args[2]);
        PrintWriter errorWriter = new PrintWriter(fileWriter2);
        
        Directory root = new Directory("ROOT");
        root.setPath("/");
        Directory currentPath = root;
        CommandFactory factory = new CommandFactory();
        int lineNumber = 0;
        
        while ((line = br.readLine()) != null)
        {
            lineNumber+=1;        
            errorWriter.println(lineNumber+"");
            outputWriter.println(lineNumber);
            
            Scanner scanner = new Scanner(line);
            String operatie = scanner.next();
            Command comanda = factory.getCommand(operatie);
            if(operatie.compareTo("pwd") == 0)
            {
                ((pwd)comanda).takeParams(currentPath, outputWriter);
                comanda.execute();
            }
            
            if(operatie.compareTo("cd") == 0)
            {
                String fullPath = scanner.next();
                ((cd)comanda).takeParams(root, currentPath, fullPath);
                if(((cd)comanda).analyzePath(fullPath) == 1)
                {
                    currentPath = ((cd)comanda).finalDestination;
                }
                else
                {
                    errorWriter.println("cd: "+fullPath+": No such directory");
                }
            }
            
            if(operatie.compareTo("mkdir") == 0)
            {
                String fullPath = scanner.next();
                ((mkdir)comanda).takeParams(root, currentPath, fullPath, errorWriter);
                comanda.execute();
            }
            if(operatie.compareTo("touch") == 0)
            {
                String fullPath = scanner.next();
                ((touch)comanda).takeParams(root, currentPath, fullPath, errorWriter);
                comanda.execute();
            }
            
            if(operatie.compareTo("ls") == 0)
            {
                int isRecursive = 0;
                String fullPath = "./";
                if(scanner.hasNext() == true)
                {
                    String item1 = scanner.next();
                    if(item1.compareTo("-R") == 0)
                    {
                        isRecursive = 1;                        
                        // am path-ul si daca este recursive sau nu                       
                    }
                    else
                    {
                        fullPath = item1;
                    }
                }
                if(scanner.hasNext() == true)
                {
                    String item1 = scanner.next();
                    if(item1.compareTo("-R") == 0)
                    {
                        isRecursive = 1;                        
                        // am path-ul si daca este recursive sau nu                       
                    }
                    else
                    {
                        fullPath = item1;
                    }
                }
                
                // fullPath - path  isRecursive - 0/1 daca este recursiv
                ((ls)comanda).takeParams(root, currentPath, fullPath, isRecursive, outputWriter, errorWriter);
                ((ls)comanda).execute();
            }
                  
            if(operatie.compareTo("cp") == 0)
            {
                
                String sourcePath = scanner.next();
                String destinationPath = scanner.next();
                ((cp)comanda).takeParams(root, currentPath, sourcePath, destinationPath, errorWriter);              
                comanda.execute();               
            }
            
            if(operatie.compareTo("mv") == 0)
            {            
                String sourcePath = scanner.next();
                String destinationPath = scanner.next();
                ((mv)comanda).takeParams(root, currentPath, sourcePath, destinationPath, errorWriter);              
                comanda.execute();               
            }
            
            if(operatie.compareTo("rm") == 0)
            {   
                String fullPath = scanner.next();
                ((rm)comanda).takeParams(root, currentPath, fullPath, errorWriter);              
                comanda.execute();               
            } 
            
            
        }
        outputWriter.close();
        errorWriter.close();
    }
}
