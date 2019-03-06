/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Razvan-Alexandru Nicu 323CB
 */
public class File implements AbstractFile, Comparable{
    private String name;
    private Directory parent;

    /**
     * Calea in sistem a fisierului curent
     */
    public String pathInSystem;

    /**
     * Clona fisierului curent
     */
    public File myClone;

    /**
     * Constructorul
     * @param name numele
     */
    public File(String name)
    {
        this.name = name;
    }

    /**
     * Returneaza referinta la parintele directorului curent
     * @return referinta la un director
     */
    @Override
    public Directory getParent()
    {
        return parent;
    }

    /**
     * Seteaza parintele directorului curent.
     * @param parent referinta la director.
     */
    @Override
    public void setParent(Directory parent)
    {
        this.parent = parent;
    }
    @Override
    public String toString()
    {
        return "Acesta este fisierul cu numele " + name;
    }    

    /**
     * Functie care returneaza numele documentului
     * @return numele
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Functia care realizeaza clonarea directorului curent, precum
     * si clonarea recursiva a continutului acestuia
     */
    @Override
    public void cloneThis()
    {
        this.myClone = new File(this.getName());       
    }
    
    /**
     * Seteaza calea curenta 
     * @param y string - cale
     */
    public void setPath(String y)
    {
        this.pathInSystem = y;
    }
    
    /**
     * 
     * @param nume
     * @return
     */
    @Override
    public int findChild(String nume) {
        return 0;
    }

    /**
     *
     * @param nume
     * @return
     */
    @Override
    public AbstractFile getChild(String nume) {
        return null;
    }

    /**
     * Functie care returneaza path-ul complet al AbstractFile-ului curent
     * @return calea
     */
    @Override
    public String getPath()
    {
         if(this.getParent() != null)
         {
             return this.getParent().pathInSystem + this.pathInSystem;
         }
         return "/";
    }
    
    /**
     * Functie care sterge referinta la clona existenta la acel moment
     */
    @Override
    public void deleteClone() {
        this.myClone = null;
    }

    /**
     *  Functie ce retunreaza clona obiectului curent.
     * @return campul myClone al AbstractFile-ului
     */
    @Override
    public AbstractFile getClone()
    {
        return this.myClone;
    }
        
    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((AbstractFile)o).getName());
    }

}
