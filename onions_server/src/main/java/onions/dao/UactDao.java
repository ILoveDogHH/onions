package onions.dao;

import onions.entity.UAct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UactDao extends JpaRepository<UAct, UAct.UActId> {

    @Query("SELECT u FROM UAct u WHERE u.uid >=:uid")
    List<UAct> getUserByLimit(@Param("uid")int uid);








}
