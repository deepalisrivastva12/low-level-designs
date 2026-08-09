package atmMachine.CashWithdrawProcess;

import atmMachine.model.Atm;

public class FiveHundredProcess extends CashWithdrawProccess {
    public FiveHundredProcess(CashWithdrawProccess cashWithdrawProccess) {
        super(cashWithdrawProccess);
    }

    @Override
    public void withdraw(Atm atm, int amount) {
        int requiredNotes=amount/500;
        int balanceAfterDeductionOf500Notes=amount%500;

        if(requiredNotes<=atm.getFiveHundredNotes()){
            atm.deductfiveHundredNotes(requiredNotes);
        }
        if(requiredNotes>atm.getFiveHundredNotes()){
            balanceAfterDeductionOf500Notes+=(requiredNotes-atm.getFiveHundredNotes())*500;

        }
        if(balanceAfterDeductionOf500Notes!=0){
            super.withdraw(atm,balanceAfterDeductionOf500Notes);
        }

    }
}
