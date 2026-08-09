package atmMachine.model;

import atmMachine.AtmStates.AtmMachineState;
import atmMachine.AtmStates.IdleState;
import atmMachine.repository.BankRepository;

public class Atm {
    private static Atm atmObj=new Atm();
    AtmMachineState machineState;
    BankRepository bankRepository=new BankRepository();
    int twoThousandsNotes;
    int fiveHundredNotes;
    int oneHundredNotes;
    int totalAtmBalance;

    public Atm() {
    }

    public void setAtmBalance(int totalAtmBalance,int twoThousandsNotes,int fiveHundredNotes,int oneHundredNotes){
        this.totalAtmBalance=totalAtmBalance;
        this.twoThousandsNotes=twoThousandsNotes;
        this.fiveHundredNotes=fiveHundredNotes;
        this.oneHundredNotes=oneHundredNotes;
    }

    public BankRepository getBankRepository() {
        return bankRepository;
    }

    public void setBankRepository(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public static Atm getAtmObj() {
        atmObj.setMachineState(new IdleState());
        return atmObj;
    }

    public static void setAtmObj(Atm atmObj) {
        Atm.atmObj = atmObj;
    }

    public AtmMachineState getMachineState() {
        return machineState;
    }

    public void setMachineState(AtmMachineState machineState) {
        this.machineState = machineState;
    }

    public int getTwoThousandsNotes() {
        return twoThousandsNotes;
    }

    public int getFiveHundredNotes() {
        return fiveHundredNotes;
    }

    public int getOneHundredNotes() {
        return oneHundredNotes;
    }

    public void deduct2kNotes(int n){
        twoThousandsNotes-=n;
    }
    public void deductfiveHundredNotes(int n){
        fiveHundredNotes-=n;
    }
    public void deductOneHundredNotes(int n){
       oneHundredNotes-=n;
    }
    public void deductFromTotalBalance(int n){
        totalAtmBalance=totalAtmBalance-n;
    }
    public int getTotalAtmBalance() {
        return totalAtmBalance;
    }


    public void printATMStatus(){
        System.out.println("Total Number of 2k Notes: "+twoThousandsNotes);
        System.out.println("Total Number of 500 Notes: "+fiveHundredNotes);
        System.out.println("Total Number of 100 Notes: "+oneHundredNotes);
        System.out.println("Current ATM Balance: "+totalAtmBalance);

    }
}
