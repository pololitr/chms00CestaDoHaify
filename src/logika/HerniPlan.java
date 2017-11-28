package logika;

import java.util.ArrayList;
import java.util.List;

import utils.Observer;
import utils.Subject;


/**
 * Class HerniPlan - třída představující mapu a stav adventury.
 * 
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny prostory, propojuje je vzájemně pomocí východů
 * a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 * dopracoval Stepan Chmel chms00 30/12/2016
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha
 * @version    ZS 2016/2017
 */
public class HerniPlan implements Subject{

    //private static final String CILOVY_PROSTOR = "Haifa";
    // inicializace prostoru a batohu
    private Prostor aktualniProstor;
    private Batoh batoh;
    private Prostor let;
 
    //private Prostor kamenLET;
    //pomocne boolean promene pro pozdejsi uchovavani
    //informaci o plneni podminek
    private boolean litej = false;
    private boolean vom = false;
    private boolean mon = false;
    private boolean pas = false;
    private boolean vhr = false;
    private boolean letci = false;
    
    private List<Observer> listObserveru = new ArrayList<Observer>();
    

    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        zalozProstoryHry();
        this.batoh = new Batoh();
        
    }
    
    /**
     *  Vytváří jednotlivé prostory a postavy.
     *  Naplni pomocne promene let a kamenLet
     *  Vytvori pomocne pole mistnosti a postav
     *  Obsah poli vlozi do metod priradVychody() a zalozPrirad()
     *  Trida HerniPlan je takto rozdelna z duvodu spravnost vuci PMD
     *  Jako výchozí aktuální prostor nastaví Dubovec.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Prostor Dubovec = new Prostor("Dubovec","Dubovec, slovenské město, ve kterém pracujete", 30, 52);
        Prostor DilnaA = new Prostor("pobockaCKD","Pobočka ČKD v této filiálce ČKD pracujete",30,105);
        Prostor DilnaB = new Prostor("kancelar","Kancelář vašeho nadřízeného",30,170);
        Prostor NelCestaM = new Prostor("do_Madarska","Nelegální přechod nové maďarské hranice",113,52);
        Prostor VlakBelehrad = new Prostor("vlak_do_Belehradu","Vlak, který vás odveze do Bělehradu",200,52);
        Prostor Belehrad = new Prostor("Belehrad","Bělehrad",287,52);
        Prostor BudovaSpojka = new Prostor("misto_setkani","Zde se máte sejít s vaší bělehradskou spojkou",287,112);
        Prostor VlakSolun = new Prostor("vlak_do_Solune","Vlak, který vás odveze do Soluně",377,52);
        Prostor Solun = new Prostor("Solun","Solun",460,52);
        Prostor VlakIstanbul = new Prostor("vlak_do_Istanbulu","Vlak, který vás odveze do Istanbulu",460,232);
        Prostor Instanbul = new Prostor("Istanbul","Istanbul",349,232);
        Prostor KancelarDustojniku = new Prostor("zachytne_pracoviste","Kancelář bývalých československých důstojníků",349,290);
        Prostor Pristav = new Prostor("pristav","Přístaviště Mersin",271,232);
        Prostor CestaHaifa = new Prostor("do_Haify","Cesta lodí do Haify, lodi cestujete poprve\ntrpite morskou nemoci.Pouzijte tabletu, pokud ji mate v batohu ",197,232);
        Prostor Haifa = new Prostor("Haifa","Haifa, místo vašeho připojení k jednotce",117,232);
        //vztvoreni postav
        
        Dubovec.setVychod(DilnaA);
        Dubovec.setVychod(NelCestaM);
        DilnaA.setVychod(DilnaB);
        DilnaA.setVychod(Dubovec);      
        DilnaB.setVychod(DilnaA);       
        NelCestaM.setVychod(VlakBelehrad);        
        VlakBelehrad.setVychod(Belehrad);       
        Belehrad.setVychod(BudovaSpojka); 
        Belehrad.setVychod(VlakSolun);        
        BudovaSpojka.setVychod(Belehrad);        
        VlakSolun.setVychod(Solun);        
        Solun.setVychod(VlakIstanbul);
        VlakIstanbul.setVychod(Instanbul);        
        Instanbul.setVychod(KancelarDustojniku); 
        Instanbul.setVychod(Pristav);         
        Pristav.setVychod(CestaHaifa);        
        CestaHaifa.setVychod(Haifa);        
               
        Postava PanEmanuel = new Postava("PanEmanuel", "\nDobry den, tak tedy dnes ktomu dojde, az budes mit vse potrebne,\nvydej se na cestu dle PLANU", "PanEmanuel.jpg");
        Postava Matej = new Postava( "Matej","\nNazdar, starej rikal, ze na tebe ma prijit rada, pokud si snim nemluvil,\nzajdi zanim je v kancelari pobocky", "Matej.jpg");
        Postava Mvoj = new Postava("MadarskyVojak", "\nÖn letartóztatták!!!!", "MadarskyVojak.jpg");
        Postava pruvodci1 = new Postava("PruvodciMAD","\nNem tudok segíteni", "PruvodciMAD.jpg");
        Postava pruvodci2 = new Postava("PruvodciJUG","\nZa sada ćemo biti u Beogradu", "PruvodciJUG.jpg");
        Postava kamil = new Postava("Kamil","\nNazdar, tak uz mas kus zasebou. Skoc do mista_setkani,\ndaj ti tam PAS pro zbytek cesty", "Kamil.jpg");
        Postava ratko = new Postava("Ratko","\nTak ahoj ty od Kamil zejo,ja mam neco tobe dat", "Ratko.jpg");     
        Postava senior = new Postava("senior", "\nO chrónos kylá san ta pouliá... time flies like a bird.. pick up and use that stone..trust me", "senior.jpg");
        Postava Hercule = new Postava("Hercule","\nDobrý den, me jmeno je Hercule Poirot, ztratil jsem rukavice", "Hercule.jpg");
        Postava csVojak = new Postava("csVojak","\nNazdar!! To sem rád že si konečně dorazil, jsi poslední zajdem do zachytneho_pracoviste", "csVojak.jpg");
        Postava dustojnik1 = new Postava("dustojnik","\nNuze, na nasem listu jste posledni, od teto chvile jste vojínem československé armády.\nVyplnte formular a\nOdchod!", "dustojnik.jpg");
        Postava csVojak2 = new Postava("vojak1", "\nAhoj, tak ty si přišel z čech? Já a další sme se konečně dostali z Ruska,\nkdyž jsme šli napomoc Polsku, tak nás rusáci zajali", "vojak1.jpg");
        Postava csVojak3 = new Postava("vojak2","\nJe mi spatne asi budu ..fvbevuvkmw..to sem nejedl", "vojak2.jpg");
        Postava velitel = new Postava("veliciDustojnik","Dobrý den vojíne, vaše cesta zde končí a zárovň začíná", "veliciDustojnik.jpg");
        Postava poddustojnik = new Postava("poddustojnik","\nJe mi líto ale došlo k úniku seznamu jmen z kanceláře v Istanbulu,\nrodiče vaše i dalších byli zadrženi gestapem", "poddustojnik.jpg");
       
       DilnaB.vlozPostavu(PanEmanuel);
       DilnaA.vlozPostavu(Matej);
       NelCestaM.vlozPostavu(Mvoj);
       VlakBelehrad.vlozPostavu(pruvodci1);
       VlakBelehrad.vlozPostavu(pruvodci2);
       Belehrad.vlozPostavu(kamil);
       BudovaSpojka.vlozPostavu(ratko);
       VlakSolun.vlozPostavu(senior);
       
       VlakIstanbul.vlozPostavu(Hercule);
       Instanbul.vlozPostavu(csVojak);
       KancelarDustojniku.vlozPostavu(dustojnik1);
       Pristav.vlozPostavu(csVojak2);
       CestaHaifa.vlozPostavu(csVojak3);
       Haifa.vlozPostavu(velitel);
       Haifa.vlozPostavu(poddustojnik);
      
      Vec PrukazLetcu=new Vec("PrukazLetcu", "\nFalešný průkaz anglických letců, použij až v bělehradu", true, "PrukazLetcu.jpg");
      Vec ManualAvia=new Vec("ManualAvia", "\nManuál pro letadlo AviaB534", true, "ManualAvia.jpg");
      Vec PrukazMonteru=new Vec("PrukazMonteru","\nFalešný průkaz montéra z bělehradského továrního závodu", true, "PrukazMonteru.jpg");
          Vec PlanCesty=new Vec("PlanCesty","\nPlán cesty je zakódován v časopisu a vypadá následovně:"+
                                             "\n1)Poté co získáte vše potřebné vydejte se na nelegalni cestu do Maďarska"+
                                             "\n2)Po vstoupení do Maďarska se vydejte vlakem do Bělehradu"+
                                             "\n3)V Bělehradu vyhledejte Kamila, ten vám řekne co budete potřebovat pro další cestu"+
                                             "\n4)Z Bělehradu pokračujte vlakem do Soluni, budete opouštět jugoslávii,\nv řecku na vás mohou polížet jako na němce"+  
                                             "\n5)Ze Soluně se vydejte vlakem do Istanbulu, zde už naleznete zástupce čs. exilové armády"+
                                             "\n6)Z Istanbulu se musíte dostat do přístavu Mersin"+
                                             "\n7)Z přístavu putujete lodí do Haify"+
                                             "\n8)Haifa je vaše cílová destinace",true, "PlanCesty.png");
      Vec toaleta=new Vec("toaleta","Ve stanici nelze", false, "toaleta.jpg");
      Vec AviaB534=new Vec("AviaB534", "Letadlo, ktere vas muze dopravit do Istanbulu", false, "AviaB534.jpg");
      Vec telefonni_budka=new Vec("telefonni_budka", "Telefonni budka", false, "telefonni_budka.jpg");
      Vec pas=new Vec("pas", "Tento pas budes potrebovat pro zbytek cesty", true, "pas.jpg");
      Vec noviny=new Vec("noviny","Německo podpoří Italy v útoku na Řecko, britové posílají letce",true, "noviny.jpg");
      Vec racek=new Vec("racek", "racek zelenonohý (Larus audouinii)",false, "racek.jpg");
      Vec lavicka=new Vec("lavicka", "Lavicka na nadraží, někdo na ní spí", false, "lavicka.jpg");
      Vec rukavice=new Vec("rukavice", "kožené rukavice s monogramem HP", true, "rukavice.jpg");
      Vec kebab=new Vec("kebab", "mistni delikatesa...lezel na zemi....radeji nejsit", true, "kebab.jpg");
      Vec formular=new Vec("formular","Zde se musíte formálně přihlásit do čs armády, vyplňte své pravé osobní udaje", false, "formular.jpg");
      Vec tableta=new Vec("tableta", "Prášek proti mořské nemoci", true, "tableta.jpg");
      Vec dalekohled=new Vec("dalekohled", "Klasicky armadni dalekohled",true, "dalekohled.jpg");
      
      
      DilnaA.vlozVec(PrukazLetcu);
      DilnaA.vlozVec(ManualAvia);
       DilnaB.vlozVec(PrukazMonteru);
       DilnaB.vlozVec(PlanCesty);
      VlakBelehrad.vlozVec(toaleta);
       Belehrad.vlozVec(AviaB534);
      
       Solun.vlozVec(telefonni_budka);
       BudovaSpojka.vlozVec(pas);
       VlakSolun.vlozVec(noviny);
       Solun.vlozVec(racek);
       Solun.vlozVec(lavicka);
       VlakIstanbul.vlozVec(rukavice);
       Instanbul.vlozVec(kebab);
       KancelarDustojniku.vlozVec(formular);
       Pristav.vlozVec(tableta);
       Pristav.vlozVec(dalekohled);
       
      // nasteveni prom 
       aktualniProstor = Dubovec; // hra začíná v dubovic
       let = Instanbul;
     }
    
   
        
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }
    
    /**
     * pomocna metoda ktera nam pomuze uchovat hodnotu prostoru Istanbul
     * pro nasledny let letadlem pri pouziti prikazu pouzij
     * 
     * return prostor Istanbul
     */
    public Prostor getCil() {
        return let;
    }
    
    /**
     * pomocna metoda ktera nam pomuze uchovat hodnotu prostoru Haifa 
     * pro naslednou "teleporatci" pri pouziti prikazu pouzij
     * 
     * @return prostor haifa
     * 
     */
   // public Prostor getCil2() {
    //    return kamenLET;
   // }
    
    /**
     * Metoda ktera nam pouze ziskat Batoh pro herni plan 
     * 
     * @return objekt batoh
     */
    public Batoh getBatoh() {
        return this.batoh;
    }
    
    //Veskere boolean promene v teto metode jsou nastavene na hodnotu false
    
    
    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
       notifyObservers();
    }
  
    /** 
     * getter pro hodbnotu umisletat
     * Volanim teto metodu zjistim hodnotu boolenovske hodnoty parametru
     * pri pokusu o pouziti AvieB534
     * 
     * @return parametr s boolean hodnotou
     */
    public boolean getUmisLitat() {
        return litej;
    }
    
    /**
     * Setter pro hodnotu umisletat
     * Pokud pouzijete manualAvia ve tride prikazUse se nastavi
     * hodnota na true
     */
    void setUmisLitat(boolean litej) {
        this.litej = litej;
    }
    
    /** 
     * getter pro hodnotu vom
     * Volanim teto metodu zjistim hodnotu boolenovske hodnoty parametru
     * pri plaveni do Hafy se vam udela nevolno 
     * Po pruchdu do prostoru Haifa se zjistuje tato hodnota
     * 
     * @return parametr s boolean hodnotou
     */
    public boolean getVom() {
        return vom;
    }
    
    /** 
     * setter pro hodbnotu vom
     * k nastavovani tohoto setteru dochazi pri poziti tablety v prostrou do_Haify
     * pokud tapletu pouzijete nastavi se na true
     */
    void setVom(boolean vom) {
        this.vom = vom;
    }
    
    /**
     * 
     * Getter pro hodnotu parametru mon
     * Tato metoda nam pomuze zjistit zdali jste pouzil prukaz monteru 
     * 
     * @return parametr s boolean hodnotou
     */
    public boolean getMont() {
        return mon;
    }
    
    /**
     * 
     * Setter pro hodnotu parametru mon
     * pokud jste pouzili prukaz monteru dojde k nastaveni na true
     */
    void setMont(boolean mon) {
        this.mon = mon;
    }
    
    /**
     * Getter pro hodnotu parametru pas
     * Hodnota parametru rika zdali jste pouzil Pas
     * trua ano, false ne
     * 
     * @return parametr s boolean hodnotou
     */
    public boolean getPas() {
        return pas;
    }
    
    /**
     * Setter pro hodnotu parametru pas
     * pokud jste pouzili Pas dojde k nastaveni na true
     * trua ano, false ne
     */
    void setPas(boolean pas) {
        this.pas = pas;
    }
   
    /**
     * Getter pro hodnotu parametru letci
     * Hodnota parametru rika zdali jste pouzil falesny prukaz letcu
     * trua ano, false ne
     * 
     * @return parametr s boolean hodnotou
     */
    public boolean getLet() {
        return letci;
    }
    
    /**
     * Setter pro hodnotu parametru pas
     * pokud jste pouzili falesny prukaz letcu dojde k nastaveni na true
     */
    void setLet(boolean letci) {
        this.letci = letci;
    }
    
    /**
     * 
     * Setter pro informaci o vyhre ci nevyhre hry
     */
    void setHracVyhral(boolean vhr) {
        this.vhr = vhr;
    }
  
    
    /**
     * getter pro informai o vyhre ci nevyhre hry
     * vyhra true -nevyhra false
     * 
     * @return parametr s boolean hodnotou
     */
    public boolean hracVyhral() {
        return vhr;
    }
    
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        listObserveru.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
        }
    }
  
    
}
