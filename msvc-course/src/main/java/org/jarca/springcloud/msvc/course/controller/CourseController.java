package org.jarca.springcloud.msvc.course.controller;

import org.jarca.springcloud.msvc.course.entity.Course;
import org.jarca.springcloud.msvc.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> listar(){
        return ResponseEntity.ok(courseService.listCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        Optional<Course> optionalCourse = courseService.courseById(id);
        if (optionalCourse.isPresent()){
            return ResponseEntity.ok(optionalCourse.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> createCourse(@Valid @RequestBody Course course, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return validateResult(bindingResult);
        }
        Course coursedb = courseService.saveCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(coursedb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course,BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()){
            return validateResult(bindingResult);
        }
        Optional<Course> opt = courseService.courseById(id);
        if (opt.isPresent()){
            Course cursoDB = opt.get();
            cursoDB.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(cursoDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        Optional<Course> opt = courseService.courseById(id);

        if (opt.isPresent()){
            courseService.deleteCourseById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validateResult(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
