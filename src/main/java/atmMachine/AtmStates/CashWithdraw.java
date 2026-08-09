package atmMachine.AtmStates;


import atmMachine.CashWithdrawProcess.CashWithdrawProccess;
import atmMachine.CashWithdrawProcess.FiveHundredProcess;
import atmMachine.CashWithdrawProcess.OneHundredProcess;
import atmMachine.CashWithdrawProcess.TwoThousandProcess;
import atmMachine.model.Atm;
import atmMachine.model.Card;
import atmMachine.model.UserBankAccount;

public class CashWithdraw extends AtmMachineState {
    public CashWithdraw() {
        System.out.println("-----Cash Withdrawn-----");

    }

    @Override
    public void cashWithdraw(Card card, Atm atm, int cashWithdrawAmount) {
        UserBankAccount bankAccount =atm.getBankRepository().getAccountByCard(card.getCardNumber());
        if(atm.getTotalAtmBalance()<cashWithdrawAmount){
            System.out.println("Insufficient Balance in ATM!!");
            exit(atm);
        } else if (bankAccount.getBalance()<cashWithdrawAmount) {
            System.out.println("Insufficient Balance in your bank account!!");
            exit(atm);
        }else {
            atm.deductFromTotalBalance(cashWithdrawAmount);
            bankAccount.setBalance(bankAccount.getBalance()-cashWithdrawAmount);
            CashWithdrawProccess cashWithdrawProccess=new TwoThousandProcess(new FiveHundredProcess(new OneHundredProcess(null)));
            cashWithdrawProccess.withdraw(atm,cashWithdrawAmount);

        }
    }
    @Override
    public void exit(Atm atm) {
        returnCard();
        atm.setMachineState(new IdleState());
        System.out.println("Exit Successfully!!");
    }

    @Override
    public void returnCard() {
        System.out.println("Kindly take the card out!!");
    }
}
