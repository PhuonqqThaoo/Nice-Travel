package com.nicetravel.repository;

import com.nicetravel.entity.TravelTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelTypeRepository extends JpaRepository<TravelTypes, Integer> {
    @Query(value ="SELECT * From travel_types where is_Deleted = 0",nativeQuery = true)
    List<TravelTypes> findAllAdmin();

    @Query(value ="SELECT * From travel_types where is_Deleted = 0",nativeQuery = true)
    Page<TravelTypes> findAllAdminPage(Pageable pageable);

    @Modifying(clearAutomatically =true)
    @Query(value = "UPDATE travel_types SET type =?1, description =?2, slug = ?3 where id =?4 ", nativeQuery = true)
    void updateTravelTypeAdmin(String type, String description,String slug, Integer id );

    @Modifying(clearAutomatically =true)
    @Query(value="UPDATE travel_types SET  is_Deleted = 1 WHERE id =?1", nativeQuery = true)
    void deletedTravelType(Integer id);
}
