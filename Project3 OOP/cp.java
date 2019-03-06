/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.PrintWriter;

/**
 *
 * @author Razvan-Alexandru Nicu 323CB
 */
public class cp implements Command{

    private String fullPath;
    private Directory currentPath;
    private Directory root;
    private Directory placeToInsert;
    private static cp single_instance = null;
    String sourcePath;
    String destinationPath;
    AbstractFile finalDestination;
    AbstractFile source;
    AbstractFile destination;
    PrintWriter error;
     
    
    private cp(){
    }
    
    /**
     * Singleton - returneaza instanta unica
     * @return
     */
    public static cp getInstance() 
    { 
      if (single_instance == null) 
          single_instance = new cp(); 

      return single_instance; 
    } 
    
    /**
     * Functie care primeste parametrii comenzii si ii salveaza
     * @param root radacina sistemului de fisiere
     * @param currentPath directorul curent 
     * @param sourcePath calea fisierului sursa folosit in comanda
     * @param destinationPath calea fisierului destinatie folosit in comanda
     * @param error fisierul pentru outputul erorii
     */
    public void takeParams(Directory root, Directory currentPath, String sourcePath, String destinationPath, PrintWriter error)
    {
        this.root = root;
        this.currentPath = currentPath;
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
        this.error = error;
    }
    
    /**
     * Functie care analizeaza Path-ul primit ca input
     * @param fullPath calea primita de comanda
     * @return 0/1 daca este o cale valida.
     */
    
    public int analyzePath(String fullPath)
    {    
        finalDestination = null;
        if(fullPath.charAt(0) == '/')
        {
            AbstractFile current = root;
            String[] vector = fullPath.split("/");
            int sw = 1;
            int iterate;
            for(iterate = 0; iterate < vector.length - 1; iterate++)
            {
                if(vector[iterate].compareTo("") != 0)
                {
                    if(vector[iterate].compareTo(".") == 0) // stau pe loc
                    {
                    }
                    else if( vector[iterate].compareTo("..") == 0) // merg in spate
                    {
                        if(current.getParent() != null)
                        {
                           current = current.getParent(); 
                        }
                        else
                        {
                            sw = 0;
                            break;
                        }
                    }
                    else if(current.findChild(vector[iterate]) == 1) // merg in continuare
                    {
                        if(((Directory)current).getChild(vector[iterate]) instanceof Directory)
                        {
                            current = (Directory)current.getChild(vector[iterate]); 
                        }
                        else
                        {
                            sw = 0;
                            break;
                        }
                    }
                    else
                    {
                        sw = 0;
                        break;
                    }
                }                
            }
            if(vector.length == 0)
            {
                this.finalDestination = current;
                return 1;
            }
            // daca am ajuns aici inseamna ca am current = destinatie
            if(sw == 1) // am gasit locul
            {
                if(current instanceof Directory && current.findChild(vector[iterate]) == 1)
                {               
                    current = current.getChild(vector[iterate]); // daca contine ultimul => pointez catre el
                    this.finalDestination = current;
                    return 1;
                }                         
            }

            return 0;                      
        }
        else
        {
            finalDestination = null;
            String[] vector = fullPath.split("/");
            AbstractFile current = currentPath;
            int sw = 1;
            int iterate;
            for(iterate = 0; iterate < vector.length - 1; iterate++)
            {               
                if(vector[iterate].compareTo(".") == 0) // stau pe loc
                {
                }
                else if( vector[iterate].compareTo("..") == 0) // merg in spate
                {
                    if(current.getParent() != null)
                    {
                        current = current.getParent(); 
                    }
                    else
                    {
                        sw = 0;
                        break;
                    }
                }
                else if(current.findChild(vector[iterate]) == 1) // merg in continuare
                {
                    if(((Directory)current).getChild(vector[iterate]) instanceof Directory)
                    {
                        current = (Directory)current.getChild(vector[iterate]); 
                    }
                    else
                    {
                        sw = 0;
                        break;
                    }
                }
                else
                {
                    sw = 0;
                    break;
                }
            }
            if(vector.length == 1 && vector[0].compareTo(".") == 0)
            {
                this.finalDestination = current;
                return 1;
            }
            if(vector.length == 1 && vector[0].compareTo("..") == 0)
            {
                if(current.getParent() != null)
                {
                    current = current.getParent();
                    this.finalDestination = current;
                    return 1;
                }
                else
                {
                    return 0;
                }
                
            }
            if(sw == 1) // am gasit locul
            {
                if(vector[iterate].compareTo("..") == 0)
                {
                    current = current.getParent();
                    this.finalDestination = current;
                    return 1;
                }
                else if(vector[iterate].compareTo(".") == 0)
                {
                    this.finalDestination = current;
                    return 1;
                }
                else if(current instanceof Directory && current.findChild(vector[iterate]) == 1)
                {               
                    current = current.getChild(vector[iterate]); // daca contine ultimul => pointez catre el
                    this.finalDestination = current;
                    return 1;
                }                         
            }
            return 0;           
        }     
    }  

    /**
     * Functie de execute. Design pattern- command
     */
    @Override
    public void execute() {
        if(analyzePath(this.sourcePath) == 1)
        {
            this.source = this.finalDestination;
        }
        else
        {
            error.println("cp: cannot copy "+this.sourcePath+": No such file or directory");
            return;
        }
        if(analyzePath(this.destinationPath) == 1)
        {
            this.destination = this.finalDestination;
        }
        else
        {
            error.println("cp: cannot copy into "+this.destinationPath+": No such directory");
            return;
        }
        if(destination.findChild(source.getName()) == 1)
        {
            error.println("cp: cannot copy "+this.sourcePath+": Node exists at destination");
            return;
        }
        // am in source si destination referintele.
        source.deleteClone();
        source.cloneThis();
        ((Directory)destination).add(source.getClone());
    }
    
}
