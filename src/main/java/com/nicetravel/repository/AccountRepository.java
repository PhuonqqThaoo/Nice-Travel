package com.nicetravel.repository;

import com.nicetravel.entity.Account;

import java.util.List;

import com.nicetravel.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
//    @Query("select DISTINCT ar.username from Account ar WHERE ar.roleId.id IN ('ADMIN','STAFF')")
//    List<Account> getAdministrators();

//    getAccountByUsername
    @Query("SELECT u FROM Account u WHERE u.username =?1")
    Account findAccountsByUsername(String username);

    @Query("SELECT u FROM Account u WHERE u.id_Card =?1")
    Account findAccountByIDCard(String id_Card);

    @Query("SELECT u FROM Account u WHERE u.username = :username")
    public Account getUserByUsername(@Param("username") String username);

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

    @Query(value ="SELECT * FROM Account WHERE is_enable = 0 and role_Id = 2", nativeQuery = true)
    Page<Account> findAllByStaffPageActive(Pageable page);

    @Query(value ="SELECT * FROM Account WHERE role_Id = 2", nativeQuery = true)
    Page<Account> findAllByStaffPage(Pageable page);

    @Query(value ="SELECT * FROM Account WHERE is_enable = 1 and role_Id = 2", nativeQuery = true)
    Page<Account> findAllByStaffPageNoActive(Pageable page);

    @Query(value ="SELECT * FROM Account WHERE is_enable = 0 and role_Id = 3", nativeQuery = true)
    Page<Account> findAllByUserActivate(Pageable page);

    //danh sách khách hàng hôm nay
    @Query(value ="SELECT * FROM Account where DAY(created_date) = Day(GETDATE()) and role_Id = 3 and Month(created_date) = Month(GETDATE()) and Year(created_date) = Year(GETDATE()) and is_enable = 0", nativeQuery = true)
    Page<Account> findAllByUserActivateInGetDate(Pageable page);

    //danh sách khách hàng tháng này
    @Query(value ="SELECT * FROM Account where  role_Id = 3 and Month(created_date) = Month(GETDATE()) and Year(created_date) = Year(GETDATE()) and is_enable = 0", nativeQuery = true)
    Page<Account> findAllByUserActivateInMonth(Pageable page);

    //danh sách khách hàng năm này
    @Query(value ="select * from Account where  role_Id = 3 and Year(created_date) = Year(GETDATE()) and is_enable = 0", nativeQuery = true)
    Page<Account> findAllByUserActivateInYear(Pageable page);

    @Query(value ="SELECT * FROM Account WHERE role_Id = 3", nativeQuery = true)
    Page<Account> getAllUserAdmin(Pageable page);

    @Query(value ="SELECT * FROM Account WHERE is_enable = 1 and role_Id = 3", nativeQuery = true)
    Page<Account> findAllByUserNoActivate(Pageable page);
    
//    List<Account> findByIsEnable (Boolean isDeleted) ;
    @Modifying(clearAutomatically =true)
    @Query(value ="UPDATE Account SET fullname = ?1, email = ?2, phone = ?3, id_Card= ?4,  gender =?5, address= ?6 WHERE username = ?7", nativeQuery = true)
    void updateNonPass(String fullname, String email, String phone,String idCard, Boolean gender,String address, String username);

    @Modifying(clearAutomatically =true)
    @Query(value="UPDATE Account SET fullname = ?1, email = ?2,password = ?3, phone = ?4, id_Card= ?5,  gender = ?6, address = ?7, img = ?8 WHERE username = ?9", nativeQuery = true)
    void update (String fullname, String email,String password, String phone,String idCard, Boolean gender, String address, String img, String username);

    @Modifying(clearAutomatically =true)
    @Query(value="UPDATE Account SET  is_enable = 1 WHERE username =?1", nativeQuery = true)
    void deletedUser(String username);
    
    // số lượng khách tháng hiện tại
    @Query(value = "{CALL sp_getTotalUserCurrentMonth()}", nativeQuery = true)
    Integer getTotalUsers();
    
    // số lượng khách tháng trước
    @Query(value = "{CALL sp_getTotalUserLastMonth()}", nativeQuery = true)
	Integer getTotalUserLastMonth();

//
    @Modifying
    @Query("UPDATE Account u SET u.provider = ?2 WHERE u.username = ?1")
    public void updateProviderType(String username, Provider provider);

    @Modifying
    @Query("UPDATE Account u SET u.password = ?1 WHERE u.username = ?2")
    public void changePassword(String newPass, String username);

    @Query("SELECT u FROM  Account u WHERE u.username = ?1")
    Account getIdByUser(String username);

} 
