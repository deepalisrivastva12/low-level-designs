package atmMachine.CashWithdrawProcess;

import atmMachine.model.Atm;

public class OneHundredProcess extends CashWithdrawProccess {
    public OneHundredProcess(CashWithdrawProccess cashWithdrawProccess) {

        super(cashWithdrawProccess);
    }

    @Override
    public void withdraw(Atm atm, int amount) {
        int requiredNotes=amount/100;
        int balanceAfterDeductionOfOneHundredNotes=amount%100;

        if(requiredNotes<=atm.getOneHundredNotes()){
            atm.deductOneHundredNotes(requiredNotes);
        }
        if(requiredNotes>atm.getOneHundredNotes()){
            balanceAfterDeductionOfOneHundredNotes+=(requiredNotes-atm.getOneHundredNotes())*100;

        }
        if(balanceAfterDeductionOfOneHundredNotes!=0){
            super.withdraw(atm,balanceAfterDeductionOfOneHundredNotes);
        }

    }
}
