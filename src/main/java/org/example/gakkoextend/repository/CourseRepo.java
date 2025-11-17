package org.example.gakkoextend.repository;
import org.example.gakkoextend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface CourseRepo extends JpaRepository<Course, UUID> {}
