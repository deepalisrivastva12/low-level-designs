package atmMachine.CashWithdrawProcess;

import atmMachine.model.Atm;

public class CashWithdrawProccess {
    CashWithdrawProccess cashWithdrawProccess;

    public CashWithdrawProccess(CashWithdrawProccess cashWithdrawProccess) {
        this.cashWithdrawProccess = cashWithdrawProccess;
    }
    public void withdraw(Atm atm,int amount){
        if(cashWithdrawProccess!=null){
            cashWithdrawProccess.withdraw(atm,amount);
        }
    }
}
