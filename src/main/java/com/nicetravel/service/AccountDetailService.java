package com.nicetravel.service;


import com.nicetravel.entity.AccountDetail;

import java.util.List;

public interface AccountDetailService {
    List<AccountDetail> getAllAccountDetail();

    AccountDetail findAccountDetailById(Integer id);

    AccountDetail createAccountDetail(AccountDetail accountDetail);

    AccountDetail updateAccountDetail(AccountDetail accountDetail);

    void deleteAccountDetail(Integer id);

//    AccountDetail findAccountDetailByUsername(String username);
}
