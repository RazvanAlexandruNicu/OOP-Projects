/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Razvan-Alexandru Nicu 323CB
 */
public interface AbstractFile extends Comparable{

    /**
     * Functie care seteaza parintele unui AbstractFile 
     * @param parent - noul parinte
     */
    public void setParent(Directory parent);

    /**
     * Functie care returneaza numele unui AbstractFile
     * @return numele
     */
    public String getName();

    /**
     * Functie care cauta in ArrayListul cu fisiere incluse
     * AbstractFile cu numele nume si returneaza 0/1 daca
     * acesta exista
     * @param nume
     * @return 1/0 gasit/negasit
     */
    public int findChild(String nume);

    /**
     * Functie care returneaza AbstractFile-ul cu numele nume
     * prezent in ArrayListul de fisiere incluse al AbstractFile-ului
     * curent
     * @param nume
     * @return AbstractFile cu numele nume
     */
    public AbstractFile getChild(String nume);

    /**
     * Functie care returneaza parintele AbstractFile-ului curent
     * @return referinta catre parinte
     */
    public Directory getParent();

    /**
     * Functie care returneaza path-ul complet al AbstractFile-ului curent
     * @return path
     */
    public String getPath();
    
    @Override
    public int compareTo(Object o);

    /**
     * Functie care realizeaza clonarea AbstractFile-ului curent si salvarea
     * acesteia in campul myClone
     */
    public void cloneThis();

    /**
     * Functie care sterge referinta la clona existenta la acel moment
     */
    public void deleteClone();

    /**
     * Functie ce retunreaza clona obiectului curent.
     * @return campul myClone al AbstractFile-ului
     */
    public AbstractFile getClone();
}
