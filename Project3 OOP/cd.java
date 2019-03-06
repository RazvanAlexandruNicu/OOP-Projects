/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Razvan-Alexandru Nicu 323CB
 */
public class cd implements Command{

  private static cd single_instance = null;
  //reference to the light
  Directory currentPath;
  Directory root;
  String fullPath;
  Directory finalDestination;
  
  private cd(){
  }

    /**
     * Singleton - returneaza instanta unica
     * @return unica instanta
     */
    public static cd getInstance() 
  { 
    if (single_instance == null) 
        single_instance = new cd(); 
  
    return single_instance; 
  } 
  
    /**
     * Functie care primeste parametrii comenzii si ii salveaza
     * @param root radacina sistemului de fisiere
     * @param currentPath directorul curent 
     * @param fullPath calea asupra careia trebuie sa executam comanda
     */
    public void takeParams(Directory root, Directory currentPath, String fullPath)
  {
      this.currentPath = currentPath;
      this.root = root;
      this.fullPath = fullPath;             
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
        //
  }
  

}
