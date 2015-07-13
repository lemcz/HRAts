package HRAts.repository;

import HRAts.model.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AttachmentRepository extends CrudRepository<Attachment, Integer> {
    Attachment findById(@Param("id") int id);

    Attachment findByOwner_Id(@Param("id") int id);

    Attachment findByCandidate_Id(@Param("id") int id);

    Attachment findByContact_Id(@Param("id") int id);

    Attachment findByCompany_Id(@Param("id") int id);

    Attachment findByVacancy_Id(@Param("id") int id);

    Attachment findByContact_IdAndName(@Param("id") int id,
                                       @Param("fileName") String filePath);
}