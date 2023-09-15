package org.jarca.springcloud.msvc.course.service;

import org.jarca.springcloud.msvc.course.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List <Course> listCourses();
    Optional<Course> courseById(Long id);
    Course saveCourse(Course course);
    void deleteCourseById(Long id);
}
