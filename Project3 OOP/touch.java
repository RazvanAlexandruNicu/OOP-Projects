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
public class touch implements Command{

    private String fullPath;
    private Directory currentPath;
    private Directory root;
    private Directory placeToInsert;
    private String name;
    private static touch single_instance = null;
    int iterate;
    String[] vector;
    StringBuilder parentPath;

    /**
     *
     */
    public int caleAbsoluta = 0;
    PrintWriter error;
  //reference to the light
     
    
    private touch(){
    }
    
    /**
     * returneaza instanta unica
     * @return
     */
    public static touch getInstance() 
    { 
      if (single_instance == null) 
          single_instance = new touch(); 

      return single_instance; 
    } 
    
    /**
     * Functie care primeste parametrii comenzii si ii salveaza
     * @param root radacina sistemului de fisiere
     * @param currentPath directorul curent 
     * @param fullPath calea fisierului folosit in comanda
     * @param error fisierul de eroare
     */
    public void takeParams(Directory root, Directory currentPath, String fullPath, PrintWriter error)
    {
        this.fullPath = fullPath;
        this.root = root;
        this.currentPath = currentPath;
        this.error = error;
    }
    
    /**
     * Functie care analizeaza Path-ul primit ca input
     * @param fullPath calea primita de comanda
     * @return 0/1 daca este o cale valida.
     */
    public int analyzePath(String fullPath)
    {
        
        if(fullPath.charAt(0) == '/')
        {
            caleAbsoluta = 1;
            Directory formPath = root;
            vector = fullPath.split("/");
            parentPath = new StringBuilder();
            for(iterate = 0; iterate < vector.length -1; iterate++)
            {
                if(iterate == 0)
                {
                    parentPath.append(vector[iterate]);
                }
                else
                {
                    parentPath.append("/").append(vector[iterate]);
                }
            }
            
            int sw = 1;
            for(iterate = 0; iterate < vector.length -1; iterate++)
            {
                if(vector[iterate].compareTo("") != 0)
                {
                    if(vector[iterate].compareTo(".") == 0) // stau pe loc
                    {
                        // nu fac nimic
                    }
                    else if( vector[iterate].compareTo("..") == 0) // merg in spate
                    {
                        if(formPath.getParent() != null)
                        {
                           formPath = formPath.getParent(); 
                        }
                        else
                        {
                            sw = 0;
                            break;
                        }
                    }
                    else if(formPath.findChild(vector[iterate]) == 1) // merg in continuare
                    {
                        if(((Directory)formPath).getChild(vector[iterate]) instanceof Directory)
                        {
                           formPath = (Directory)formPath.getChild(vector[iterate]); 
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
                return 0;
            }
            if(sw == 1) // am gasit locul
            {
                placeToInsert = formPath;
                name = vector[iterate];
                return 1;
            }
            else
            {
                return 0;
            }
            
        }
        else
        {       
            vector = fullPath.split("/");
            Directory formPath = currentPath;
            
            parentPath = new StringBuilder();
            for(iterate = 0; iterate < vector.length -1; iterate++)
            {
                if(iterate == 0)
                {
                    parentPath.append(vector[iterate]);
                }
                else
                {
                    parentPath.append("/").append(vector[iterate]);
                }              
            }
            
            int sw = 1;
            for(iterate = 0; iterate < vector.length -1; iterate++)
            {
                if(vector[iterate].compareTo(".") == 0) // stau pe loc
                {
                    // nu fac nimic
                }
                else if( vector[iterate].compareTo("..") == 0) // merg in spate
                {                   
                    if(formPath.getParent() != null)
                    {
                        formPath = formPath.getParent(); 
                    }
                    else
                    {
                        sw = 0;
                        break;
                    }
                }
                else if(formPath.findChild(vector[iterate]) == 1) // merg in continuare
                {
                    if(((Directory)formPath).getChild(vector[iterate]) instanceof Directory)
                    {
                        formPath = (Directory)formPath.getChild(vector[iterate]); 
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
                return 0;
            }
            if(vector.length == 1 && vector[0].compareTo("..") == 0)
            {
                return 0;             
            }
            if(sw == 1) // am gasit locul
            {
                placeToInsert = formPath;
                name = vector[iterate];
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
    public void execute() {
        if(analyzePath(this.fullPath) == 1)
        {
            if(placeToInsert.findChild(name) == 0)
            {
               placeToInsert.touch(name); 
            }
            else
            {
                if(placeToInsert.getChild(name).getParent() == root)
                {
                    error.println("touch: cannot create file "+placeToInsert.getChild(name).getParent().getPath()+placeToInsert.getChild(name).getName()+": Node exists"); 
                }
                else
                {
                    error.println("touch: cannot create file "+placeToInsert.getChild(name).getParent().getPath()+"/"+placeToInsert.getChild(name).getName()+": Node exists"); 
                }
                        
                              
            }
        }
        else
        {
            error.println("touch: "+parentPath+": No such directory");
        }       
    }
}
    

