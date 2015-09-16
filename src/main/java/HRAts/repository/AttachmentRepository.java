package HRAts.repository;

import HRAts.model.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AttachmentRepository extends CrudRepository<Attachment, Integer> {
    Attachment findById(@Param("id") int id);

    Attachment findByOwner_Id(@Param("id") int id);

    Attachment findByUser_Id(@Param("id") int id);

    Attachment findByCompany_Id(@Param("id") int id);

    Attachment findByVacancy_Id(@Param("id") int id);

    Attachment findByUser_IdAndName(@Param("id") int id,
                                    @Param("fileName") String filePath);
}