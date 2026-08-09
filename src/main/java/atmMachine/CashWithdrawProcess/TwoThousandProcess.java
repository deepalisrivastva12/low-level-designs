package atmMachine.CashWithdrawProcess;

import atmMachine.model.Atm;

public class TwoThousandProcess extends CashWithdrawProccess {

    public TwoThousandProcess(CashWithdrawProccess cashWithdrawProccess) {
        super(cashWithdrawProccess);
    }

    @Override
    public void withdraw(Atm atm, int amount) {
        int requiredNotes=amount/2000;
        int balanceAfterDeductionOf2kNotes=amount%2000;

        if(requiredNotes<=atm.getTwoThousandsNotes()){
            atm.deduct2kNotes(requiredNotes);
        }
        if(requiredNotes>atm.getTwoThousandsNotes()){
            balanceAfterDeductionOf2kNotes+=(requiredNotes-atm.getTwoThousandsNotes())*2000;

        }
        if(balanceAfterDeductionOf2kNotes!=0){
            super.withdraw(atm,balanceAfterDeductionOf2kNotes);
        }

    }
}

