package atmMachine.enums;

public enum TransactionType {
    WITHDRAW,
    CHECK_BALANCE;
    public static void printAllStates(){
        for (TransactionType type:TransactionType.values()){
                System.out.println(type.name());
            }
        }
}

