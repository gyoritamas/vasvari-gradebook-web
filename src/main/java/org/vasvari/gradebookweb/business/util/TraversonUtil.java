package org.vasvari.gradebookweb.business.util;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.stereotype.Component;
import org.vasvari.gradebookweb.business.dto.SubjectOutput;
import org.vasvari.gradebookweb.business.dto.GradebookOutput;
import org.vasvari.gradebookweb.business.dto.StudentDto;
import org.vasvari.gradebookweb.business.dto.TeacherDto;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class TraversonUtil {
    private final JwtTokenUtil jwtTokenUtil;

    public Collection<StudentDto> getStudentDtoCollection(String url, String linkTo) {
        Traverson traverson = new Traverson(URI.create(url), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<StudentDto> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<StudentDto> courseResource = traverson
                    .follow(String.format("$._links.%s.href", linkTo))
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (courseResource != null)
                return courseResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            // TODO: use custom exception
            throw new RuntimeException("Unauthorized");
        }
    }

    public Collection<TeacherDto> getTeacherDtoCollection(String url, String linkTo) {
        Traverson traverson = new Traverson(URI.create(url), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<TeacherDto> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<TeacherDto> teacherResource = traverson
                    .follow(String.format("$._links.%s.href", linkTo))
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (teacherResource != null)
                return teacherResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unauthorized");
        }
    }

    public Collection<SubjectOutput> getSubjectOutputCollection(String url, String linkTo) {
        Traverson traverson = new Traverson(URI.create(url), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<SubjectOutput> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<SubjectOutput> courseResource = traverson
                    .follow(String.format("$._links.%s.href", linkTo))
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (courseResource != null)
                return courseResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            // TODO: use custom exception
            throw new RuntimeException("Unauthorized");
        }
    }

    public Collection<GradebookOutput> getGradebookOutputCollection(String url, String linkTo) {
        Traverson traverson = new Traverson(URI.create(url), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<GradebookOutput> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<GradebookOutput> gradebookResource = traverson
                    .follow(String.format("$._links.%s.href", linkTo))
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (gradebookResource != null)
                return gradebookResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unauthorized");
        }
    }
}
