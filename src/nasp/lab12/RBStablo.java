package nasp.lab12;

public class RBStablo<Tip1> {
    private Cvor korijen = null;
    private Cvor TNULL;

    public RBStablo() {
        TNULL = new Cvor();
        TNULL.boja = true;
        TNULL.lijevi = null;
        TNULL.desni = null;
        korijen = TNULL;
    }

    private void desniRotate(Cvor cvor) {
        Cvor roditelj = cvor.roditelj;
        Cvor lijevoDijete = cvor.lijevi;

        cvor.lijevi = lijevoDijete.desni;
        if (lijevoDijete.desni != TNULL) {
            lijevoDijete.desni.roditelj = cvor;
        }

        lijevoDijete.desni = cvor;
        cvor.roditelj = lijevoDijete;

        spojiRoditelja(roditelj, cvor, lijevoDijete);
    }

    private void lijeviRotate(Cvor cvor) {
        Cvor roditelj = cvor.roditelj;
        Cvor desnoDijete = cvor.desni;    // y = x.desni
        cvor.desni = desnoDijete.lijevi;  // x.desni = y.lijevi
        if (desnoDijete.lijevi != TNULL) {     // if y.lijevi != T.nil
            desnoDijete.lijevi.roditelj = cvor;     // y.lijevi.p = x
        }
        desnoDijete.lijevi = cvor; // y.lijevi = x
        cvor.roditelj = desnoDijete;//x.p = y

        spojiRoditelja(roditelj, cvor, desnoDijete);
    }

    private void spojiRoditelja(Cvor roditelj, Cvor staroDijete, Cvor novoDijete) { //RBtransplant
        if (roditelj == null || roditelj == TNULL) {     // if x.p == T.nil
            korijen = novoDijete;   // T.korijen = y
        } else if (roditelj.lijevi == staroDijete) { // elseif x == x.p.lijevi
            roditelj.lijevi = novoDijete;            //        x.p.lijevi = y
        } else if (roditelj.desni == staroDijete) {
            roditelj.desni = novoDijete;
        }
        if (novoDijete != null) {
            novoDijete.roditelj = roditelj; //y.p = x.p
        }
    }


    private void delete(Cvor cvor) {
        Cvor y = cvor;
        Cvor x = null; // x je cvor koji se pomjera
        boolean yOrgBoja = y.boja;
        if (cvor.lijevi == TNULL) {
            x = cvor.desni;
            spojiRoditelja(cvor.roditelj, cvor, cvor.desni);
        } else if (cvor.desni == TNULL) {
            x = cvor.lijevi;
            spojiRoditelja(cvor.roditelj, cvor, cvor.lijevi);
        } else {
            y = treeMinimum(cvor.desni);
            yOrgBoja = y.boja;
            x = y.desni;
            if (y.roditelj == cvor) x.roditelj = y; //x je NIL i crn je
            else {
                spojiRoditelja(y.roditelj, y, y.desni);
                y.desni = cvor.desni;
                y.desni.roditelj = y;
            }
            spojiRoditelja(cvor.roditelj, cvor, y);
            y.lijevi = cvor.lijevi;
            y.lijevi.roditelj = y;
            y.boja = cvor.boja;
        }
        if (yOrgBoja == true) {
            deleteFixUp(x);
        }
    }

