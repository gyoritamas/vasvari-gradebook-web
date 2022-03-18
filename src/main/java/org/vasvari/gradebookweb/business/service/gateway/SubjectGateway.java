package org.vasvari.gradebookweb.business.service.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.business.dto.SubjectInput;
import org.vasvari.gradebookweb.business.dto.SubjectOutput;
import org.vasvari.gradebookweb.business.dto.StudentDto;
import org.vasvari.gradebookweb.business.model.CourseOutputModel;
import org.vasvari.gradebookweb.business.util.TraversonUtil;

import java.util.Collection;

@Service
@ComponentScan
@RequiredArgsConstructor
public class SubjectGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final TraversonUtil traversonUtil;
    private final RestTemplate template;

    public SubjectOutput findSubjectById(Long id) {
        ResponseEntity<CourseOutputModel> response = template.getForEntity(baseUrl + "/subjects/{id}", CourseOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            // TODO: use custom exception
            throw new RuntimeException("Something went wrong");

        return response.getBody().getContent();
    }

    public Collection<SubjectOutput> findAllSubjects() {
        String url = baseUrl + "/subjects";
        String linkTo = "self";

        return traversonUtil.getSubjectOutputCollection(url, linkTo);
    }

    public Collection<SubjectOutput> findSubjectsOfCurrentUserAsTeacher() {
        String url = baseUrl + "/teacher-user/subjects";
        String linkTo = "subjects-of-teacher";

        return traversonUtil.getSubjectOutputCollection(url, linkTo);
    }

    public Collection<SubjectOutput> findSubjectsOfCurrentUserAsStudent() {
        String url = baseUrl + "/student-user/subjects";
        String linkTo = "subjects-of-student";

        return traversonUtil.getSubjectOutputCollection(url, linkTo);
    }

    public Collection<StudentDto> findStudentsOfSubject(Long subjectId) {
        String url = String.format("%s/subjects/%s/students", baseUrl, subjectId);
        String linkTo = "students-of-subject";

        return traversonUtil.getStudentDtoCollection(url, linkTo);
    }

    public void saveSubject(SubjectInput subject) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/subjects", subject, EntityModel.class);
        // TODO: use custom exception
        if (response.getStatusCodeValue() != 201 || response.getBody() == null)
            throw new RuntimeException("Something went wrong");
    }

    public void updateSubject(Long id, SubjectInput update) {
        template.put(baseUrl + "/subjects/{id}", update, id);
    }

    public void deleteSubject(Long id) {
        template.delete(baseUrl + "/subjects/{id}", id);
    }
}