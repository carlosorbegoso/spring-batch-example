package com.skyblue.apiconsultas.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SunatDataRepository extends JpaRepository<SunatData, Long> {
    boolean existsByRuc(String ruc);

    Optional<SunatData> findByRuc(String ruc);
    @Query(value = "select s.ruc from sunat_data_ruc s where s.ruc in :rucList", nativeQuery = true)
    List<String> findAllByRucIn(@Param("rucList") List<String> rucList);
}