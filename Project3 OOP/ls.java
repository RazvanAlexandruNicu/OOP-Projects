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
public class ls implements Command{

  private static ls single_instance = null;
  //reference to the light
  Directory currentPath;
  Directory root;
  String fullPath;
  Directory finalDestination;
  int isRecursive = 0;
  PrintWriter output; 
  PrintWriter error;
  
  private ls(){
  }

    /**
     * Singleton - returneaza instanta unica
     * @return
     */
    public static ls getInstance() 
  { 
    if (single_instance == null) 
        single_instance = new ls(); 
  
    return single_instance; 
  } 
  
    /**
     * Functie care primeste parametrii comenzii si ii salveaza
     * @param root radacina sistemului de fisiere
     * @param currentPath directorul curent 
     * @param fullPath calea fisierului folosit in comanda
     * @param isRecursive 1/0 recursiv/nerecursiv
     * @param output fisierul de output
     * @param error fisierul de eroare
     */
    public void takeParams(Directory root, Directory currentPath, String fullPath, int isRecursive, PrintWriter output, PrintWriter error)
  {
      this.currentPath = currentPath;
      this.root = root;
      this.fullPath = fullPath;
      this.isRecursive = isRecursive;
      this.output = output;
      this.error = error;
  }
  
    /**
     * Functie care analizeaza Path-ul primit ca input
     * @param fullPath calea primita de comanda
     * @return 0/1 daca este o cale valida.
     * @return
     */
    public int analyzePath(String fullPath)
  {
        this.finalDestination = null;
        
        if(fullPath.charAt(0) == '/')
        {
            Directory current = root;
            String[] vector = fullPath.split("/");
            int sw = 1;
            int iterate;
            for(iterate = 0; iterate < vector.length; iterate++)
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
            if(sw == 1) // am gasit locul
            {
                this.finalDestination = current;
                return 1;
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
            for(iterate = 0; iterate < vector.length; iterate++)
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
                this.finalDestination = current;
                return 1;
            }
            else
            {
                return 0;
            }
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
            error.println("ls: "+this.fullPath+": No such directory");
        }
        else
        {
            if(this.isRecursive == 1)
            {
                finalDestination.lsDepth(output);
            }
            else
            {
                finalDestination.ls(output);
            }
        }
  }
  

}
