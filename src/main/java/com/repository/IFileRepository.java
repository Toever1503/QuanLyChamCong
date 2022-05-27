package com.repository;

import com.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFileRepository extends JpaRepository<File, Long> {
}
