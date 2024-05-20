package com.datn.shopshoesbackend.repository;

import com.datn.shopshoesbackend.dto.ResAccountDto;
import com.datn.shopshoesbackend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByUsername(String username);

    @Query("SELECT new com.datn.shopshoesbackend.domain.dto.RespAccountDto(a.id, a.username, a.createDate, a.modifyDate, a.isActive, r.name, " +
            "ad.fullname, ad.gender, ad.phone, ad.email, ad.address, ad.birthDate) " +
            "FROM Account a " +
            "INNER JOIN AccountDetail ad ON a.id = ad.account.id " +
            "INNER JOIN Role r ON a.role.id = r.id " +
            "WHERE a.username = ?1")
    ResAccountDto findByUsername(String username);
}
