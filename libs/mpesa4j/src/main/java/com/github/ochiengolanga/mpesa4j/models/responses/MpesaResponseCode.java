package com.github.ochiengolanga.mpesa4j.models.responses;

public interface MpesaResponseCode {
    //0	    Success
    //1	    Insufficient Funds
    //2	    Less Than Minimum Transaction Value
    //3	    More Than Maximum Transaction Value
    //4	    Would Exceed Daily Transfer Limit
    //5	    Would Exceed Minimum Balance
    //6	    Unresolved Primary Party
    //7	    Unresolved Receiver Party
    //8	    Would Exceed Maxiumum Balance
    //11	Debit Account Invalid
    //12	Credit Account Invaliud
    //13	Unresolved Debit Account
    //14	Unresolved Credit Account
    //15	Duplicate Detected
    //17	Internal Failure
    //20	Unresolved Initiator
    //26	Traffic blocking condition in place

    int SUCCESS = 0;
    int INSUFFICIENT_FUNDS = 1;
    int LESS_THAN_MINIMUM_TRANSACTION_VALUE = 2;
    int MORE_THAN_MAXIMUM_TRANSACTION_VALUE = 3;
    int WOULD_EXCEED_DAILY_TRANSFER_LIMIT = 4;
    int WOULD_EXCEED_MINIMUM_BALANCE = 5;
    int UNRESOLVED_PRIMARY_PARTY = 6;
    int UNRESOLVED_RECEIVER_PARTY = 7;
    int WOULD_EXCEED_MAXIMUM_BALANCE = 8;
    int DEBIT_ACCOUNT_INVALID = 11;
    int CREDIT_ACCOUNT_INVALID = 12;
    int UNRESOLVED_DEBIT_ACCOUNT = 13;
    int UNRESOLVED_CREDIT_ACCOUNT = 14;
    int DUPLICATE_DETECTED = 15;
    int INTERNAL_FAILURE = 17;
    int UNRESOLVED_INITIATOR = 20;
    int TRAFFIC_BLOCKING_CONDITION_IN_PLACE = 26;
}
