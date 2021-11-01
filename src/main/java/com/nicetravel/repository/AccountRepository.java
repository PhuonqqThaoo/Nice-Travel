package com.nicetravel.repository;

import com.nicetravel.entity.Account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
//    @Query("select DISTINCT ar.username from Account ar WHERE ar.roleId.id IN ('ADMIN','STAFF')")
//    List<Account> getAdministrators();

//    getAccountByUsername
    @Query("SELECT u FROM Account u WHERE u.username =?1")
    Account findAccountsByUsername(String username);

    @Query("SELECT u FROM Account u WHERE u.email = ?1")
    public Account findByEmail(String email);

    @Query("SELECT u FROM Account u WHERE u.verificationCode = ?1")
    public Account findByVerificationCode(String code);
    
    @Query("SELECT u FROM  Account u WHERE u.role_Id.id = 3")
    List<Account> findByUser();
    
    @Query(value ="SELECT * FROM Account WHERE is_enable = 0 and role_Id = 3", nativeQuery = true)
    List<Account> findAllAvailable();
    
    @Query(value ="SELECT * FROM Account WHERE is_enable = 0 and role_Id = 2", nativeQuery = true)
    List<Account> findAllByStaff();
    
//    List<Account> findByIsEnable (Boolean isDeleted) ;
    @Modifying(clearAutomatically =true)
    @Query(value ="UPDATE Account SET fullname = ?1, email = ?2, phone = ?3, id_Card= ?4,  gender =?5, address= ?6 WHERE username = ?7", nativeQuery = true)
    void updateNonPass(String fullname, String email, String phone,String idCard, Boolean gender,String address, String username);
    
    @Modifying(clearAutomatically =true)
    @Query(value="UPDATE Account SET fullname = ?1, email = ?2,password = ?3, phone = ?4, id_Card= ?5,  gender = ?6, address = ?7 WHERE username = ?8", nativeQuery = true)
    void update (String fullname, String email,String password, String phone,String idCard, Boolean gender, String address, String username);
    
    @Modifying(clearAutomatically =true)
    @Query(value="UPDATE Account SET  is_enable = 1 WHERE username =?1", nativeQuery = true)
    void deletedUser(String username);
    
   
} 
