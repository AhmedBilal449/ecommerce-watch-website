package com.cstp.shop.model.dto;

import com.cstp.shop.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    Transfers form data into an object
    Variable names correlate to POST parameters
    email, password, password_retype
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto
{
    private String email;
    private String password;
    private String password_retype;

    public boolean isValidAccount()
    {
        return false;
    }


    public Account toAccount() {
        return new Account(email, password, Account.Group.CUSTOMER);
    }

}
