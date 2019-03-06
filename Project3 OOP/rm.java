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
public class rm implements Command{

  private static rm single_instance = null;
  //reference to the light
  Directory currentPath;
  Directory root;
  String fullPath;
  Directory finalDestination;
  AbstractFile objectToRemove;
  PrintWriter error;
  
  private rm(){
  }

    /**
     * returneaza instanta unica
     * @return
     */
    public static rm getInstance() 
  { 
    if (single_instance == null) 
        single_instance = new rm(); 
  
    return single_instance; 
  } 
  
    /**
     * Functie care primeste parametrii comenzii si ii salveaza
     * @param root radacina sistemului de fisiere
     * @param currentPath directorul curent 
     * @param fullPath calea fisierului folosit in comanda
     * @param error fisierul de eroare
     */
    public void takeParams(Directory root, Directory currentPath, String fullPath,PrintWriter error)
  {
      this.currentPath = currentPath;
      this.root = root;
      this.fullPath = fullPath;
      this.error = error;
  }
  
    /**
     * Functie care analizeaza Path-ul primit ca input
     * @param fullPath calea primita de comanda
     * @return 0/1 daca este o cale valida.
     */
    public int analyzePath(String fullPath)
  {
        this.finalDestination = null;
        
        if(fullPath.charAt(0) == '/')
        {
            Directory parent = null;
            Directory current = root;
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
                        current = (Directory)current.getChild(vector[iterate]);
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
            if(sw == 1 && vector[1].compareTo("..") != 0 && vector[1].compareTo(".") != 0 && vector.length == 2)
            {
                if(current.findChild(vector[1]) == 1)
                {
                    objectToRemove = current.getChild(vector[1]);
                    this.finalDestination = current;
                    return 1;
                }
                return 0;
            }
                        
            if(sw == 1) // am gasit locul
            {
                this.finalDestination = current;
                if(this.finalDestination.findChild(vector[iterate]) == 1)
                {
                  this.objectToRemove = this.finalDestination.getChild(vector[iterate]);
                    return 1;  
                }
                else
                {
                    return 0;
                }
                
            }
            else
            {
                return 0;
            }
            
        }
        else
        {
            String[] vector = fullPath.split("/");
            Directory current = currentPath;
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
                    current = (Directory)current.getChild(vector[iterate]);
                }
                else
                {
                    sw = 0;
                    break;
                }
            }
            if(sw == 1 && vector[iterate].compareTo("..") == 0)
            {
                if(current.getParent() != null)
                {
                    current = current.getParent();
                    objectToRemove = current;
                    if(objectToRemove.getParent() != null)
                    {
                        this.finalDestination = objectToRemove.getParent();
                        return 1;
                    }
                    else
                    {
                        // nu pot sterge root
                        return 0;
                    }              
                }
                // nu pot sterge root
                return 0;
            }
            if(sw == 1 && vector[iterate].compareTo(".") == 0)
            {
            //    System.out.println("TREBUIE SA RAMAN PE LOC + CURRENT="+current);
                objectToRemove = current;
                if(objectToRemove.getParent() != null)
                {
                    this.finalDestination = objectToRemove.getParent();
                    return 1;
                }
                // nu pot sterge root
                return 0; 
            }
            if(sw == 1 && vector[0].compareTo("..") != 0 && vector[0].compareTo(".") != 0 && vector.length == 1)
            {
                if(current.findChild(vector[0]) == 1)
                {
                    objectToRemove = current.getChild(vector[0]);
                    this.finalDestination = current;
                    return 1;
                }
                return 0;
            }
            if(sw == 1) // am gasit locul
            {
                this.finalDestination = current;
                this.objectToRemove = this.finalDestination.getChild(vector[iterate]);
                return 1;
            }
            return 0;
        }     
    }  
  
    /**
     * Functie de execute. Design pattern- command
     */
    @Override
  public void execute()
  {
        if(this.analyzePath(fullPath) == 0)
        {
            error.println("rm: cannot remove "+this.fullPath+": No such file or directory");
        }
        else if(objectToRemove instanceof Directory && ((Directory)objectToRemove).findChildInSubtree(currentPath) == 1)
        {
        }
        else
        {
        //    System.out.println("Sterg "+objectToRemove);
            finalDestination.delete(objectToRemove);
        }
                    

  }
  

}