    private void deleteFixUp(Cvor x) {
        Cvor w = null; // w je brat
        while (x != korijen && x.boja == true) {
            if (x == x.roditelj.lijevi) {
                w = x.roditelj.desni;
                if (w.boja == false) {
                    w.boja = true;
                    x.roditelj.boja = false;
                    lijeviRotate(x.roditelj);
                    w = x.roditelj.desni;
                }

                if (w.lijevi.boja == true && w.desni.boja == true) {
                    w.boja = false;
                    x = x.roditelj;
                } else {
                    if (w.desni.boja == true) {
                        w.lijevi.boja = true;
                        w.boja = false;
                        desniRotate(w);
                        w = x.roditelj.desni;
                    }
                    w.boja = x.roditelj.boja;
                    x.roditelj.boja = true;
                    w.desni.boja = true;
                    lijeviRotate(x.roditelj);
                    x = korijen;
                }
            } else {
                w = x.roditelj.lijevi;
                if (w.boja == false) {
                    w.boja = true;
                    x.roditelj.boja = false;
                    desniRotate(x.roditelj);
                    w = x.roditelj.lijevi;
                }

                if (w.lijevi.boja == true && w.desni.boja == true) {
                    w.boja = false;
                    x = x.roditelj;
                } else {
                    if (w.lijevi.boja == true) {
                        w.desni.boja = true;
                        w.boja = false;
                        lijeviRotate(w);
                        w = x.roditelj.lijevi;
                    }

                    w.boja = x.roditelj.boja;
                    x.roditelj.boja = true;
                    w.lijevi.boja = true;
                    desniRotate(x.roditelj);
                    x = korijen;
                }
            }
        }
        x.boja = true;
    }

    public void deleteKey(Tip1 element) {
        Cvor cvor = korijen;
        while (cvor != TNULL) {
            if (cvor.element.compareTo(element) == 0) {
                break;
            }
            if (cvor.element.compareTo(element) > 0) cvor = cvor.lijevi;
            else cvor = cvor.desni;
        }
        if (cvor == TNULL) {
            System.out.println("Traženi element ne postoji u stablu!\n");
            return;
        }
        delete(cvor);
    }

    private Cvor treeMinimum(Cvor cvor) {
        while (cvor.lijevi != TNULL) {
            cvor = cvor.lijevi;
        }

        return cvor;
    }

    public void insert(Cvor novi) {
        // roditelj je y
        // novi je z
        // cvor je x
        Cvor roditelj = null;
        novi.lijevi = TNULL;
        novi.desni = TNULL;
        novi.boja = false;
        Cvor cvor = this.korijen;
        while (cvor != TNULL) {
            roditelj = cvor;
            if (novi.compareTo(cvor) < 0) {
                cvor = cvor.lijevi;
            } else cvor = cvor.desni;
        }
        novi.roditelj = roditelj;
        if (roditelj == null) {
            this.korijen = novi;
            this.korijen.boja = true;
            return;
        } else if (novi.compareTo(roditelj) < 0) roditelj.lijevi = novi;
        else roditelj.desni = novi;
        insertFixUp(novi);

    }

    public void insertFixUp(Cvor novi) {
        Cvor ujak = null;
        while (novi.roditelj.boja == false) {
            if (novi.roditelj == novi.roditelj.roditelj.lijevi) {
                ujak = novi.roditelj.roditelj.desni; //ujak
                if (ujak.boja == false) {
                    novi.roditelj.boja = true;
                    ujak.boja = true;
                    novi.roditelj.roditelj.boja = false;
                    novi = novi.roditelj.roditelj;
                } else {
                    if (novi == novi.roditelj.desni) {
                        novi = novi.roditelj;
                        lijeviRotate(novi);
                    }
                    novi.roditelj.boja = true;
                    novi.roditelj.roditelj.boja = false;
                    desniRotate(novi.roditelj.roditelj);
                }
            } else {
                ujak = novi.roditelj.roditelj.lijevi;
                if (ujak.boja == false) {
                    novi.roditelj.boja = true;
                    ujak.boja = true;
                    novi.roditelj.roditelj.boja = false;
                    novi = novi.roditelj.roditelj;
                } else {
                    if (novi == novi.roditelj.lijevi) {
                        novi = novi.roditelj;
                        desniRotate(novi);
                    }
                    novi.roditelj.boja = true;
                    novi.roditelj.roditelj.boja = false;
                    lijeviRotate(novi.roditelj.roditelj);
                }
            }
            if (novi == korijen) {
                break;
            }

        }
        korijen.boja = true;
    }

    public void ispisInorder() {
        Inorder(this.korijen);
    }

    private void Inorder(Cvor cvor) {
        if (cvor != TNULL) {
            Inorder(cvor.lijevi);
            System.out.print(cvor.element + " ");
            Inorder(cvor.desni);
        }
    }

    public Cvor getKorijen() {
        return korijen;
    }
}
