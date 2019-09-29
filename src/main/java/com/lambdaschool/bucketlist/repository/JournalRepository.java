package com.lambdaschool.bucketlist.repository;

import com.lambdaschool.bucketlist.models.Journal;
import org.springframework.data.repository.CrudRepository;

public interface JournalRepository extends CrudRepository<Journal, Long> {
}
