package com.nicetravel.dao;

import com.nicetravel.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDetailDAO extends JpaRepository<AccountDetail, Integer> {
}
