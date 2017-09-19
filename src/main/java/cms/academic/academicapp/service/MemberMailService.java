package cms.academic.academicapp.service;

import cms.academic.academicapp.model.Mail;

public interface MemberMailService {
    public Integer sendMailToStudents(Mail content);
}
