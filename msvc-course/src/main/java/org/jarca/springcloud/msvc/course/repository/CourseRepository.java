package org.jarca.springcloud.msvc.course.repository;

import org.jarca.springcloud.msvc.course.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

}
