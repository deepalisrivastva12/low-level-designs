package atmMachine;

import atmMachine.enums.TransactionType;
import atmMachine.model.Atm;
import atmMachine.model.Card;
import atmMachine.model.User;
import atmMachine.model.UserBankAccount;
import atmMachine.repository.BankRepository;

public class ATMRoom {
    Atm atm;
    BankRepository bankRepository;
    User user;
    public static void main(String[] args){
        ATMRoom atmRoom=new ATMRoom();
        atmRoom.initialize();

        System.out.println("-----Current Status of ATM-----");
        atmRoom.atm.printATMStatus();

        atmRoom.atm.getMachineState().insertCard(atmRoom.user.getCard(),atmRoom.atm);
        atmRoom.atm.getMachineState().authenticateCardPin(atmRoom.user.getCard(),atmRoom.atm,1211);
        atmRoom.atm.getMachineState().selectOperation(atmRoom.user.getCard(),atmRoom.atm, TransactionType.WITHDRAW);
        atmRoom.atm.getMachineState().cashWithdraw(atmRoom.user.getCard(),atmRoom.atm,3500);

        UserBankAccount bankAccount=atmRoom.bankRepository.getAccountByCard(atmRoom.user.getCard().getCardNumber());
        System.out.println("Current balance in user's account: "+bankAccount.getBalance());

        System.out.println("-----Current Status of ATM-----");
        atmRoom.atm.printATMStatus();
    }
    private void initialize(){
        bankRepository=new BankRepository();
        atm=Atm.getAtmObj();
        atm.setBankRepository(bankRepository);
        atm.setAtmBalance(10000,3,7,5);
        this.user=createUser();
    }

    private User createUser() {
        User user1=new User();
        Card card=new Card("123456789","12/29","James");
        bankRepository.registerCard("123456789",new UserBankAccount(5000,1211));
        user1.setCard(card);
        return user1;
    }
}
