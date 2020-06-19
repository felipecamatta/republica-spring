package com.dev.republica.repository;

import com.dev.republica.model.Feedback;
import com.dev.republica.model.Republica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByRepublica(Republica republica);

}
