package cms.academic.academicapp.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cms.academic.academicapp.model.Mail;
import cms.academic.academicapp.service.MemberMailService;


@Transactional(propagation = Propagation.REQUIRED)
public class MemberMailServiceImpl implements MemberMailService {

	@Override
	public Integer sendMailToStudents(Mail content) {
		// TODO Auto-generated method stub
		return null;
	}
}
