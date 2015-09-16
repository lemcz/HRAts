package HRAts.service;

import HRAts.model.Attachment;
import HRAts.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Transactional(readOnly = true)
    public Iterable<Attachment> findAll() {
        return attachmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Attachment findById(int attachmentId) {
        return attachmentRepository.findById(attachmentId);
    }

    @Transactional(readOnly = true)
    public Attachment findByOwnerId(int ownerId) {
        return attachmentRepository.findByOwner_Id(ownerId);
    }

    @Transactional(readOnly = true)
    public Attachment findByCandidateId(int candidateId) {
        return attachmentRepository.findByUser_Id(candidateId);
    }

    @Transactional(readOnly = true)
    public Attachment findByContactId(int contactId) {
        return attachmentRepository.findByUser_Id(contactId);
    }

    @Transactional(readOnly = true)
    public Attachment findByVacancyId(int vacancyId) {
        return attachmentRepository.findByVacancy_Id(vacancyId);
    }

    @Transactional(readOnly = true)
    public Attachment findByCompanyId(int companyId) {
        return attachmentRepository.findByCompany_Id(companyId);
    }

    @Transactional(readOnly = true)
    @Secured("ROLE_USER")
    public Attachment findByContactIdAndName(int contactId, String fileName) {
        return attachmentRepository.findByUser_IdAndName(contactId, fileName);
    }

    @Transactional
    public Attachment save(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int attachmentId) {
        attachmentRepository.delete(attachmentId);
    }
}