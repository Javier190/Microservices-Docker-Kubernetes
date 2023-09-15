package org.jarca.springcloud.msvc.course.service;

import org.jarca.springcloud.msvc.course.entity.Course;
import org.jarca.springcloud.msvc.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Course> listCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> courseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(Long id) {
        if (courseRepository.findById(id).isPresent()){
            courseRepository.deleteById(id);
        }
    }
}
