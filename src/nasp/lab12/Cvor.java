package nasp.lab12;

public class Cvor<Tip1 extends Comparable<Tip1>> implements Comparable<Cvor<Tip1>> {
    Tip1 element;
    boolean boja;
    Cvor<Tip1> lijevi;
    Cvor<Tip1> desni;
    Cvor<Tip1> roditelj;

    public Cvor() {

    }

    public Cvor(Tip1 element) {
        this.boja = false; //crvena
        this.element = element;
    }

    @Override
    public int compareTo(Cvor<Tip1> o) {
        return element.compareTo(o.element);
    }
}

