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
public class pwd implements Command{

  private static pwd single_instance = null;
  //reference to the light
  AbstractFile file;
  PrintWriter output;
  
  private pwd(){
  }

    /**
     * returneaza instanta unica
     * @return
     */
    public static pwd getInstance() 
  { 
    if (single_instance == null) 
        single_instance = new pwd(); 
  
    return single_instance; 
  } 
  
    /**
     * Functie care primeste parametrii comenzii si ii salveaza
     * @param file fisierul de output
     * @param output fisierul de eroare
     */
    public void takeParams(Directory file, PrintWriter output)
  {
      this.file = file;
      this.output = output;
  }

    /**
     * Functie de execute. Design pattern- command
     */
    @Override
  public void execute()
  {
    String path =  file.getPath();
    output.println(path);
  }

}
