/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Razvan-Alexandru Nicu 323CB
 */
public class Directory implements AbstractFile, Comparable {
    private final String name;
    private Directory parent;

    /**
     * Camp in care se stocheaza calea fisierului in sistemul de fisiere
     */
    public String pathInSystem;

    /**
     * ArrayList cu AbstractFiles incluse in director.
     */
    public final ArrayList<AbstractFile> includedFiles = new ArrayList<>();
    Directory myClone = null;
    //=====================
    
    /**
     * Constructor
     * @param name numele documentului
     */
    public Directory(String name)
    {
        this.name = name;
    }
    //======================

    /**
     * Functie care returneaza numele documentului
     * @return numele
     */
    @Override
    public String getName()
    {
        return this.name;
    }
    //======================
    
    /**
     * Functie care adauga obiectul primit in lista de fisiere incluse
     * @param obj obiectul care trebuie inserat
     */
    public void add(AbstractFile obj)
    {
        obj.setParent(this);
        this.includedFiles.add(obj);
        Collections.sort(this.includedFiles);
    }
    
    /**
     * Functie care sterge obiectul primit din lista de fisiere incluse
     * @param obj obiectul care trebuie sters
     */
    public void delete(AbstractFile obj)
    {
        if(this.includedFiles.contains(obj) == true)
        {
            this.includedFiles.remove(obj);
            Collections.sort(this.includedFiles);
        }
    }
    
    /**
     * Functia care realizeaza clonarea directorului curent, precum
     * si clonarea recursiva a continutului acestuia
     */
    @Override
    public void cloneThis()
    {
        this.myClone = new Directory(this.getName());       
        for(AbstractFile iterator : this.includedFiles)
        {
            if(iterator instanceof File)
            {
                File filanoua = new File(iterator.getName());
                this.myClone.add(filanoua);
            }
            else
            {
                ((Directory)iterator).cloneThis();
                this.myClone.add(((Directory)iterator).myClone);
            }
        }
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
     * Returneaza referinta la parintele directorului curent
     * @return referinta la un director
     */
    @Override
    public Directory getParent()
    {
        if(this.name.compareTo("root")==0)
        {
            return null;
        }
        else
        {
            return parent;
        }
    }
     //======================   

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
        return "Acesta este documentul cu numele " + name;
    }
    
    /**
     * Functie care returneaza AbstractFile-ul cu numele nume
     * prezent in ArrayListul de fisiere incluse al AbstractFile-ului
     * curent
     * @param nume numele AbstractFile ului
     * @return AbstractFile cu numele nume
     */
    @Override
    public int findChild(String nume)
    {
        for(AbstractFile iterator : this.includedFiles)
        {
            if(iterator.getName().compareTo(nume) == 0)
            {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Functie care returneaza path-ul complet al AbstractFile-ului curent
     * @param nume numele AbstractFile-ului
     * @return AbstractFile-ul cu numele nume inclus.
     */
    @Override
    public AbstractFile getChild(String nume) {
        for(AbstractFile iterator : this.includedFiles)
        {
            if(iterator.getName().compareTo(nume) == 0)
            {
                return iterator;
            }
        }
        return null;
    }
    
    /**
     * Functie care returneaza path-ul complet al AbstractFile-ului curent
     * @return calea
     */
    @Override
    public String getPath()
    {
        if(this.getParent() == null)
        {
            return "/";
        }
        else if(this.getParent().getName().compareTo("ROOT") == 0)
        {
            return "/"+this.getName();
        }
        return this.getParent().getPath()+"/"+this.getName();
    }
        
    /**
     * Functie care creeaza documentul cu numele name in directorul curent
     * @param name numele directorului de creat
     */
    public void mkdir(String name)
    {
        Directory newDirectory = new Directory(name);
        newDirectory.setParent(this);
        this.add(newDirectory);
    } 

    /**
     * Functie care creeaza fisierul cu numele name in directorul curent
     * @param name numele fisierului de creat
     */
    public void touch(String name) {
        File newFile = new File(name);
        newFile.setParent(this);
        this.add(newFile);
    }
    
    /**
     * functie care retunreaza 0/1 daca referinta se afla in subarborele
     * directorului curent (folosit pt operatia rm)
     * @param obj referinta catre AbstractFile
     * @return 0/1 negasit / gasit
     */
    public int findChildInSubtree(AbstractFile obj)
    {
        if(this.equals(obj) == true)
        {
            return 1;
        }
        for(AbstractFile iterator : this.includedFiles)
        {
            if(iterator.equals(obj) == true)
            {
            //    System.out.println(iterator.getName());
                return 1;
            }
        }
        for(AbstractFile file : this.includedFiles)
        {
            if(file instanceof Directory)
            {
                if(((Directory) file).findChildInSubtree(obj)==1)
                {
                    return 1;
                }
            }
        }
        return 0;
    }
        
    /**
     * Functie care afiseaza outputul comenzii ls in fisierul output
     * @param output fisierul de output
     */
    public void ls(PrintWriter output) {
        
        this.pathInSystem = this.getPath();
        output.println(this.pathInSystem+":");        
        if(this.includedFiles.size() != 0)
        {
            for(AbstractFile file : this.includedFiles)
            {
                if(this.getPath().compareTo("/") != 0)
                {
                    if(file.compareTo(this.includedFiles.get(this.includedFiles.size()-1)) == 0)
                    {
                     //   System.out.println("SUNT LA ULTIMUL");
                        output.print(this.getPath()+"/"+file.getName());
                    }
                    else
                    {
                        output.print(this.getPath()+"/"+file.getName()+" ");
                    }
                    
                }
                else
                {
                    if(file.compareTo(this.includedFiles.get(this.includedFiles.size()-1)) == 0)
                    {
                        output.print(this.getPath()+file.getName());
                    }
                    else
                    {
                        output.print(this.getPath()+file.getName()+" ");
                    }
                }
            }
            output.println("");
        }
        else
        {
            output.println("");
        }
        output.println("");

    }
    
    /**
     * Functie care afiseaza outputul comenzii ls recursiv in fisierul output
     * @param output fisierul de output
     */
    public void lsDepth(PrintWriter output) {      
        this.ls(output);
        for(AbstractFile file : this.includedFiles)
        {
            if(file instanceof Directory)
            {
                ((Directory) file).lsDepth(output);
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((AbstractFile)o).getName());
    }

    /**
     * Functie care sterge referinta la clona existenta la acel moment
     */
    @Override
    public void deleteClone() {
        this.myClone = null;
    }
    
    /**
     * Functie ce retunreaza clona obiectului curent.
     * @return campul myClone al AbstractFile-ului
     */
    @Override
    public AbstractFile getClone()
    {
        return this.myClone;
    }

}
