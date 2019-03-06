/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Razvan-Alexandru Nicu 323CB
 */
public class CommandFactory {
    
    /**
     * Functie care creeaza comanda corespunzatoare Stringului dat
     * ca parametru si retunreaza aceasta comanda
     * @param CommandType String cu numele comenzii
     * @return comanda.
     */
    public Command getCommand(String CommandType){
      if(CommandType == null){
         return null;
      }		
      if(CommandType.equalsIgnoreCase("ls"))
      {
         return ls.getInstance();
      } else if(CommandType.equalsIgnoreCase("pwd"))
      {
         return pwd.getInstance();     
      } else if(CommandType.equalsIgnoreCase("cd"))
      {
         return cd.getInstance();
      } else if(CommandType.equalsIgnoreCase("cp"))
      {
         return cp.getInstance();
      } else if(CommandType.equalsIgnoreCase("mkdir"))
      {
         return mkdir.getInstance();
      } else if(CommandType.equalsIgnoreCase("touch"))
      {
         return touch.getInstance();
      } else if(CommandType.equalsIgnoreCase("rm"))
      {
         return rm.getInstance();
      } else if(CommandType.equalsIgnoreCase("mv"))
      {
         return mv.getInstance();
      }  
      return null;
   }
    
}
